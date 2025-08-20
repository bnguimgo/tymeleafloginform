package com.bnguimgo.springboot.security.tymeleafloginform.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Cognito has a custom logout url.
 * See more information <a href="https://docs.aws.amazon.com/cognito/latest/developerguide/logout-endpoint.html">here</a>.
 */
@Component
public class RedirectUrlLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    /**
     * The domain of your user pool.
     */
    private final String domain = "https://taet.auth.eu-west-3.amazoncognito.com";

    /**
     * An allowed callback URL.
     */
    private final String logoutRedirectUrl = "http://localhost:8092/loginform/logout";

    /**
     * The ID of your User Pool Client.
     */
    private final String userPoolClientId = "5imvs0q0817avlstrm6dqur1cm";

    private final String loginRedirectUrl = "http://localhost:8092/loginform/login/oauth2/code/cognito";


    /**
     * Here, we must implement the new logout URL request. We define what URL to send our request to, and set out client_id and logout_uri parameters.
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return UriComponentsBuilder
                .fromUri(URI.create(domain + "/logout"))
                .queryParam("client_id", userPoolClientId)
                .queryParam("logout_uri", logoutRedirectUrl)
                //.queryParam("redirect_uri", loginRedirectUrl)
                //.queryParam("response_type", "code")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }
}