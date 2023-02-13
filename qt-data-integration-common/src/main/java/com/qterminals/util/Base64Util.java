package com.qterminals.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {
    public static String encodePassword(String plainPassword) {
        if ("".equals(plainPassword)) {
            return plainPassword;
        }

        byte[] decodedBytes = Base64.getEncoder().encode(plainPassword.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes);
    }

    public static String decodePassword(String encodedPassword) {
        if ("".equals(encodedPassword)) {
            return encodedPassword;
        }

        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes);
    }
}
