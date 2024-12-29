package com.example.labmanagement.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabReservation {
    @Id
    @CreatedBy
    private String id;
    private String courseName;
    private String courseId;
    private String labId;
    private String labName;
    private Byte week;
    private Byte dayOfWeek;
    private Byte section;
    private String teacherName;
    private String teacherId;
    @ReadOnlyProperty
    private LocalDateTime reservationTime;
}
