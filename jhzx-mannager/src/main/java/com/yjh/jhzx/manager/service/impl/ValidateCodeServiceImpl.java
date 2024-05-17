package com.yjh.jhzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.yjh.jhzx.manager.service.ValidateCodeService;
import com.yjh.jhzx.model.vo.system.ValidateCodeVo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        //二维码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String codeValue = circleCaptcha.getCode();//验证码的值
        val imageBase64 = circleCaptcha.getImageBase64();
        String key = UUID.randomUUID().toString().replaceAll("_","");
        redisTemplate.opsForValue().set("user:validate"+key,codeValue,5, TimeUnit.MINUTES);

        ValidateCodeVo validateCodeVo= new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }

}
