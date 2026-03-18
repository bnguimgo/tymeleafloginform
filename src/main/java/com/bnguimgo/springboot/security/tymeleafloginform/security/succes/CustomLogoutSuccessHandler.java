package com.bnguimgo.springboot.security.tymeleafloginform.security.succes;

import com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutHandler {

    @Override
    public void logout(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response,
                       Authentication authentication) {

        if(null != authentication) {
            CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();
            if(null != authenticatedUser) {
                log.warn("User : {} logout successful, the authentication will be set to false", authenticatedUser.getDisplayName());
            } else {
                log.warn("No authenticated user ");
            }

            authentication.setAuthenticated(false);

        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}