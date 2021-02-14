package com.github.yuzhian.zero.boot.resource.service.impl;

import com.github.yuzhian.zero.boot.properties.ResourceProperties;
import com.github.yuzhian.zero.boot.resource.service.IMultipartUploadService;
import com.github.yuzhian.zero.boot.exception.ApiException;
import com.github.yuzhian.zero.boot.util.DigestUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.UUID;

/**
 * @author yuzhian
 */
@Service
@RequiredArgsConstructor
public class MultipartUploadService implements IMultipartUploadService {
    private final ResourceProperties resourceProperties;

    @Override
    public String createMultipartUpload() {
        String uploadId = UUID.randomUUID().toString().replaceAll("-", "");
        File dir = new File(resourceProperties.getLocation().getParts(), uploadId);
        if (dir.exists()) {
            return createMultipartUpload();
        } else if (dir.mkdir()) {
            return uploadId;
        }
        throw new ApiException("UPLOAD_INIT_FAILED", "分片上传初始化失败");
    }

    @Override
    @SneakyThrows({IOException.class})
    public String uploadPart(String uploadId, Integer part, MultipartFile partFile, String clientHash) {
        File dir = new File(resourceProperties.getLocation().getParts(), uploadId);
        if (!dir.exists()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "UPLOAD_NOT_INIT", "未初始化此分片上传操作");
        }
        String serverHash = DigestUtils.hex(partFile.getInputStream(), "SHA-256");
        if (Strings.isNotBlank(clientHash) && !serverHash.equals(clientHash)) {
            throw new ApiException(HttpStatus.CONFLICT, "HASH_VERIFICATION_FAILED", "哈希校验失败, 请确保使用的方式是SHA-256");
        }
        Files.write(Path.of(resourceProperties.getLocation().getParts(), uploadId, String.valueOf(part)), partFile.getBytes(), StandardOpenOption.CREATE);
        return serverHash;
    }

    // TODO
    //  1. 4GB耗时70S
    //  2. 过期上传
    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows({IOException.class, NoSuchAlgorithmException.class})
    public String complete(String uploadId) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        File parts = new File(resourceProperties.getLocation().getParts(), uploadId); // 分片目录
        File merge = new File(resourceProperties.getLocation().getMerge(), uploadId);
        // 输出文件
        try (FileChannel targetChannel = new FileOutputStream(merge, true).getChannel()) {
            byte[] buffer = new byte[1 << 22];

            // 遍历分片, 计算hash, 合并数据, 删除分片
            Files.walk(parts.toPath()).filter(path -> path.toFile().isFile()).sorted(Comparator.comparing(p -> Integer.valueOf(p.toFile().getName()))).forEach(part -> {
                File source = part.toFile();
                try (FileChannel sourceChannel = new FileInputStream(source).getChannel(); // !使用两个独立的输入流
                     DigestInputStream dis = new DigestInputStream(new FileInputStream(source), digest)) {
                    for (; ; ) {
                        if (dis.read(buffer) == -1) break;
                    }
                    targetChannel.transferFrom(sourceChannel, targetChannel.size(), sourceChannel.size()); // 合并
                } catch (IOException ignore) {
                } finally {
                    source.delete();
                }
            });
            parts.delete();
        }

        // 计算文件hash, 检查重复, 重命名文件
        String sha256 = new BigInteger(1, digest.digest()).toString(16);
        File store = new File(resourceProperties.getLocation().getStore(), sha256);
        if (store.exists()) {
            merge.delete();
        } else {
            merge.renameTo(store);
        }
        return sha256;
    }
}
