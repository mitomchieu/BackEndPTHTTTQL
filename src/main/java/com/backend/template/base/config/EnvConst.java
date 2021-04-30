package com.backend.template.base.config;


import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.Objects;

public class EnvConst {
    public static String DATABASE_URL;
    public static String DATABASE_USERNAME;
    public static String DATABASE_PASSWORD;
    public static String JWT_SECRET;
    public static Long JWT_EXPIRATION;
    public static Integer SERVER_PORT;
    static {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }
        DATABASE_URL = (System.getenv("DATABASE_URL"));
        DATABASE_USERNAME = (System.getenv("DATABASE_USERNAME"));
        DATABASE_PASSWORD  = (System.getenv("DATABASE_PASSWORD"));
        JWT_SECRET = (System.getenv("JWT_SECRET"));
        JWT_EXPIRATION = Long.parseLong(Objects.requireNonNull(System.getenv("JWT_EXPIRATION")));
        SERVER_PORT = Integer.parseInt(Objects.requireNonNull(System.getenv("SERVER_PORT")));
    }
    EnvConst() {
    }
}
