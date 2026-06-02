package com.peace.personalhealthmanagementplatform.module.auth.mapper;

import com.peace.personalhealthmanagementplatform.module.auth.entity.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    UserPO selectByUsername(@Param("username") String username);

    UserPO selectByPhone(@Param("phone") String phone);

    UserPO selectByEmail(@Param("email") String email);

    UserPO selectById(@Param("id") Long id);

    int insert(UserPO user);

    int updatePassword(@Param("id") Long id, @Param("password") String password);
}
