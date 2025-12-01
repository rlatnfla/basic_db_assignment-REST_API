package com.example.basic_db_rest_api.init.parser;

import com.example.basic_db_rest_api.init.model.Student;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class FileStudentParser implements StudentParser {

    private static final String FILE_PATH = "static/hongik_dbms_table.html";
    private File getHtmlFile() throws IOException {

        ClassPathResource resource = new ClassPathResource(FILE_PATH);
        return resource.getFile();
    }

    public List<Student> parseAndExtractData() {

        List<Student> students = new ArrayList<>();
        try {

            File input = getHtmlFile();

            Document doc = Jsoup.parse(input, "UTF-8");

            Elements h2Tags = doc.select("h2");

            for (Element h2 : h2Tags) {
                String degree = h2.text();

                Element studentTable = h2.nextElementSibling();

                if (studentTable == null || !studentTable.tagName().equalsIgnoreCase("table")) {
                    continue;
                }

                Elements studentRows = studentTable.select("tr:gt(0)");

                for (Element row : studentRows) {
                    Elements cells = row.select("td");

                    String name = cells.get(0).text();
                    String email = cells.get(1).text();
                    String yearText = cells.get(2).text();
                    int year = Integer.parseInt(yearText);

                    students.add(new Student(degree, name, email, year));
                }
            }

        } catch (Exception e) {
            log.error("html 파일 파싱중 오류 발생: {}", e.getMessage());
        }
        return students;
    }
}
