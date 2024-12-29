package com.example.labmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LabReservationRequest {
    private String courseId;
    private String courseName;
    private Byte week;
    private Byte dayOfWeek;
    private Byte section;
    private String labId;
}
