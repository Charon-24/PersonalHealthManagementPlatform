package com.peace.personalhealthmanagementplatform.module.auth.controller;

import com.peace.personalhealthmanagementplatform.common.response.ApiResponse;
import com.peace.personalhealthmanagementplatform.module.auth.dto.LoginReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.service.AuthService;
import com.peace.personalhealthmanagementplatform.module.auth.vo.LoginVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/auth")
public class AdminAuthController {

    private final AuthService authService;

    public AdminAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginVO> login(@RequestBody @Valid LoginReqDTO dto) {
        return ApiResponse.success(authService.adminLogin(dto));
    }
}
