package com.peace.personalhealthmanagementplatform.module.auth.service;

import com.peace.personalhealthmanagementplatform.module.auth.dto.ChangePasswordReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.ForgotPasswordReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.LoginReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.RegisterReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.vo.CaptchaVO;
import com.peace.personalhealthmanagementplatform.module.auth.vo.LoginVO;

public interface AuthService {

    CaptchaVO generateCaptcha();

    void register(RegisterReqDTO dto);

    LoginVO login(LoginReqDTO dto);

    LoginVO adminLogin(LoginReqDTO dto);

    void logout(String token);

    void forgotPassword(ForgotPasswordReqDTO dto);

    void changePassword(Long userId, ChangePasswordReqDTO dto);
}
