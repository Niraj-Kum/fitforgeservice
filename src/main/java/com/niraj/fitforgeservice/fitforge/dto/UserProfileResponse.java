package com.niraj.fitforgeservice.fitforge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private Integer uid;
    private String email;
    private String name;
    private Integer age;
    private Double weight;
    private Double height;
    private String photoURL;
}