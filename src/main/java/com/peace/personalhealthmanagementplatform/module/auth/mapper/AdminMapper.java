package com.peace.personalhealthmanagementplatform.module.auth.mapper;

import com.peace.personalhealthmanagementplatform.module.auth.entity.AdminPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {

    AdminPO selectByUsername(@Param("username") String username);

    AdminPO selectById(@Param("id") Long id);

    String selectRoleKeyByRoleId(@Param("roleId") Long roleId);
}
