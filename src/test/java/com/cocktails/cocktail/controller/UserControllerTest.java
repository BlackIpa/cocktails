package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.JwtResponse;
import com.cocktails.cocktail.dto.SignInRequest;
import com.cocktails.cocktail.dto.SignOutRequest;
import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.dto.UserUpdateRequest;
import com.cocktails.cocktail.exception.DuplicateException;
import com.cocktails.cocktail.exception.InvalidCredentialsException;
import com.cocktails.cocktail.handler.GlobalExceptionHandler;
import com.cocktails.cocktail.service.AuthService;
import com.cocktails.cocktail.service.RefreshTokenService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Mock
    private RefreshTokenService refreshTokenService;

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
        mockMvc.perform(post("/user/register")
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
        mockMvc.perform(post("/user/register")
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
        mockMvc.perform(post("/user/login")
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
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Wrong email")));
        verify(authService).authenticateUser(any(SignInRequest.class));
    }

    @Test
    @SneakyThrows
    public void logoutShouldReturn() {
        // given
        val token = "token";
        val request = SignOutRequest.builder()
                .refreshToken(token)
                .build();

        // then
        mockMvc.perform(post("/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        verify(refreshTokenService).deleteRefreshToken(token);
    }

    @Test
    @SneakyThrows
    public void logoutShouldThrowWhenWhenInvalidToken() {
        // given
        val token = "token";
        val request = SignOutRequest.builder()
                .refreshToken(token)
                .build();

        // when
        doThrow(new IllegalArgumentException("Invalid token"))
                .when(refreshTokenService).deleteRefreshToken(token);

        // then
        mockMvc.perform(post("/user/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(content().string(containsString("Invalid token")));
        verify(refreshTokenService).deleteRefreshToken(token);
    }

    @Test
    @SneakyThrows
    public void getUserDetailsShouldReturn() {
        // given
        val email = "test@email.com";
        val principal = mock(Principal.class);
        val userResponse = UserResponse.builder().email(email).build();

        // when
        when(principal.getName()).thenReturn(email);
        when(userService.getUserDetails(email)).thenReturn(userResponse);

        // then
        mockMvc.perform(get("/user/details")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
        verify(userService).getUserDetails(email);
    }

    @Test
    @SneakyThrows
    public void getUserDetailsShouldThrowIfUserNotFound() {
        // given
        val email = "test@email.com";
        val principal = mock(Principal.class);

        // when
        when(principal.getName()).thenReturn(email);
        when(userService.getUserDetails(email)).thenThrow(new UsernameNotFoundException("User not found"));

        // then
        mockMvc.perform(get("/user/details")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
        verify(userService).getUserDetails(email);
    }

    @Test
    @SneakyThrows
    public void updateUserShouldReturn() {
        // given
        val email = "test@email.com";
        val principal = mock(Principal.class);
        val updateRequest = UserUpdateRequest.builder()
                .firstName("first")
                .lastName("last")
                .build();
        val updatedUserResponse = UserResponse.builder()
                .email(email)
                .firstName(updateRequest.getFirstName())
                .lastName(updateRequest.getLastName())
                .build();

        // when
        when(principal.getName()).thenReturn(email);
        when(userService.updateUser(email, updateRequest)).thenReturn(updatedUserResponse);

        // then
        mockMvc.perform(put("/user/update")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("first"))
                .andExpect(jsonPath("$.lastName").value("last"))
                .andExpect(jsonPath("$.email").value(email));
        verify(userService).updateUser(eq(email), any(UserUpdateRequest.class));
    }

    @Test
    @SneakyThrows
    public void updateUserShouldThrowIfUserNotFound() {
        // given
        val email = "test@email.com";
        val principal = mock(Principal.class);
        val updateRequest = UserUpdateRequest.builder()
                .firstName("first")
                .lastName("last")
                .build();

        // when
        when(principal.getName()).thenReturn(email);
        when(userService.updateUser(email, updateRequest)).thenThrow(new UsernameNotFoundException("User not found"));

        // then
        mockMvc.perform(put("/user/update")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
        verify(userService).updateUser(eq(email), any(UserUpdateRequest.class));
    }

}