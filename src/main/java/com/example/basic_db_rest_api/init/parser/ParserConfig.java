package com.example.basic_db_rest_api.init.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfig {

    @Bean
    StudentParser studentParser() {
        return new FileStudentParser();
    }
}
