package com.peace.personalhealthmanagementplatform.module.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaptchaVO {
    private String captchaKey;
    private String captchaImage;
}
