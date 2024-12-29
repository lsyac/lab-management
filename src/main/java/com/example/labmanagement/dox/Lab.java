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

public class Lab {
    @Id
    @CreatedBy
    private String id;
    private String name;
    private String manage;
    private Short quantity;
    private String description;
    private short state;
}
