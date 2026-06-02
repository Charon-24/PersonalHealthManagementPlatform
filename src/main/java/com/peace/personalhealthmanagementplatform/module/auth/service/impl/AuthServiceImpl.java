package com.peace.personalhealthmanagementplatform.module.auth.service.impl;

import com.peace.personalhealthmanagementplatform.common.auth.CaptchaStore;
import com.peace.personalhealthmanagementplatform.common.auth.JwtUtil;
import com.peace.personalhealthmanagementplatform.common.auth.TokenBlacklistStore;
import com.peace.personalhealthmanagementplatform.common.constant.RoleConstants;
import com.peace.personalhealthmanagementplatform.common.constant.StatusConstants;
import com.peace.personalhealthmanagementplatform.common.error.ErrorCode;
import com.peace.personalhealthmanagementplatform.common.exception.BusinessException;
import com.peace.personalhealthmanagementplatform.module.auth.dto.ChangePasswordReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.ForgotPasswordReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.LoginReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.dto.RegisterReqDTO;
import com.peace.personalhealthmanagementplatform.module.auth.entity.AdminPO;
import com.peace.personalhealthmanagementplatform.module.auth.entity.UserPO;
import com.peace.personalhealthmanagementplatform.module.auth.mapper.AdminMapper;
import com.peace.personalhealthmanagementplatform.module.auth.mapper.UserMapper;
import com.peace.personalhealthmanagementplatform.module.auth.service.AuthService;
import com.peace.personalhealthmanagementplatform.module.auth.vo.CaptchaVO;
import com.peace.personalhealthmanagementplatform.module.auth.vo.LoginVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final JwtUtil jwtUtil;
    private final CaptchaStore captchaStore;
    private final TokenBlacklistStore tokenBlacklistStore;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(UserMapper userMapper, AdminMapper adminMapper,
                           JwtUtil jwtUtil, CaptchaStore captchaStore,
                           TokenBlacklistStore tokenBlacklistStore) {
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
        this.jwtUtil = jwtUtil;
        this.captchaStore = captchaStore;
        this.tokenBlacklistStore = tokenBlacklistStore;
    }

    @Override
    public CaptchaVO generateCaptcha() {
        return captchaStore.generate();
    }

    @Override
    public void register(RegisterReqDTO dto) {
        verifyCaptcha(dto.getCaptchaKey(), dto.getCaptchaCode());

        if (userMapper.selectByUsername(dto.getUsername()) != null) {
            throw new BusinessException(ErrorCode.CONFLICT, "用户名已存在");
        }
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()
                && userMapper.selectByPhone(dto.getPhone()) != null) {
            throw new BusinessException(ErrorCode.CONFLICT, "手机号已注册");
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()
                && userMapper.selectByEmail(dto.getEmail()) != null) {
            throw new BusinessException(ErrorCode.CONFLICT, "邮箱已注册");
        }

        UserPO user = new UserPO();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setStatus(StatusConstants.ENABLED);
        user.setDeleted(0);
        userMapper.insert(user);
    }

    // PLACEHOLDER_REMAINING_METHODS

    @Override
    public LoginVO login(LoginReqDTO dto) {
        verifyCaptcha(dto.getCaptchaKey(), dto.getCaptchaCode());

        UserPO user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }
        if (user.getStatus() != StatusConstants.ENABLED) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "账号已被禁用");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), RoleConstants.USER);
        return new LoginVO(token, user.getId(), user.getUsername(), RoleConstants.USER);
    }

    @Override
    public LoginVO adminLogin(LoginReqDTO dto) {
        verifyCaptcha(dto.getCaptchaKey(), dto.getCaptchaCode());

        AdminPO admin = adminMapper.selectByUsername(dto.getUsername());
        if (admin == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }
        if (admin.getStatus() != StatusConstants.ENABLED) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "账号已被禁用");
        }
        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户名或密码错误");
        }

        String roleKey = adminMapper.selectRoleKeyByRoleId(admin.getRoleId());
        if (roleKey == null) {
            roleKey = RoleConstants.ADMIN;
        }

        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername(), roleKey);
        return new LoginVO(token, admin.getId(), admin.getUsername(), roleKey);
    }

    // PLACEHOLDER_MORE_METHODS

    @Override
    public void logout(String token) {
        tokenBlacklistStore.add(token);
    }

    @Override
    public void forgotPassword(ForgotPasswordReqDTO dto) {
        verifyCaptcha(dto.getCaptchaKey(), dto.getCaptchaCode());

        UserPO user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }

        userMapper.updatePassword(user.getId(), passwordEncoder.encode(dto.getNewPassword()));
    }

    @Override
    public void changePassword(Long userId, ChangePasswordReqDTO dto) {
        UserPO user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.BUSINESS_VALIDATION_FAILED, "旧密码不正确");
        }

        userMapper.updatePassword(userId, passwordEncoder.encode(dto.getNewPassword()));
    }

    private void verifyCaptcha(String captchaKey, String captchaCode) {
        if (!captchaStore.verify(captchaKey, captchaCode)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "验证码错误或已过期");
        }
    }
}
