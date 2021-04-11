package com.backend.template.base.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public class EnvConst {
    public static String DATABASE_URL;
    public static String DATABASE_USERNAME;
    public static String DATABASE_PASSWORD;
    public static String JWT_SECRET;
    public static Long JWT_EXPIRATION;
    public static Integer SERVER_PORT;
    static {
        DATABASE_URL = (Dotenv.load().get("DATABASE_URL"));
        DATABASE_USERNAME = (Dotenv.load().get("DATABASE_USERNAME"));
        DATABASE_PASSWORD  = (Dotenv.load().get("DATABASE_PASSWORD"));
        JWT_SECRET = (Dotenv.load().get("JWT_SECRET"));
        JWT_EXPIRATION = Long.parseLong(Objects.requireNonNull(Dotenv.load().get("JWT_EXPIRATION")));
        SERVER_PORT = Integer.parseInt(Objects.requireNonNull(Dotenv.load().get("SERVER_PORT")));
    }
    EnvConst() {
    }
}
