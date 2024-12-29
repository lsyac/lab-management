package com.example.labmanagement.repository;

import com.example.labmanagement.dox.LabReservation;
import com.example.labmanagement.dto.LabSlotDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabReservationRepository extends CrudRepository<LabReservation, String> {
    List<LabReservation> findByLabId(String labId);
    boolean existsByLabIdAndWeekAndDayOfWeekAndSection(String labId,int week,int dayOfWeek,int section);
    List<LabReservation> findByTeacherId(String teacherId);
    List<LabReservation> findByCourseId(String courseId);
    int countByCourseId(String courseId);
    void deleteById(String id);
    @Query("""
        WITH AllSlots AS (
            SELECT
                l.id AS lab_id,
                l.name AS lab_name,
                w.week,
                d.day_of_week,
                s.section
            FROM
                lab l,
                (SELECT DISTINCT week FROM lab_reservation) w,
                (SELECT DISTINCT day_of_week FROM lab_reservation) d,
                (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
        )
        SELECT
            a.lab_id,
            a.lab_name,
            a.week,
            a.day_of_week,
            a.section
        FROM
            AllSlots a
        LEFT JOIN lab_reservation lr
            ON a.lab_id = lr.lab_id
            AND a.week = lr.week
            AND a.day_of_week = lr.day_of_week
            AND a.section = lr.section
        WHERE
            lr.id IS NULL
            AND a.week = :week
            AND a.day_of_week = :dayOfWeek
        ORDER BY
            a.lab_id,
            a.section;
    """)
    List<LabSlotDTO> findFreeSlotsByWeekAndDayOfWeek(int week, int dayOfWeek);
    @Query("""
        WITH AllSlots AS (
            SELECT
                l.id AS lab_id,
                l.name AS lab_name,
                w.week,
                d.day_of_week,
                s.section
            FROM
                lab l,
                (SELECT DISTINCT week FROM lab_reservation) w,
                (SELECT DISTINCT day_of_week FROM lab_reservation) d,
                (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
        )
        SELECT
            a.lab_id,
            a.lab_name,
            a.week,
            a.day_of_week,
            a.section
        FROM
            AllSlots a
        LEFT JOIN lab_reservation lr
            ON a.lab_id = lr.lab_id
            AND a.week = lr.week
            AND a.day_of_week = lr.day_of_week
            AND a.section = lr.section
        WHERE
            lr.id IS NULL
            AND a.week = :week
            AND a.day_of_week = :dayOfWeek
            AND a.section = :section
        ORDER BY
            a.lab_id,
            a.section;
    """)
    List<LabSlotDTO> findFreeSlotsByWeekDayAndSection(int week, int dayOfWeek, int section);
}