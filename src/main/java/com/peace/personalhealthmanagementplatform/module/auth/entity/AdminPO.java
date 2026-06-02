package com.peace.personalhealthmanagementplatform.module.auth.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminPO {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String sex;
    private String email;
    private String avatar;
    private Long roleId;
    private Integer status;
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
