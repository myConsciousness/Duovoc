package dev.app.ks.thinkit.duovoc.framework;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : CipherHandler.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/10/19
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 文字列に対する暗号化および復号化を行う機能を定義したユーティリティクラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class CipherHandler {

    /**
     * 当該クラスで扱う暗号化形式。
     */
    private static final String ALGORITHM = "AES";

    /**
     * 暗号に対するサルト処理を行う際のキー。
     */
    private static final String SALT_KEYS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * 暗号のサイズ。
     */
    private static final int KEY_SIZE = 16;

    /**
     * 当該クラスのコンストラクタ。
     */
    private CipherHandler() {
    }

    /**
     * 入力として渡された文字列に対してAES形式で暗号化を行います。
     *
     * @param source 暗号化対象の文字列。
     * @param key    暗号化時に使用するキー。
     * @return 暗号文。
     */
    public static String encrypt(final String source, final String key) {

        String result = "";

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
            result = new String(Base64.getEncoder().encode(cipher.doFinal(source.getBytes())));

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 入力として渡されたAES形式の暗号文に対して復号化を行います。
     *
     * @param source 復号化対象の文字列。
     * @param key    復号化時に使用するキー。
     * @return 平文。
     */
    public static String decrypt(final String source, final String key) {

        String result = "";

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
            result = new String(cipher.doFinal(Base64.getDecoder().decode(source.getBytes())));

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 暗号化を行う際に使用する秘密鍵を生成します。
     *
     * @return 秘密鍵。
     */
    public static String generateSecretKey() {

        final StringBuilder salt = new StringBuilder();
        final Random rnd = new Random();
        final int keysLength = SALT_KEYS.length();

        while (salt.length() < KEY_SIZE) {
            final int index = (int) (rnd.nextFloat() * keysLength);
            salt.append(SALT_KEYS.charAt(index));
        }

        return salt.toString();
    }
}
