package com.github.yuzhian.zero.boot.system.web;

import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.system.service.IPermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "PermissionController", description = "权限控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/permission")
public class PermissionController extends BaseController {
    private final IPermissionService permissionService;
}
