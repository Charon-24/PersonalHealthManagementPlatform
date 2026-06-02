package com.peace.personalhealthmanagementplatform.module.auth.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPO {
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String realName;
    private Integer age;
    private String sex;
    private String avatar;
    private Integer status;
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
