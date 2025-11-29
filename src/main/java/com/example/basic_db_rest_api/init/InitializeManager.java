package com.example.basic_db_rest_api.init;

import com.example.basic_db_rest_api.init.data.StudentDataInitializer;
import com.example.basic_db_rest_api.init.model.Student;
import com.example.basic_db_rest_api.init.parser.StudentParser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitializeManager {

    private final StudentParser studentParser;
    private final StudentDataInitializer studentDataInitializer;

    public void init() {
        List<Student> students = studentParser.parseAndExtractData();
        studentDataInitializer.initializeDatabase(students);
    }
}
