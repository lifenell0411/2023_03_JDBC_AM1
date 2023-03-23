package com.KoreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.example.JAM.controller.MemberController;
import com.KoreaIT.example.JAM.util.DBUtil;
import com.KoreaIT.example.JAM.util.SecSql;
import com.KoreaIT.example.JAM.util.util;

public class App {
	public void start() {
		System.out.println("=프로그램 시작==");
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("명령어 ) ");
			String cmd = sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {

				conn = DriverManager.getConnection(url, "root", "");

				int actionResult = action(conn, sc, cmd);

				if (actionResult == -1) {
					System.out.println("프로그램을 종료합니다");
					break;
				}

			} catch (SQLException e) {
				System.out.println("@@@@@@에러 1@@@@@@:" + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int action(Connection conn, Scanner sc, String cmd) {
		if (cmd.equals("exit")) {
			System.out.println("프로그램을 종료합니다");
			return -1;
		}

		MemberController memberController = new MemberController();
		memberController.setConn(conn);
		memberController.setScanner(sc);

		if (cmd.equals("member join")) {

			memberController.doJoin(cmd);

		} else if (cmd.equals("article write")) {
			System.out.println("==게시물 작성==");
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			SecSql sql = new SecSql();

			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);

			int id = DBUtil.insert(conn, sql);

			System.out.println(id + "번 글이 생성 되었습니다");

		} else if (cmd.startsWith("article detail ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);

			System.out.println("==게시물 상세보기==");

			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

			if (articleMap.isEmpty()) {
				System.out.println(id + "번 글은 존재하지 않습니다");
				return 0;
			}

			Article article = new Article(articleMap);

			System.out.println("번호 : " + article.id);
			System.out.println("작성날짜 : " + util.getNowDateTimeStr(article.regDate));
			System.out.println("수정날짜 : " + util.getNowDateTimeStr(article.updateDate));
			System.out.println("제목 : " + article.title);
			System.out.println("내용 : " + article.body);

		} else if (cmd.startsWith("article delete ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);

			System.out.println("==게시물 삭제==");

			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*)");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);

			int articlesCount = DBUtil.selectRowIntValue(conn, sql);

			if (articlesCount == 0) {
				System.out.println(id + "번 글은 존재하지 않습니다");
				return 0;
			}

			sql = new SecSql();
			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(conn, sql);

			System.out.println(id + "번 글이 삭제 되었습니다");

		} else if (cmd.startsWith("article modify ")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);

			System.out.println("==게시물 수정==");
			System.out.printf("새 제목 : ");
			String title = sc.nextLine();
			System.out.printf("새 내용 : ");
			String body = sc.nextLine();

			SecSql sql = new SecSql();

			sql.append("UPDATE article");
			sql.append("SET updateDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			sql.append("WHERE id = ?", id);

			DBUtil.update(conn, sql);

			System.out.println(id + "번 글이 수정 되었습니다");

		} else if (cmd.equals("article list")) {
			System.out.println("==게시물 목록==");

			List<Article> articles = new ArrayList<>();

			SecSql sql = new SecSql();

			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("ORDER BY id DESC;");

			List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

			for (Map<String, Object> articleMap : articleListMap) {
				articles.add(new Article(articleMap));
			}

			if (articles.size() == 0) {
				System.out.println("게시글이 없습니다");
				return 0;
			}

			System.out.println("번호   /   제목");

			for (Article article : articles) {
				System.out.printf("%4d   /   %s\n", article.id, article.title);
			}
		} else {
			System.out.println("존재하지 않는 명령어입니다");
		}

		return 0;
	}

}