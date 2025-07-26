package com.niraj.fitforgeservice.fitforge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FitForgeResponse<T> {
    private T response;
    private Integer id;

    public FitForgeResponse(Integer id, T response) {
        this.id = id;
        this.response = response;
    }

    public FitForgeResponse(T response) {
        this.response = response;
    }
}
