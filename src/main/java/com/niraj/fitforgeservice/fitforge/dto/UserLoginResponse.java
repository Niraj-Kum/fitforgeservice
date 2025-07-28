package com.niraj.fitforgeservice.fitforge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    private String token;
    private UserProfileResponse user;
}
