package com.example.labmanagement.repository;

import com.example.labmanagement.dox.Lab;
import com.example.labmanagement.dox.LabReservation;
import com.example.labmanagement.dto.LabSlotDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
class LabReservationRepositoryTest {
    @Autowired
    private LabReservationRepository labReservationRepository;
    @Test
    void testReserve() {
        LabReservation labReservation = LabReservation.builder()
                .courseId("1320627309026050048")
                .labId("1")
                .build();
    }
    @Test
    void testExists() {
        String labId = "1";
        int week = 1;
        int dayOfWeek = 2;
        int section = 3;
        boolean exists = labReservationRepository.existsByLabIdAndWeekAndDayOfWeekAndSection(labId, week, dayOfWeek, section);
        System.out.println(exists);
    }
    @Test
    void testFindByTeacherId() {
        String teacherId = "1319917133948092416";
        List<LabReservation> labReservations = labReservationRepository.findByTeacherId(teacherId);
        for (LabReservation labReservation : labReservations) {
            System.out.println(labReservation);
        }
    }
    @Test
    void testDeleteById() {
        String reservationId = "1321372213954523136";
        labReservationRepository.deleteById(reservationId);
    }
    @Test
    void testFindFreeSlotsByWeekAndDayOfWeek() {
        List<LabSlotDTO> reservations = labReservationRepository.findFreeSlotsByWeekAndDayOfWeek(1, 2);
        for (LabSlotDTO labReservation : reservations) {
            System.out.println(labReservation);
        }
    }
    @Test
    void testFindFreeSlotsByWeekDayAndSection() {
        List<LabSlotDTO> reservations = labReservationRepository.findFreeSlotsByWeekDayAndSection(1,2,1);
        for (LabSlotDTO labReservation : reservations) {
            System.out.println(labReservation);
        }
    }
}