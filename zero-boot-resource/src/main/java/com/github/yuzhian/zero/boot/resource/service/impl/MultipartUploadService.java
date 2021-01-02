package com.github.yuzhian.zero.boot.resource.service.impl;

import com.github.yuzhian.zero.boot.properties.ResourceProperties;
import com.github.yuzhian.zero.boot.resource.service.IMultipartUploadService;
import com.github.yuzhian.zero.boot.support.ApiException;
import com.github.yuzhian.zero.boot.util.QETag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
        System.out.println(dir.getAbsolutePath());
        if (dir.exists()) {
            return createMultipartUpload();
        } else if (dir.mkdir()) {
            return uploadId;
        }
        throw new ApiException("PART_TASK_CREATE_FAILED", "分片任务创建失败");
    }

    @Override
    @SneakyThrows({IOException.class})
    public String uploadPart(String uploadId, Integer part, MultipartFile file, String hash) {
        File dir = new File(resourceProperties.getLocation().getParts(), uploadId);
        if (!dir.exists()) {
            throw new ApiException("PART_NOT_INIT", "未初始化此分片");
        }
        Files.write(Path.of(resourceProperties.getLocation().getParts(), uploadId, String.valueOf(part)),
                file.getBytes(), StandardOpenOption.CREATE);
        return new QETag().calcETag(file.getInputStream(), file.getSize());
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows({IOException.class, NoSuchAlgorithmException.class})
    public String complete(String uploadId) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // 输出文件
        File target = Path.of(resourceProperties.getLocation().getMerge(), uploadId).toFile();
        try (FileChannel targetChannel = new FileOutputStream(target, true).getChannel()) {
            File dir = new File(resourceProperties.getLocation().getParts(), uploadId);
            byte[] buffer = new byte[1024 * 8];

            // 遍历分片, 计算hash, 合并数据, 删除分片
            Files.walk(dir.toPath()).filter(path -> path.toFile().isFile()).sorted(Comparator.comparing(p -> Integer.valueOf(p.toFile().getName()))).forEach(part -> {
                File source = part.toFile();
                try (FileInputStream fis = new FileInputStream(source);
                     FileChannel sourceChannel = fis.getChannel();
                     DigestInputStream dis = new DigestInputStream(fis, digest)) {
                    for (; ; ) {
                        if (dis.read(buffer) == -1) break;
                    }
                    targetChannel.transferFrom(sourceChannel, targetChannel.size(), sourceChannel.size()); // 合并
                } catch (IOException ignore) {
                } finally {
                    source.delete();
                }
            });
            dir.delete();
        }

        // 计算文件hash, 检查重复, 重命名文件
        String sha256 = new BigInteger(1, digest.digest()).toString(16);
        if (new File(resourceProperties.getLocation().getStore(), sha256).exists()) {
            new File(resourceProperties.getLocation().getMerge(), uploadId).delete();
        } else {
            new File(resourceProperties.getLocation().getMerge(), uploadId).renameTo(new File(resourceProperties.getLocation().getStore(), sha256));
        }
        return sha256;
    }
}
