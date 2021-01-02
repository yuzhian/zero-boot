package com.github.yuzhian.zero.boot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author yuzhian
 */
public class DigestUtils {
    public static byte[] digest(File file, String algorithm) {
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance(algorithm))) {
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

    public static String hex(File file, String algorithm) {
        return new BigInteger(1, digest(file, algorithm)).toString(16);
    }
}
