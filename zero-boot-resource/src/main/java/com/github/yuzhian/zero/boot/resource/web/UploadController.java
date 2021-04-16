package com.github.yuzhian.zero.boot.resource.web;

import com.github.yuzhian.zero.boot.resource.service.IMultipartUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yuzhian
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "UploadController", description = "上传控制器")
@RequestMapping(value = "/upload")
public class UploadController {
    private final IMultipartUploadService multipartUploadService;

    @PostMapping("/part")
    @Operation(summary = "创建分片上传")
    public ResponseEntity<String> createMultipartUpload() {
        return ResponseEntity.ok(multipartUploadService.createMultipartUpload());
    }

    @Operation(summary = "上传分片")
    @PatchMapping(value = {"/part/{uploadId}/{part}"})
    @Parameters({
            @Parameter(name = "uploadId", description = "上传操作标记", required = true, in = ParameterIn.PATH),
            @Parameter(name = "part", description = "分片序号(1-999)", required = true, in = ParameterIn.PATH),
            @Parameter(name = "partFile", description = "分片文件", required = true, in = ParameterIn.DEFAULT),
            @Parameter(name = "hash", description = "分片hash", required = true, in = ParameterIn.DEFAULT),
    })
    public ResponseEntity<Void> uploadPart(@PathVariable String uploadId, @PathVariable Integer part, MultipartFile partFile, @RequestParam(required = false) String hash) {
        String etag = multipartUploadService.uploadPart(uploadId, part, partFile, hash);
        return ResponseEntity.ok().eTag(etag).build();
    }

    @Operation(summary = "完成分片上传")
    @PutMapping(value = "/part/{uploadId}")
    @Parameter(name = "uploadId", description = "文件hash值(SHA-256)", required = true, in = ParameterIn.PATH)
    public ResponseEntity<String> complete(@PathVariable String uploadId) {
        String hash = multipartUploadService.complete(uploadId);
        return ResponseEntity.ok(hash);
    }
}
