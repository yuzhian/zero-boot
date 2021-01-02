package com.github.yuzhian.zero.boot.resource.web;

import com.github.yuzhian.zero.boot.resource.service.IMultipartUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yuzhian
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "上传控制器")
@RequestMapping(value = "/upload")
public class UploadController {
    private final IMultipartUploadService multipartUploadService;

    @PostMapping("/part")
    @ApiOperation(value = "创建分片上传")
    public ResponseEntity<String> createMultipartUpload() {
        return ResponseEntity.ok(multipartUploadService.createMultipartUpload());
    }

    @ApiOperation(value = "上传分片")
    @PatchMapping(value = {"/part/{uploadId}/{part}"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uploadId", value = "上传操作标记", required = true, paramType = "path", dataType = "java.lang.String"),
            @ApiImplicitParam(name = "part", value = "分片序号", required = true, paramType = "path", dataType = "java.lang.Integer"),
    })
    public ResponseEntity<Void> uploadPart(@PathVariable String uploadId, @PathVariable Integer part, MultipartFile file, @RequestParam(required = false) String hash) {
        String etag = multipartUploadService.uploadPart(uploadId, part, file, hash);
        return ResponseEntity.ok().eTag(etag).build();
    }

    @ApiOperation(value = "完成分片上传")
    @PutMapping(value = "/part/{uploadId}")
    @ApiImplicitParam(name = "uploadId", value = "文件hash值(SHA-256)", required = true, paramType = "path", dataType = "java.lang.String")
    public ResponseEntity<String> complete(@PathVariable String uploadId) {
        String hash = multipartUploadService.complete(uploadId);
        return ResponseEntity.ok(hash);
    }
}
