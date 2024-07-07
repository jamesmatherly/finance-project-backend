package com.jamesmatherly.sample.project.mapper;

import com.jamesmatherly.sample.project.model.UserData;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;

@Component
public class UserDataMapperImpl implements UserDataMapper {

    @Override
    public UserData cognitoUsertoUserDate(AdminGetUserResponse data) {
        if ( data == null ) {
            return null;
        }

        UserData userData = new UserData();
        userData.setUserCreateDate(data.userCreateDate());
        userData.setUserLastModifiedDate(data.userLastModifiedDate());
        userData.setUserStatus(data.userStatusAsString());
        userData.setUsername(data.username());

        return userData;
    }
}
