package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record PostResponse(Integer id, Integer userId, String content, String image_url, Date created_at, Date updated_at) {

}