package com.example.basic_db_rest_api.init.data;

import com.example.basic_db_rest_api.init.model.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentDataInitializer {

    private final JdbcTemplate jdbcTemplate;

    public void initializeDatabase(List<Student> students) {
        dropAndCreateTable();
        insertStudentData(students);
    }

    private void dropAndCreateTable() {
        try {
            jdbcTemplate.execute("DROP TABLE IF EXISTS students CASCADE");

            jdbcTemplate.execute("DROP SEQUENCE IF EXISTS students_sid_seq");

            jdbcTemplate.execute("CREATE SEQUENCE students_sid_seq START WITH 1 INCREMENT BY 1");

            String createTableSQL = """
                CREATE TABLE students (
                    sid INTEGER PRIMARY KEY,
                    name VARCHAR(100),
                    email VARCHAR(100),
                    degree VARCHAR(100),
                    graduation INTEGER
                )
                """;
            jdbcTemplate.execute(createTableSQL);

            jdbcTemplate.execute(
                "ALTER TABLE students ALTER COLUMN sid SET DEFAULT nextval('students_sid_seq')");

            log.info("students 테이블 생성 완료");
        } catch (DataAccessException e) {
            log.error("테이블 생성 중 SQL 오류 발생: {}", e.getMessage());
            throw e;
        }
    }

    private void insertStudentData(List<Student> students) {
        String insertQuery = "INSERT INTO students (degree, name, email, graduation) VALUES (?, ?, ?, ?)";

        int batchSize = students.size();

        try {

            jdbcTemplate.batchUpdate(
                insertQuery,
                students,
                batchSize,
                (ps, student) -> {
                    ps.setString(1, student.getDegree());
                    ps.setString(2, student.getName());
                    ps.setString(3, student.getEmail());
                    ps.setInt(4, student.getYear());
                }
            );

            log.info("총 {} 명의 학생 데이터 삽입 완료", batchSize);

        } catch (DataAccessException e) {
            log.error("학생 데이터 삽입 중 오류 발생: {}", e.getMessage());
            throw e;
        }
    }
}
