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
import com.project.uber.UberApp.services.AuthService;
import com.project.uber.UberApp.services.DriverService;
import com.project.uber.UberApp.services.RiderService;
import com.project.uber.UberApp.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthServiceImplTest { // Unit testing

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RiderService riderService;
    @Mock
    private DriverService driverService;
    @Mock
    private WalletService walletService;

    @Spy
    private ModelMapper modelMapper;
    @Spy
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity user;

    @BeforeEach
    void setUp(){
        user = new UserEntity();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setRoles(Set.of(Role.RIDER));

    }

    @Test
    void testLogin_whenSuccess(){ // Happy Case
//        Arrange
        log.info("Testing Happy Login");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateAccessToken(any(UserEntity.class))).thenReturn("access-token");
        when(jwtService.generateRefreshToken(any(UserEntity.class))).thenReturn("refresh-token");

//        Act

        String[] tokens = authService.login(user.getEmail(),user.getPassword());

//        Assert
        assertThat(tokens).hasSize(2);
        assertThat(tokens[0]).isEqualTo("access-token");
        assertThat(tokens[1]).isEqualTo("refresh-token");

        log.info("Happy Login Tested successfully");
    }

    @Test
    void testLogin_whenAuthenticationFails() { // Bad Case
        // Arrange
        log.info("Testing Failed Login");
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            authService.login(user.getEmail(), user.getPassword());
        });

        log.info("Failed Login Tested successfully");
    }

    @Test
    void testSignUp_whenSuccess(){
        log.info("Testing Happy Signup");
//        arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

//        act
        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("test@mail.com");
        signupDto.setPassword("test");

        UserDto userDto = authService.singup(signupDto);

//        assert
        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(signupDto.getEmail());

        verify(riderService).createNewRider(any(UserEntity.class));
        verify(walletService).createNewWallet(any(UserEntity.class));
        log.info("Happy Signup Tested successfully");
    }

    @Test
    void testSignUp_whenUserAlreadyExists() {
        log.info("Testing Signup when user already exists");

        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act & Assert
        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("existing@mail.com");
        signupDto.setPassword("password");

        assertThrows(RuntimeConflictException.class, () -> {
            authService.singup(signupDto);
        });

        verify(userRepository, never()).save(any(UserEntity.class));  // Ensure save is not called
        log.info("Signup with existing user Tested successfully");
    }

    @Test
    void testSignUp_whenRiderServiceFails() {
        log.info("Testing Signup when Rider Service fails");

        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        doThrow(new RuntimeException("Rider service failed")).when(riderService).createNewRider(any(UserEntity.class));

        // Act & Assert
        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("new@mail.com");
        signupDto.setPassword("password");

        assertThrows(RuntimeException.class, () -> {
            authService.singup(signupDto);
        });

        verify(walletService, never()).createNewWallet(any(UserEntity.class));  // Ensure wallet service is not called
        log.info("Signup with Rider Service failure Tested successfully");
    }

    @Test
    void testSignUp_whenWalletServiceFails() {
        log.info("Testing Signup when Wallet Service fails");

        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        doThrow(new RuntimeException("Wallet service failed")).when(walletService).createNewWallet(any(UserEntity.class));

        // Act & Assert
        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("new@mail.com");
        signupDto.setPassword("password");

        assertThrows(RuntimeException.class, () -> {
            authService.singup(signupDto);
        });

        log.info("Signup with Wallet Service failure Tested successfully");
    }

//     ON board New Driver Tests
    @Test
    void testOnboardNewDriver_whenSuccess_thenReturnDriverDto(){
        log.info("Testing onboard new Driver");
        log.info("Testing onboard new Driver - Happy Case");

        // Arrange
        Long userId = 1L;
        String vehicleId = "vehicle123";

        DriverEntity driverEntity = DriverEntity.builder()
                .id(userId)
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        DriverDto driverDto = new DriverDto();
        driverDto.setId(userId);
        driverDto.setVehicleId(vehicleId);
        driverDto.setRating(0.0);
        driverDto.setAvailable(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(driverService.createNewDriver(any(DriverEntity.class))).thenReturn(driverEntity);
        when(modelMapper.map(driverEntity, DriverDto.class)).thenReturn(driverDto); // Map mock entity to DTO

        // Act
        DriverDto result = authService.onboardNewDriver(userId, vehicleId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getVehicleId()).isEqualTo(vehicleId);
        assertThat(result.getRating()).isEqualTo(0.0);
        assertThat(result.getAvailable()).isTrue();

        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        verify(driverService).createNewDriver(any(DriverEntity.class));

        log.info("Onboard new Driver - Happy Case tested successfully");
    }

    @Test
    void testOnboardNewDriver_whenUserNotFound_thenThrowResourceNotFoundException() {
        log.info("Testing onboard new Driver - User Not Found Case");

        // Arrange
        Long userId = 1L;
        String vehicleId = "vehicle123";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            authService.onboardNewDriver(userId, vehicleId);
        });

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(driverService, never()).createNewDriver(any(DriverEntity.class));

        log.info("Onboard new Driver - User Not Found Case tested successfully");
    }

    @Test
    void testOnboardNewDriver_whenUserAlreadyDriver_thenThrowRuntimeConflictException() {
        log.info("Testing onboard new Driver - User Already a Driver Case");

        // Arrange
        Long userId = 1L;
        String vehicleId = "vehicle123";

        // Mock user entity with DRIVER role
        user = new UserEntity();
        user.setId(userId);
        user.setEmail("test@mail.com");
        user.setRoles(Set.of(Role.DRIVER));  // User already has DRIVER role

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(RuntimeConflictException.class, () -> {
            authService.onboardNewDriver(userId, vehicleId);
        });

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(driverService, never()).createNewDriver(any(DriverEntity.class));

        log.info("Onboard new Driver - User Already a Driver Case tested successfully");
    }

    @Test
    void testOnboardNewDriver_whenDriverServiceFails_thenThrowException() {
        log.info("Testing onboard new Driver - Driver Service Failure Case");

        // Arrange
        Long userId = 1L;
        String vehicleId = "vehicle123";

        // Mock user without DRIVER role
        DriverEntity driverEntity = DriverEntity.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(driverService.createNewDriver(any(DriverEntity.class)))
                .thenThrow(new RuntimeException("Driver service failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authService.onboardNewDriver(userId, vehicleId);
        });

        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        verify(driverService).createNewDriver(any(DriverEntity.class));

        log.info("Onboard new Driver - Driver Service Failure Case tested successfully");
    }


}