package com.yjh.jhzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yjh.jhzx.common.exception.YjhException;
import com.yjh.jhzx.manager.mapper.SysUserMapper;
import com.yjh.jhzx.manager.service.SysUserService;
import com.yjh.jhzx.model.dto.system.LoginDto;
import com.yjh.jhzx.model.entity.system.SysUser;
import com.yjh.jhzx.model.vo.common.ResultCodeEnum;
import com.yjh.jhzx.model.vo.system.LoginVo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate <String,String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        //进行验证码的校验及删除
        String captcha = loginDto.getCaptcha();//生成的验证码
        String key = loginDto.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        if (StrUtil.isEmpty(redisCode) || !StrUtil.equals(redisCode,captcha)){
            throw new YjhException(ResultCodeEnum.VALIDATECODE_ERROR);
        }


        redisTemplate.delete("user:validate"+key);

        //登入
        String userName = loginDto.getUserName();

        SysUser sysUser =sysUserMapper.selectUserInfoByUserName(userName);
        if (sysUser == null) {
//            throw new RuntimeException("用户不存在");
            throw new YjhException(ResultCodeEnum.LOGIN_AUTH);
        }

        String database_password = sysUser.getPassword();
        String input_password = loginDto.getPassword();
        String md5InputPassword = DigestUtils.md5DigestAsHex(input_password.getBytes());


        if (!md5InputPassword.equals(database_password)) {
//            throw new RuntimeException("密码错误");
            throw new YjhException(ResultCodeEnum.LOGIN_ERROR);
        }

        String token = UUID.randomUUID().toString().replace("-", "");

        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(loginDto),7, TimeUnit.DAYS);

        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);


        // 返回
        return loginVo;
    }
}
