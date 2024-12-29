package com.example.labmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabReservationRangeRequest {
    private String courseId;
    private String courseName;
    private Byte startWeek;
    private Byte endWeek;
    private Byte dayOfWeek;
    private Byte section;
    private String labId;
}
