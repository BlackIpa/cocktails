package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.JwtResponse;
import com.cocktails.cocktail.dto.SignInRequest;
import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.exception.DuplicateException;
import com.cocktails.cocktail.exception.InvalidCredentialsException;
import com.cocktails.cocktail.handler.GlobalExceptionHandler;
import com.cocktails.cocktail.service.AuthService;
import com.cocktails.cocktail.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @SneakyThrows
    public void registerShouldSaveUser() {
        // given
        val request = SignUpRequest.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("name")
                .password("test1234")
                .build();

        val response = UserResponse.builder().email(request.getEmail()).build();

        // when
        when(userService.registerUser(request)).thenReturn(response);

        // then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(userService).registerUser(any(SignUpRequest.class));
    }

    @Test
    @SneakyThrows
    public void registerShouldThrowWhenEmailExists() {
        // given
        SignUpRequest request = SignUpRequest.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("name")
                .password("test1234")
                .build();

        // when
        when(userService.registerUser(request)).thenThrow(new DuplicateException("Email already exists"));

        // then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Email already exists")));

        verify(userService).registerUser(any(SignUpRequest.class));
    }

    @Test
    @SneakyThrows
    public void loginShouldReturn() {
        // given
        SignInRequest request = SignInRequest.builder()
                .email("test@email.com")
                .password("test1234")
                .build();

        JwtResponse response = JwtResponse.builder().token("string").build();

        // when
        when(authService.authenticateUser(request)).thenReturn(response);

        // then
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(authService).authenticateUser(any(SignInRequest.class));
    }

    @Test
    @SneakyThrows
    public void loginShouldThrowWhenEmailOrPasswordInvalid() {
        // given
        SignInRequest request = SignInRequest.builder()
                .email("test@email.com")
                .password("test")
                .build();

        // when
        when(authService.authenticateUser(request)).thenThrow(new InvalidCredentialsException("Wrong email"));

        // then
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Wrong email")));

        verify(authService).authenticateUser(any(SignInRequest.class));
    }

}