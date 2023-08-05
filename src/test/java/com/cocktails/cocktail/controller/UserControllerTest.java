package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.LogInRequest;
import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.JwtAuthenticationResponse;
import com.cocktails.cocktail.exception.DuplicateException;
import com.cocktails.cocktail.exception.InvalidCredentialsException;
import com.cocktails.cocktail.handler.GlobalExceptionHandler;
import com.cocktails.cocktail.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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
    private AuthenticationService authenticationService;

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
        SignUpRequest request = SignUpRequest.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("name")
                .password("test")
                .build();

        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder().token("string").build();

        // when
        when(authenticationService.signup(request)).thenReturn(response);

        // then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(authenticationService).signup(any(SignUpRequest.class));
    }

    @Test
    @SneakyThrows
    public void registerShouldThrowWhenEmailExists() {
        // given
        SignUpRequest request = SignUpRequest.builder()
                .email("test@email.com")
                .firstName("test")
                .lastName("name")
                .password("test")
                .build();

        // when
        when(authenticationService.signup(request)).thenThrow(new DuplicateException("Email already exists"));

        // then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Email already exists")));

        verify(authenticationService).signup(any(SignUpRequest.class));
    }

    @Test
    @SneakyThrows
    public void loginShouldReturn() {
        // given
        LogInRequest request = LogInRequest.builder()
                .email("test@email.com")
                .password("test")
                .build();

        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder().token("string").build();

        // when
        when(authenticationService.login(request)).thenReturn(response);

        // then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(authenticationService).login(any(LogInRequest.class));
    }

    @Test
    @SneakyThrows
    public void loginShouldThrowWhenEmailOrPasswordInvalid() {
        // given
        LogInRequest request = LogInRequest.builder()
                .email("test@email.com")
                .password("test")
                .build();

        // when
        when(authenticationService.login(request)).thenThrow(new InvalidCredentialsException("Wrong email"));

        // then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Wrong email")));

        verify(authenticationService).login(any(LogInRequest.class));
    }

}