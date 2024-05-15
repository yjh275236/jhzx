package com.yjh.jhzx.manager.mapper;

import com.yjh.jhzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);
}
