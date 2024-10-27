package com.project.uber.UberApp.services;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.SignupDto;
import com.project.uber.UberApp.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String[] login(String email, String password);
    UserDto singup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId, String vehicleId);

    String deleteUser(Long userId);

    String refreshToken(String refreshToken);
}
