package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.SignupDto;
import com.project.uber.UberApp.dto.UserDto;
import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.entities.enums.Role;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.exceptions.RuntimeConflictException;
import com.project.uber.UberApp.repositories.UserRepository;
import com.project.uber.UberApp.security.JWTService;
import com.project.uber.UberApp.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final DriverService driverService;
    private final WalletService walletService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final EmailSenderService emailSenderService;
    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        String[] tokens = {accessToken, refreshToken};
        return tokens;
    }

    @Override
    @Transactional
    public UserDto singup(SignupDto signupDto) {

        UserEntity chkUser = userRepository.findByEmail(signupDto.getEmail()).orElse(null);

        if(chkUser!=null){
            throw new RuntimeConflictException("Cannot signup, user already exist with email "
                    + signupDto.getEmail());
        }


        UserEntity user = modelMapper.map(signupDto, UserEntity.class);

        user.setRoles(Set.of(Role.RIDER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity saveUser = userRepository.save(user);

//        Creating User related Entities....
        riderService.createNewRider(saveUser);
        walletService.createNewWallet(saveUser);

        return modelMapper.map(saveUser,UserDto.class);
    }

    @Override
    @Transactional
    public DriverDto onboardNewDriver(Long userId, String vehicleId) {
        log.info("Onboarding new Driver.");

        UserEntity user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id: "+ userId)
        );

        if(user.getRoles().contains(Role.DRIVER)){
            throw new RuntimeConflictException("User is already a Driver.");
        }

        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);

        DriverEntity driver = DriverEntity
                .builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        DriverEntity savedDriver = driverService.createNewDriver(driver);
        emailSenderService.sendEmail(user.getEmail(),
                "Boarding as a new driver",
                "Dear user, \n Congratulations !! You have now become a Driver-partner with our RideON application. \n" +
                        "You can now a accept a new ride from the riders and can earn money.\n" +
                        "All the best for your future rides.\n" +
                        "Regards,\n Team RideOn. ");

        return modelMapper.map(savedDriver,DriverDto.class);
    }

    @Override
    @Transactional
    public String deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                ()->  new ResourceNotFoundException("User not found with Id: " + userId)
        );

        user.setRoles(null);
        userRepository.save(user);
        userRepository.deleteById(userId);

        return "User deleted Successfully.";
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with Id: "+userId)
        );

        return jwtService.generateAccessToken(user);
    }
}
