package com.project.uber.UberApp.services;

import com.project.uber.UberApp.entities.UserEntity;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userRepository.findByEmail(username).orElseThrow(
                ()->new BadCredentialsException("User not Found with email "+ username)
        );
    }

    public UserEntity getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with Id: " + id)
        );
    }
}
