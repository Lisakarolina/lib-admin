package com.example.libadmin.sec;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REDIRECT_URL_SESSION_ATTRIBUTE_NAME = "REDIRECT_URL";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object redirectURLObject = request.getSession().getAttribute(REDIRECT_URL_SESSION_ATTRIBUTE_NAME);
        System.out.println("my redirectURLObject: ");
        System.out.println(redirectURLObject);

        // make sure that target url before authentication is only used if it's not register or the login/logout form (-> for example after first typing in false credentials)
        if(redirectURLObject != null) {
            setDefaultTargetUrl(redirectURLObject.toString());
        }
        else {
            setDefaultTargetUrl("/list");
        }

        request.getSession().removeAttribute(REDIRECT_URL_SESSION_ATTRIBUTE_NAME);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
