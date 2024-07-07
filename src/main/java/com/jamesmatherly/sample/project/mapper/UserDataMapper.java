package com.jamesmatherly.sample.project.mapper;

import com.jamesmatherly.sample.project.model.UserData;

import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;

public interface UserDataMapper {
    
    UserData cognitoUsertoUserDate(AdminGetUserResponse data);
}
