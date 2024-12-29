package com.example.labmanagement.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @CreatedBy
    private String id;
    private String name;
    private short quantity;
    private short lesson;
    private String classInfo;
    private String major;
    private String teacherId;
}
