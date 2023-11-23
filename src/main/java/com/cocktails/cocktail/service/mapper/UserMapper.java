package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

}
