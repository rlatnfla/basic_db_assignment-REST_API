package com.example.basic_db_rest_api;

import com.example.basic_db_rest_api.init.InitializeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BasicDbRestApiApplication implements CommandLineRunner {

    private final InitializeManager initializeManager;

    public BasicDbRestApiApplication(InitializeManager initializeManager) {
        this.initializeManager = initializeManager;
    }
    public static void main(String[] args) {
        SpringApplication.run(BasicDbRestApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {

            initializeManager.init();

        } catch (Exception e) {

            log.error("애플리케이션 초기화 중 오류 발생: {}", e.getMessage());
        }
    }
}
