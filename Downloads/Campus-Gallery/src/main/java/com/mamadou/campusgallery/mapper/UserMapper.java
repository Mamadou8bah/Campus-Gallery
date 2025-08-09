package com.mamadou.campusgallery.mapper;

import com.mamadou.campusgallery.dto.RegisterResponse;
import com.mamadou.campusgallery.model.User;

public class UserMapper {

    public static RegisterResponse userToRegisterResponse(User user) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setFullName(user.getFullName());
        registerResponse.setEmail(user.getEmail());
        registerResponse.setImageUrl(user.getProfileUrl());
        return registerResponse;
    }
}
