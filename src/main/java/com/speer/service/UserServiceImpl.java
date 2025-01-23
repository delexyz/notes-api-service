package com.speer.service;

import com.speer.configuration.security.JwtProvider;
import com.speer.dto.*;
import com.speer.entity.User;
import com.speer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public ResponseDto signUp(UserReqDto userReqDto) {
        if(userReqDto.isValid().containsKey(false)) {
            return new ResponseDto(false, userReqDto.isValid().get(false), userReqDto);
        }
        Optional<User> optionalUser = userRepository.findByEmail(userReqDto.getEmail());
        if(optionalUser.isPresent()) {
            return new ResponseDto(false, "User already exists with this email", userReqDto);
        }
        String encodedPassword = passwordEncoder.encode(userReqDto.getPassword());
        userReqDto.setPassword(encodedPassword);
        User user = new User(userReqDto);
        userRepository.save(user);
        UserResDto userResDto = new UserResDto(user);
        return new ResponseDto(true, "User created successfully", userResDto);
    }

    @Override
    public AuthUserResDto login(UserCredentialDto userCredentialDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialDto.getEmail());
        if(optionalUser.isEmpty()) {
            return new AuthUserResDto(false, "User not found!", null, null);
        }
        User user = optionalUser.get();
        boolean isPasswordValid = passwordEncoder.matches(userCredentialDto.getPassword(), user.getPassword());
        if(!isPasswordValid) {
            return new AuthUserResDto(false, "Incorrect password", null, null);
        }
        String jwtToken = jwtProvider.generateToken(user.getEmail(), user.getId());
        UserResDto userResDto = new UserResDto(user);
        return new AuthUserResDto(true, "Login successful", jwtToken, userResDto);
    }
}
