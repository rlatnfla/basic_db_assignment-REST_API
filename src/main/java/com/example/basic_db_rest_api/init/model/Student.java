package com.example.basic_db_rest_api.init.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Student {

    private final String degree;
    private final String name;
    private final String email;
    private final int year;
}
