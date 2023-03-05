package com.gigmanager.config;

public class AuthenticationConfigConstants {
    //constants which we will need to refer in security specific classes
    public static final String SECRET = "No_Secrets_Here";
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user";
}
