package com.jamesmatherly.sample.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

@Service
@Log
public class UserService {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.cognito.poolId}")
    private String poolId;

    @Bean
    CognitoIdentityProviderClient cognitoClient() {
        return CognitoIdentityProviderClient.builder()
            .region(Region.of(awsRegion))
            .build();
    }

    public String getUser(String username) {
        try {
            AdminGetUserRequest userRequest = AdminGetUserRequest.builder()
                    .username(username)
                    .userPoolId(poolId)
                    .build();

            AdminGetUserResponse response = cognitoClient().adminGetUser(userRequest);
            System.out.println("User status " + response.userStatusAsString());

        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return "";
    }

}
