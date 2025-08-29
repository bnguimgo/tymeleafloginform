package com.bnguimgo.springboot.security.tymeleafloginform.config;

import com.bnguimgo.springboot.security.tymeleafloginform.security.userdetails.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutHandler {

/*    private final UserCache userCache;

    public CustomLogoutHandler(UserCache userCache) {
        this.userCache = userCache;
    }*/

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        //String userName = UserUtils.getAuthenticatedUserName();
        //userCache.removeUserFromCache("xxx");
        if(null != authentication) {
/*            DefaultOidcUser authenticatedUser = (DefaultOidcUser) authentication.getPrincipal();
            Map<String, Object> authUserAttributes = authenticatedUser.getAttributes();
            String userName = authUserAttributes.get("username").toString();*/
            CustomUserDetails authenticatedUser = (CustomUserDetails) authentication.getPrincipal();
            log.warn("User : {} logout successful, the authentication will be set to false", authenticatedUser.getUsername());
            authentication.setAuthenticated(false);

        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}