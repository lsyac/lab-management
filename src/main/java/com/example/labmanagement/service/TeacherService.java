package com.example.labmanagement.service;

import com.example.labmanagement.dox.Course;
import com.example.labmanagement.dox.LabReservation;
import com.example.labmanagement.dto.LabSlotDTO;
import com.example.labmanagement.repository.AnnouncementRepository;
import com.example.labmanagement.repository.CourseRepository;
import com.example.labmanagement.repository.LabReservationRepository;
import com.example.labmanagement.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class TeacherService {
    private final CourseRepository courseRepository;
    private final LabReservationRepository labReservationRepository;
    @Transactional
    public List<Course> getAllCourses(String userId) {
        return courseRepository.findByTeacherId(userId);
    }
    @Transactional
    public ResultVO addCourse(Course course, String userId) {
        courseRepository.save(course);
        List<Course> courses = getAllCourses(userId);
        return ResultVO.success(courses);
    }
    @Transactional
    public List<LabReservation> getAllLabReservations(String teacherId) {
        return labReservationRepository.findByTeacherId(teacherId);
    }
    @Transactional
    public List<LabReservation> getAllLabReservationsByCourseId(String courseId) {
        return labReservationRepository.findByCourseId(courseId);
    }
    @Transactional
    public ResultVO deleteLabReservation(String id,String teacherId) {
        labReservationRepository.deleteById(id);
        List<LabReservation> labReservations = getAllLabReservations(teacherId);
        return ResultVO.success(labReservations);
    }
    @Transactional
    public ResultVO cancelReservations(List<String> reservationIds,String teacherId) {
        for (String reservationId : reservationIds) {
            labReservationRepository.deleteById(reservationId);
        }
        List<LabReservation> labReservations = getAllLabReservations(teacherId);
        return ResultVO.success(labReservations);
    }
    @Transactional
    public List<LabSlotDTO> getFreeSlotsByWeekAndDayOfWeek(int week, int dayOfWeek) {
        labReservationRepository.findFreeSlotsByWeekAndDayOfWeek(week, dayOfWeek);
        return labReservationRepository.findFreeSlotsByWeekAndDayOfWeek(week, dayOfWeek);
    }
    @Transactional
    public List<LabSlotDTO> getFreeSlotsByWeekDayAndSection(int week,int dayOfWeek, int  section) {
        return labReservationRepository.findFreeSlotsByWeekDayAndSection(week, dayOfWeek, section);
    }

}
