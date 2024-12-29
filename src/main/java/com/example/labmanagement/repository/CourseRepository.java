package com.example.labmanagement.repository;

import com.example.labmanagement.dox.Course;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {
    @Query("""
        select * from course c 
        where c.teacher_id=:userId
             """)
    List<Course> findByTeacherId(String userId);
}
