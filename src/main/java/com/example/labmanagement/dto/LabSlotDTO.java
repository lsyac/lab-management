package com.example.labmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LabSlotDTO {
    private String labId;
    private  String labName;
    private int week;
    private int dayOfWeek;
    private int section;
    private int quantity;
    private String description;
}
