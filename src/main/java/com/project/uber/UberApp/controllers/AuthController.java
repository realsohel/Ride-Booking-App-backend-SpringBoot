package com.project.uber.UberApp.controllers;


import com.project.uber.UberApp.dto.SignupDto;
import com.project.uber.UberApp.dto.UserDto;
import com.project.uber.UberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public UserDto signUp(@RequestBody SignupDto signupDto){
        return authService.singup(signupDto);
    }
}
