package com.example.libadmin.controller;

import com.example.libadmin.domain.User;
import com.example.libadmin.sec.CustomAuthenticationSuccessHandler;
import com.example.libadmin.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.example.libadmin.sec.CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME;

@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        //request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME, referer);

        //request.getSession().setAttribute(REDIRECT_URL_SESSION_ATTRIBUTE_NAME, referer);
        return "login";
    }

    @GetMapping("/guest-login")
    public String guestLogin(HttpServletRequest request) {
        try {
            request.login("user@email.com","password");
        } catch(ServletException ex) {
            // since auth data is hardcoded, and I'm in control of the db, no special error handling needed
            System.out.println(ex);
            return "login";
        }
        return "redirect:/list";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if( bindingResult.hasErrors() ) {
            // show validation errors
            model.addAttribute("user",user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "register";
        } else {
            // Register new user
            User newUser = userService.register(user);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success",true);
            return "redirect:/register";
        }
    }
}
