package com.speer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.speer.configuration.security.JwtProvider;
import com.speer.dto.*;
import com.speer.entity.User;
import com.speer.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserServiceImpl userService;

    private UserReqDto validUserReqDto;
    private UserReqDto invalidUserReqDto;
    private UserCredentialDto userCredentialDto;
    private User user;

    @BeforeEach
    void setUp() {
        validUserReqDto = new UserReqDto("Emmanuel", "Olaleru", "delexyz@gmail.com", "password123");
        invalidUserReqDto = new UserReqDto("", "", "delexyz@gmail.com", "password123");
        userCredentialDto = new UserCredentialDto("delexyz@gmail.com", "password123");
        user = new User(validUserReqDto);
    }

    @Test
    void signUp_withValidUser_shouldReturnSuccess() {
        when(userRepository.findByEmail(validUserReqDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(validUserReqDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseDto response = userService.signUp(validUserReqDto);

        assertTrue(response.getSuccessful());
        assertEquals("User created successfully", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void signUp_withInvalidUser_shouldReturnFailure() {
        ResponseDto response = userService.signUp(invalidUserReqDto);

        assertFalse(response.getSuccessful());
        assertEquals("First name is required", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void signUp_withExistingEmail_shouldReturnFailure() {
        when(userRepository.findByEmail(validUserReqDto.getEmail())).thenReturn(Optional.of(user));

        ResponseDto response = userService.signUp(validUserReqDto);

        assertFalse(response.getSuccessful());
        assertEquals("User already exists with this email", response.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_withValidCredentials_shouldReturnSuccess() {
        when(userRepository.findByEmail(userCredentialDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userCredentialDto.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtProvider.generateToken(user.getEmail(), user.getId())).thenReturn("jwtToken");

        AuthUserResDto response = userService.login(userCredentialDto);

        assertTrue(response.getAuthenticated());
        assertEquals("Login successful", response.getStatus());
        assertNotNull(response.getJwt());
        verify(jwtProvider, times(1)).generateToken(user.getEmail(), user.getId());
    }

    @Test
    void login_withInvalidEmail_shouldReturnFailure() {
        when(userRepository.findByEmail(userCredentialDto.getEmail())).thenReturn(Optional.empty());

        AuthUserResDto response = userService.login(userCredentialDto);

        assertFalse(response.getAuthenticated());
        assertEquals("User not found!", response.getStatus());
        assertNull(response.getJwt());
        verify(jwtProvider, never()).generateToken(anyString(), anyLong());
    }

    @Test
    void login_withInvalidPassword_shouldReturnFailure() {
        when(userRepository.findByEmail(userCredentialDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userCredentialDto.getPassword(), user.getPassword())).thenReturn(false);

        AuthUserResDto response = userService.login(userCredentialDto);

        assertFalse(response.getAuthenticated());
        assertEquals("Incorrect password", response.getStatus());
        assertNull(response.getJwt());
        verify(jwtProvider, never()).generateToken(anyString(), anyLong());
    }
}