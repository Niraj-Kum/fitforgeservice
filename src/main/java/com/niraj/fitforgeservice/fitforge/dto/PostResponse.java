package com.niraj.fitforgeservice.fitforge.dto;

import java.util.Date;

public record PostResponse(Integer id, UserLiteResponse user, String content, String imageUrl, Integer postType, Integer likes, Integer comments,
                           Date createdAt) {
}