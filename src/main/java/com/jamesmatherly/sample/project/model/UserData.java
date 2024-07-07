package com.jamesmatherly.sample.project.model;

import java.time.Instant;

import lombok.Data;

@Data
public class UserData {
    private String username;
    private String userStatus;
    private Instant userCreateDate;
    private Instant userLastModifiedDate;
}
