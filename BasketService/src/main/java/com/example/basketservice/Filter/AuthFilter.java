package com.example.basketservice.Filter;

import com.example.basketservice.Client.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Authorization header'ı al
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Token bulunamadı\"}");
            return;
        }

        // 2. Token'ı çıkar
        String token = authHeader.substring(7); // "Bearer " sonrası

        // 3. Token'ı validate et
        Map<String, Object> validationResult = authClient.validateToken(token);

        if (validationResult == null || !(Boolean) validationResult.get("valid")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Token geçersiz\"}");
            return;
        }

        // 4. UserId'yi request'e ekle
        Long userId = ((Number) validationResult.get("userId")).longValue();
        request.setAttribute("userId", userId);
        request.setAttribute("username", validationResult.get("username"));
        request.setAttribute("role", validationResult.get("role"));

        // 5. Devam et
        filterChain.doFilter(request, response);
    }
}
