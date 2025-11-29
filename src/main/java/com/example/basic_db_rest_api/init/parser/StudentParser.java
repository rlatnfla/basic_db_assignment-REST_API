package com.example.basic_db_rest_api.init.parser;

import com.example.basic_db_rest_api.init.model.Student;
import java.util.List;

public interface StudentParser {

    List<Student> parseAndExtractData();

}
