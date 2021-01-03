package com.github.yuzhian.zero.boot.resource.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author yuzhian
 */
public interface IMultipartUploadService {

    /**
     * 创建分片上传文件
     */
    String createMultipartUpload();

    /**
     * 上传分片
     *
     * @param uploadId 上传标记符
     * @param part     分片号
     * @param partFile 分片文件
     * @param hash     分片hash
     * @return etag
     */
    String uploadPart(String uploadId, Integer part, MultipartFile partFile, String hash);

    /**
     * 合并文件
     *
     * @param uploadId 上传标记符
     * @return SHA-256 值
     */
    String complete(String uploadId);
}
