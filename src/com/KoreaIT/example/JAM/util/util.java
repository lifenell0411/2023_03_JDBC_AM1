package com.KoreaIT.example.JAM.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class util {
	/** 포맷팅 현재 날짜/시간 반환 Str */
	public static String getNowDateTimeStr() {
		// 현재 날짜/시간
		LocalDateTime now = LocalDateTime.now();
		// 포맷팅
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yy년 MM월dd일 HH시mm분ss초"));
		// 포맷팅 현재 날짜/시간 출력

		return formatedNow;
	}

	public static String getNowDateTimeStr(LocalDateTime regDate) {
		// 현재 날짜/시간
		// 포맷팅
		String formatedNow = regDate.format(DateTimeFormatter.ofPattern("yy년MM월dd일 HH시mm분ss초"));
		// 포맷팅 현재 날짜/시간 출력

		return formatedNow;
	}
}