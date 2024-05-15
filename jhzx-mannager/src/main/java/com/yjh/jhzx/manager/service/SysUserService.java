package com.yjh.jhzx.manager.service;

import com.yjh.jhzx.model.dto.system.LoginDto;
import com.yjh.jhzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);
}
