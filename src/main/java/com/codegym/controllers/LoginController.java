package com.codegym.controllers;

import com.codegym.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("user")
public class LoginController {

    @ModelAttribute("user")
    public User initLoginUser() {
        return new User();
    }

    @GetMapping(value={"/","/login"})
    public String login(@CookieValue(value = "setUser", defaultValue = "") String setUser, Model model) {
        model.addAttribute("cookieValue", new Cookie("setUser", setUser));
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@ModelAttribute("user") User user, Model model, @CookieValue(value = "setUser", defaultValue = "") String setUser,
                          HttpServletResponse response) {
        if (user.getEmail().equals("admin@gmail.com") && user.getPassword().equals("12345")) {
            setUser = user.getEmail();

            // create cookie and set it in response
            Cookie cookie = new Cookie("setUser", setUser);
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);

            model.addAttribute("cookieValue",cookie);
            model.addAttribute("message", "Login success. Welcome ");
        } else {
            user.setEmail("");
            Cookie cookie = new Cookie("setUser", setUser);
            model.addAttribute("cookieValue", cookie);
            model.addAttribute("message", "Login failed. Try again.");
        }
        return "login";
    }
}
