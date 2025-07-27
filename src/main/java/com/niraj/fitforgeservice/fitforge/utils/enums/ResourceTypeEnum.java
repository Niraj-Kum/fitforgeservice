package com.niraj.fitforgeservice.fitforge.utils.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ResourceTypeEnum {
    PLATFORM(1, "platform"),
    BOOK(1, "book"),
    COURSE(1, "course"),
    DOC(1, "doc");

    @Getter
    private final Integer key;
    @Getter
    private final String value;

    public static final Map<Integer, ResourceTypeEnum> BY_KEY = new HashMap<>();
    public static final Map<String, ResourceTypeEnum> BY_VALUE = new HashMap<>();

    private ResourceTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    static {
        for (ResourceTypeEnum post: values()) {
            BY_KEY.put(post.key, post);
            BY_VALUE.put(post.value, post);
        }
    }
}
