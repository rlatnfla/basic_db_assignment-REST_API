package com.example.basic_db_rest_api.api.controller;

import com.example.basic_db_rest_api.api.service.StudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentsController {

    private final StudentsService studentsService;

    @GetMapping("/students/degree")
    public ResponseEntity<String> getDegreeByName(@RequestParam String name) {

        String result = studentsService.getDegreeByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/students/email")
    public ResponseEntity<String> getEmailByName(@RequestParam String name) {

        String result = studentsService.getEmailByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/students/stat")
    public ResponseEntity<String> getStudentCountByDegree(@RequestParam String degree) {

        String result = studentsService.countByDegree(degree);
        return ResponseEntity.ok(result);
    }
}
