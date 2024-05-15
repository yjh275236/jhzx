package com.yjh.jhzx.manager.controller;

import com.yjh.jhzx.manager.service.SysUserService;
import com.yjh.jhzx.manager.service.ValidateCodeService;
import com.yjh.jhzx.model.dto.system.LoginDto;
import com.yjh.jhzx.model.vo.common.Result;
import com.yjh.jhzx.model.vo.common.ResultCodeEnum;
import com.yjh.jhzx.model.vo.system.LoginVo;
import com.yjh.jhzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }



    @Operation(summary = "登入的方法")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
}
