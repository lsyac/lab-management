package com.example.labmanagement.repository;

import com.example.labmanagement.dox.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;
    @Test
    void testFindByUserId() {
        String u = "1319917133948092416";
        List<Course> courses = courseRepository.findByTeacherId(u);
        for (Course course : courses) {
            System.out.println(course);
        }
    }
    @Test
    void testAddCourse() {
        Course course = Course.builder()
                .name("计网")
                .quantity((short) 60)
                .teacherId("1319917134866644992")
                .lesson((short) 48)
                .classInfo("1")
                .major("软件工程")
                .build();
        courseRepository.save(course);
    }
    @Test
    void testFindAll() {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        for (Course course : courses) {
            System.out.println(course);
        }
    }

}