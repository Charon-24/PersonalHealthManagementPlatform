package com.peace.personalhealthmanagementplatform.module.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ForgotPasswordReqDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 8, max = 64, message = "密码长度8-64位")
    private String newPassword;

    @NotBlank(message = "验证码标识不能为空")
    private String captchaKey;

    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
}
