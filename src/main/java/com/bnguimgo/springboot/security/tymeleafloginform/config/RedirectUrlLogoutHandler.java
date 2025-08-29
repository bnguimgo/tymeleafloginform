package com.bnguimgo.springboot.security.tymeleafloginform.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PropertiesServiceConfig properties;

    /**
     * Here, we must implement the new logout URL request. We define what URL to send our request to, and set out client_id and logout_uri parameters.
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return UriComponentsBuilder
                .fromUri(URI.create(properties.getDomain() + "/logout"))
                .queryParam("client_id", properties.getClientId())
                .queryParam("logout_uri", properties.getLogoutRedirectUrl())
                //.queryParam("redirect_uri", loginRedirectUrl)
                //.queryParam("response_type", "code")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }
}