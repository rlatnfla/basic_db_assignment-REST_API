package com.example.basic_db_rest_api.api.service;

import com.example.basic_db_rest_api.api.dao.StudentsDataAccessor;
import com.example.basic_db_rest_api.init.model.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsService {

    private final StudentsDataAccessor studentsDataAccessor;

    private static final String NO_SUCH_STUDENT_MESSAGE = "No such student";

    public String getDegreeByName(String name) {

        List<Student> students = studentsDataAccessor.findAllByName(name);

        if (students.isEmpty()) {
            return NO_SUCH_STUDENT_MESSAGE;
        } else if (students.size() > 1) {
            return "There are multiple students with the same name. Please provide an email address instead";
        } else {
            Student student = students.get(0);
            return student.getName() + " : " + student.getDegree();
        }
    }

    public String getEmailByName(String name) {

        List<Student> students = studentsDataAccessor.findAllByName(name);

        if (students.isEmpty()) {
            return NO_SUCH_STUDENT_MESSAGE;
        } else if (students.size() > 1) {
            return "There are multiple students with the same name. Please contact the administrator by phone.";
        } else {
            Student student = students.get(0);
            return student.getName() + " : " + student.getEmail();
        }
    }

    public String countByDegree(String degree) {
        Long count = studentsDataAccessor.countAllByDegree(degree);

        return degree + " : " + count;
    }

    public String putStudents(String name, String email, int graduation) {
        Long count = studentsDataAccessor.countAllByNameAndEmail(name, email);

        if (count > 0) {
            return "Already registered";
        } else {
            studentsDataAccessor.insertStudent(name, email, graduation);
            return "Registration successful";
        }
    }
}
