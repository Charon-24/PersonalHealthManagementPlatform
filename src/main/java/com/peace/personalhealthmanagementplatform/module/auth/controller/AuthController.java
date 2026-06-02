package com.peace.personalhealthmanagementplatform.module.auth.controller;

import com.peace.personalhealthmanagementplatform.common.auth.UserContext;
import com.peace.personalhealthmanagementplatform.common.constant.HttpHeaderConstants;
import com.peace.personalhealthmanagementplatform.common.response.ApiResponse;
import com.peace.personalhealthmanagementplatform.module.auth.dto.ChangePasswordReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.ForgotPasswordReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.LoginReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.RegisterReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.service.AuthService;
import com.peace.personalhealthmanagementplatform.module.auth.vo.CaptchaVO;
import com.peace.personalhealthmanagementplatform.module.auth.vo.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/captcha")
    public ApiResponse<CaptchaVO> captcha() {
        return ApiResponse.success(authService.generateCaptcha());
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody @Valid RegisterReqDTO dto) {
        authService.register(dto);
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<LoginVO> login(@RequestBody @Valid LoginReqDTO dto) {
        return ApiResponse.success(authService.login(dto));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaderConstants.AUTHORIZATION);
        if (header != null && header.startsWith(HttpHeaderConstants.TOKEN_PREFIX)) {
            String token = header.substring(HttpHeaderConstants.TOKEN_PREFIX.length());
            authService.logout(token);
        }
        return ApiResponse.success();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@RequestBody @Valid ForgotPasswordReqDTO dto) {
        authService.forgotPassword(dto);
        return ApiResponse.success();
    }

    @PutMapping("/password")
    public ApiResponse<Void> changePassword(@RequestBody @Valid ChangePasswordReqDTO dto) {
        authService.changePassword(UserContext.getUserId(), dto);
        return ApiResponse.success();
    }
}
