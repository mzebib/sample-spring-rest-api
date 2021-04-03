package com.github.mzebib.provisioningapi.security;

import java.security.SecureRandom;

/**
 * @author mzebib
 */
public class TokenGenerator {

    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final int SECURE_TOKEN_LENGTH = 256;

    private static final SecureRandom random = new SecureRandom();

    private static final char[] SYMBOLS = CHARACTERS.toCharArray();

    private static final char[] BUFFER = new char[SECURE_TOKEN_LENGTH];

    public static String generateToken() {
        for (int i = 0; i < BUFFER.length; ++i) {
            BUFFER[i] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        }

        return new String(BUFFER);
    }
}
