package com.example.basic_db_rest_api.api.dao;

import com.example.basic_db_rest_api.init.model.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudentsDataAccessor {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> {

        return new Student(
            rs.getString("degree"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getInt("graduation")
        );
    };

    public List<Student> findAllByName(String name) {

        String query = """
            SELECT s.degree, s.name, s.email, s.graduation
            FROM students s
            WHERE s.name = ?         
            """;

        return jdbcTemplate.query(query, studentRowMapper, name);
    }

    public Long countAllByDegree(String degree) {

        String query = """
            SELECT count(*)
            FROM students s
            WHERE s.degree = ?
            """;

        return jdbcTemplate.queryForObject(query, Long.class, degree);
    }


}
