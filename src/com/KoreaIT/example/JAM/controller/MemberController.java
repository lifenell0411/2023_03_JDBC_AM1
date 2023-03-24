package com.KoreaIT.example.JAM.controller;

import com.KoreaIT.example.JAM.container.Container;
import com.KoreaIT.example.JAM.dto.Member;
import com.KoreaIT.example.JAM.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController() {
		memberService = Container.memberService;
	}

	public void login(String cmd) {
		String loginId = null;
		String loginPw = null;

		System.out.println("==로그인==");

		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup == false) {
				System.out.println(loginId + "는(은) 존재하지 않습니다");
				continue;
			}

			break;
		}

		Member member = memberService.getMemberByLoginId(loginId);

		int maxTryCount = 3;
		int tryCount = 0;

		while (true) {
			if (tryCount >= maxTryCount) {
				System.out.println("비밀번호를 확인하고 다시 입력해주세요");
				break;
			}

			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0) {
				tryCount++;
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}

			if (member.loginPw.equals(loginPw) == false) {
				tryCount++;
				System.out.println("비밀번호가 일치하지 않습니다");
				continue;
			}

			System.out.println(member.name + " 회원님, 환영합니다");

			Container.session.login(member);

			break;
		}

	}

	public void doJoin(String cmd) {
		if (Container.session.isLogined() == true) {
			System.out.println("로그아웃 후 이용해주세요");
			return;
		}

		String loginId = null;
		String loginPw = null;
		String loginPwConfirm = null;
		String name = null;

		System.out.println("==회원 가입==");

		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup) {
				System.out.println(loginId + "는(은) 이미 사용중인 아이디입니다");
				continue;
			}

			break;
		}

		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}

			boolean loginPwCheck = true;

			while (true) {
				System.out.printf("비밀번호 확인 : ");
				loginPwConfirm = sc.nextLine().trim();

				if (loginPwConfirm.length() == 0) {
					System.out.println("비밀번호 확인을 입력해주세요");
					continue;
				}

				if (loginPw.equals(loginPwConfirm) == false) {
					System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
					loginPwCheck = false;
					break;
				}
				break;
			}
			if (loginPwCheck) {
				break;
			}
		}

		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine();

			if (name.length() == 0) {
				System.out.println("이름을 입력해주세요");
				continue;
			}
			break;
		}

		int id = memberService.doJoin(loginId, loginPw, name);

		System.out.println(name + " 회원님, 가입 되었습니다");

	}

	public void showProfile(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 상태가 아닙니다");
		} else {
			System.out.println(Container.session.loginedMember);
		}
	}

	public void logout(String cmd) {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		} else {
			Container.session.logout();
		}
	}

}