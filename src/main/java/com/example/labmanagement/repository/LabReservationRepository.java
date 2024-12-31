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
                l.quantity AS quantity,
                l.description AS description,
                w.week,
                d.day_of_week,
                s.section
            FROM
                lab l,
                (SELECT 1 AS week UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5\s
                 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10
                 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15
                 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20
                 UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL SELECT 25
                 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 29 UNION ALL SELECT 30
                 UNION ALL SELECT 31 UNION ALL SELECT 32) w,
                (SELECT 1 AS day_of_week UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7) d,
                (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
    )
        SELECT
            a.lab_id,
            a.lab_name,
            a.week,
            a.day_of_week,
            a.section,
            a.quantity,
            a.description
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
                l.quantity AS quantity,
                l.description AS description,
                w.week,
                d.day_of_week,
                s.section
            FROM
                lab l,
                (SELECT 1 AS week UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5\s
                 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10
                 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15
                 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20
                 UNION ALL SELECT 21 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL SELECT 25
                 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 29 UNION ALL SELECT 30
                 UNION ALL SELECT 31 UNION ALL SELECT 32) w,
                (SELECT 1 AS day_of_week UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7) d,
                (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
                )
        SELECT
            a.lab_id,
            a.lab_name,
            a.week,
            a.day_of_week,
            a.section,
            a.quantity,
            a.description
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