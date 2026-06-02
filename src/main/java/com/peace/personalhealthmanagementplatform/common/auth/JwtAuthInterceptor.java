package com.peace.personalhealthmanagementplatform.common.auth;

import com.peace.personalhealthmanagementplatform.common.constant.HttpHeaderConstants;
import com.peace.personalhealthmanagementplatform.common.error.ErrorCode;
import com.peace.personalhealthmanagementplatform.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final TokenBlacklistStore tokenBlacklistStore;

    public JwtAuthInterceptor(JwtUtil jwtUtil, TokenBlacklistStore tokenBlacklistStore) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistStore = tokenBlacklistStore;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader(HttpHeaderConstants.AUTHORIZATION);
        if (header == null || !header.startsWith(HttpHeaderConstants.TOKEN_PREFIX)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录或token缺失");
        }

        String token = header.substring(HttpHeaderConstants.TOKEN_PREFIX.length());

        if (tokenBlacklistStore.isBlacklisted(token)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "token已失效");
        }

        if (!jwtUtil.isTokenValid(token)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "token无效或已过期");
        }

        UserContext.set(new UserContext.UserContextInfo(
                jwtUtil.getUserId(token),
                jwtUtil.getUsername(token),
                jwtUtil.getRole(token)
        ));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserContext.clear();
    }
}
