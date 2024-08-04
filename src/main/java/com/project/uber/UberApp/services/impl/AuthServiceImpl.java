package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.SignupDto;
import com.project.uber.UberApp.dto.UserDto;
import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.entities.enums.Role;
import com.project.uber.UberApp.exceptions.RuntimeConflictException;
import com.project.uber.UberApp.repositories.UserRepository;
import com.project.uber.UberApp.services.AuthService;
import com.project.uber.UberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    @Override
    public String login(String email, String password) {
        return null;
    }

    @Override
    public UserDto singup(SignupDto signupDto) {

        UserEntity chkUser = userRepository.findByEmail(signupDto.getEmail()).orElse(null);

        if(chkUser!=null){
            throw new RuntimeConflictException("Cannot signup, user already exist with email "
                    + signupDto.getEmail());
        }


        UserEntity user = modelMapper.map(signupDto, UserEntity.class);

        user.setRoles(Set.of(Role.RIDER));

        UserEntity saveUser = userRepository.save(user);

//        Creating User related Entities....
        riderService.createNewRider(saveUser);

        return modelMapper.map(saveUser,UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        return null;
    }
}
