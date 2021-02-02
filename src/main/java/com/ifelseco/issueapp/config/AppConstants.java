package com.ifelseco.issueapp.config;

public class AppConstants {
    public static final String WEB_URL="http://localhost:8181";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
