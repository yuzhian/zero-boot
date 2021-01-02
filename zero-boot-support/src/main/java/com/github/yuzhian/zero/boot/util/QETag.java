package com.github.yuzhian.zero.boot.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class QETag {
    private static final int CHUNK_SIZE = 1 << 22;

    public byte[] sha1(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("sha1");
        return mDigest.digest(data);
    }

    public String urlSafeBase64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data).replace('+', '-').replace('/', '_');
    }

    public String calcETag(InputStream inputStream, long size) {
        String etag = "";
        try (inputStream) {

            if (size <= CHUNK_SIZE) {
                byte[] fileData = new byte[(int) size];
                //noinspection ResultOfMethodCallIgnored
                inputStream.read(fileData, 0, (int) size);
                byte[] sha1Data = sha1(fileData);
                int sha1DataLen = sha1Data.length;
                byte[] hashData = new byte[sha1DataLen + 1];
                System.arraycopy(sha1Data, 0, hashData, 1, sha1DataLen);
                hashData[0] = 0x16;
                etag = urlSafeBase64Encode(hashData);
            } else {
                int chunkCount = (int) (size / CHUNK_SIZE);
                if (size % CHUNK_SIZE != 0) {
                    chunkCount += 1;
                }
                byte[] allSha1Data = new byte[0];
                for (int i = 0; i < chunkCount; i++) {
                    byte[] chunkData = new byte[CHUNK_SIZE];
                    int bytesReadLen = inputStream.read(chunkData, 0, CHUNK_SIZE);
                    byte[] bytesRead = new byte[bytesReadLen];
                    System.arraycopy(chunkData, 0, bytesRead, 0, bytesReadLen);
                    byte[] chunkDataSha1 = sha1(bytesRead);
                    byte[] newAllSha1Data = new byte[chunkDataSha1.length
                            + allSha1Data.length];
                    System.arraycopy(allSha1Data, 0, newAllSha1Data, 0,
                            allSha1Data.length);
                    System.arraycopy(chunkDataSha1, 0, newAllSha1Data,
                            allSha1Data.length, chunkDataSha1.length);
                    allSha1Data = newAllSha1Data;
                }
                byte[] allSha1DataSha1 = sha1(allSha1Data);
                byte[] hashData = new byte[allSha1DataSha1.length + 1];
                System.arraycopy(allSha1DataSha1, 0, hashData, 1,
                        allSha1DataSha1.length);
                hashData[0] = (byte) 0x96;
                etag = urlSafeBase64Encode(hashData);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return etag;
    }
}