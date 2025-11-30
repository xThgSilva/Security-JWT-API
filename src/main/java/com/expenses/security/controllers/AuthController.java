package com.expenses.security.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.security.dto.UserDTO;
import com.expenses.security.entities.User;
import com.expenses.security.exceptions.InvalidCredentialsException;
import com.expenses.security.services.JwtService;
import com.expenses.security.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	@Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public User register(@RequestBody UserDTO user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO user, HttpServletResponse response) {
        Optional<User> userExists = userService.findByEmail(user.getEmail());

        if (userExists.isEmpty() || 
            !userService.verifyPassword(user.getPassword(), userExists.get().getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials.");
        }

        String token = jwtService.gerarToken(userExists.get().getId(), userExists.get().getEmail());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return "Login realizado com sucesso!";
    }
}
