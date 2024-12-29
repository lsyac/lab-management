package com.example.labmanagement.repository;

import com.example.labmanagement.dox.Lab;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class LabRepositoryTest {
    @Autowired
    private LabRepository labRepository;
    @Test
    void findByLabId() {
        String labId = "1";
        Optional<Lab> lab = labRepository.findById(labId);
        System.out.println(lab);
    }
    @Test
    void testAddLab() {
        Lab lab = Lab.builder()
                .name("908")
                .manage("8")
                .quantity((short) 90)
                .state((short) 1)
                .build();
        labRepository.save(lab);
    }
    @Test
    void testFindByState() {
        short state = 1;
        List<Lab> labs = labRepository.findByState(state);
        for (Lab lab : labs) {
            System.out.println(lab);
        }
    }
    @Test
    void testDeleteLabByLabId() {
        String labId = "1321804826766782464";
        labRepository.deleteById(labId);
    }
}