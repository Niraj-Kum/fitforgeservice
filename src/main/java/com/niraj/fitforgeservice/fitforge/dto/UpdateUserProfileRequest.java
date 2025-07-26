package com.niraj.fitforgeservice.fitforge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest {
    private String name;
    private Integer age;
    private Double weight;
    private Double height;
    private String photoURL;
}
