package com.example.basic_db_rest_api.init.parser;

import com.example.basic_db_rest_api.init.model.Student;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WebStudentParser implements StudentParser {

    private static final String WEB_URL = "https://apl.hongik.ac.kr/lecture/dbms ";

    @Override
    public List<Student> parseAndExtractData() {

        List<Student> students = new ArrayList<>();

        try {

            Document doc = Jsoup.connect(WEB_URL)
                .timeout(5000)
                .get();

            Elements h2Tags = doc.select("h2");

            for (Element h2 : h2Tags) {
                String degree = h2.text();

                Element studentList = h2.nextElementSibling();

                if (studentList == null || !studentList.tagName().equalsIgnoreCase("ul")) {
                    continue;
                }

                Elements studentItems = studentList.select("li");

                for (Element item : studentItems) {
                    String studentLine = item.text();

                    String[] parts = studentLine.split(",\\s*", 3);

                    if (parts.length != 3) {
                        log.warn("학생 데이터 형식 불일치 (학위: {}, 데이터: {})", degree, studentLine);
                        continue;
                    }


                    String name = parts[0].trim();
                    String email = parts[1].trim();
                    String yearText = parts[2].trim();

                    int year = Integer.parseInt(yearText);


                    students.add(new Student(degree, name, email, year));
                }
            }


        } catch (IOException e) {
            log.error("웹 페이지 연결 중 오류 발생: {}", e.getMessage());
        } catch (Exception e) {
            log.error("html 파일 파싱중 오류 발생: {}", e.getMessage());
        }

        return students;
    }
}
