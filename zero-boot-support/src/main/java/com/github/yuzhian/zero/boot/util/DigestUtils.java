package com.github.yuzhian.zero.boot.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author yuzhian
 */
public class DigestUtils {
    public static byte[] digest(InputStream stream, String algorithm) {
        try (DigestInputStream dis = new DigestInputStream(stream, MessageDigest.getInstance(algorithm))) {
            byte[] buffer = new byte[1024 * 4];
            for (; ; ) {
                if (dis.read(buffer) == -1) break;
            }
            return dis.getMessageDigest().digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static String hex(InputStream stream, String algorithm) {
        return new BigInteger(1, digest(stream, algorithm)).toString(16);
    }
}
