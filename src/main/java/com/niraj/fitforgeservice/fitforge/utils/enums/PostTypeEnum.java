package com.niraj.fitforgeservice.fitforge.utils.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum PostTypeEnum {
    WORKOUT(1, "health"),
    CAREER(2, "career");

    @Getter
    private final Integer key;
    @Getter
    private final String value;

    private static final Map<Integer, PostTypeEnum> BY_KEY = new HashMap<>();
    private static final Map<String, PostTypeEnum> BY_VALUE = new HashMap<>();

    private PostTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    static {
        for (PostTypeEnum post: values()) {
            BY_KEY.put(post.key, post);
            BY_VALUE.put(post.value, post);
        }
    }
}
