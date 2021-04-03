package com.backend.template.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConst {
    public static String DATABASE_URL = (Dotenv.load().get("DATABASE_URL"));
    public static String DATABASE_USERNAME = (Dotenv.load().get("DATABASE_USERNAME"));
    public static String DATABASE_PASSWORD = (Dotenv.load().get("DATABASE_PASSWORD"));
    public static String JWT_SECRET = (Dotenv.load().get("JWT_SECRET"));
    public static Long JWT_EXPIRATION = Long.parseLong(Dotenv.load().get("JWT_EXPIRATION"));
}
