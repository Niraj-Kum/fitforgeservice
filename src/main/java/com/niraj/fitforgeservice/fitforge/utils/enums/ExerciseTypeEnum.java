package com.niraj.fitforgeservice.fitforge.utils.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ExerciseTypeEnum {
    STRENGTH(1, "Strength"),
    CARDIO(2, "Cardio"),
    ABS(3, "Abs"),
    NECK(4, "Neck"),
    CALISTHENICS(5, "Calisthenics"),
    FUNCTIONALCORE(6, "Functional Core");

    @Getter
    private final Integer key;
    @Getter
    private final String value;

    public static final Map<Integer, ExerciseTypeEnum> BY_KEY = new HashMap<>();
    public static final Map<String, ExerciseTypeEnum> BY_VALUE = new HashMap<>();

    private ExerciseTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    static {
        for (ExerciseTypeEnum post: values()) {
            BY_KEY.put(post.key, post);
            BY_VALUE.put(post.value, post);
        }
    }
}
