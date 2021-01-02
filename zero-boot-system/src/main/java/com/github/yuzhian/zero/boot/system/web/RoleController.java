package com.github.yuzhian.zero.boot.system.web;

import com.github.yuzhian.zero.boot.support.BaseController;
import com.github.yuzhian.zero.boot.system.service.IRoleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "角色控制器")
@RequiredArgsConstructor
@RequestMapping(value = "/role")
public class RoleController extends BaseController {
    private final IRoleService roleService;
}
