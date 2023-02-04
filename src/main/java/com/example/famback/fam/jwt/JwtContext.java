package com.example.famback.fam.jwt;
public class JwtContext {
	protected static String ACCESS_SECRET_KEY = "linlintkfkdgo";
	protected static String REFRESH_SECRET_KEY = "miocatmiocat";
	public static final String REFRESH_COOKIE_KEY = "ayzotflsfls";
	protected static final long ACCESS_EXPIRATION_TIME = 4 * 60 * 60 * 1000L; //유효시간 4시간
	protected static final long REFRESH_EXPIRATION_TIME = 30 * 24 * 60 * 60 * 1000L; // 유효시간 30일
}
