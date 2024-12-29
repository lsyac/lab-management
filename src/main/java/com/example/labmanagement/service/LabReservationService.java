package com.example.labmanagement.service;

import com.example.labmanagement.dox.Course;
import com.example.labmanagement.dox.Lab;
import com.example.labmanagement.dox.LabReservation;
import com.example.labmanagement.dto.LabReservationRangeRequest;
import com.example.labmanagement.dto.LabReservationRequest;
import com.example.labmanagement.repository.CourseRepository;
import com.example.labmanagement.repository.LabRepository;
import com.example.labmanagement.repository.LabReservationRepository;
import com.example.labmanagement.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class LabReservationService {
    private final LabReservationRepository labReservationRepository;
    private final LabRepository labRepository;
    private final CourseRepository courseRepository;
    @Transactional
    public List<LabReservation> getLabReservations(String labId) {
        return labReservationRepository.findByLabId(labId);
    }
    @Transactional
    public List<Lab> getAvailableLabs() {
        return labRepository.findByState((byte) 1);
    }
    @Transactional
    public ResultVO createTemporaryReservation(LabReservationRequest request,String teacherId,String teacherName) {
        Lab lab = labRepository.findById(request.getLabId()).orElseThrow(() -> new RuntimeException("Lab not found"));
        boolean exists = labReservationRepository.existsByLabIdAndWeekAndDayOfWeekAndSection(request.getLabId(), request.getWeek(), request.getDayOfWeek(), request.getSection());
        if (exists) {
            return ResultVO.reservedFailed();
        }
        LabReservation reservation = LabReservation.builder()
                .courseId(request.getCourseId())
                .courseName(request.getCourseName())
                .labId(request.getLabId())
                .labName(lab.getName())
                .week(request.getWeek())
                .dayOfWeek(request.getDayOfWeek())
                .section(request.getSection())
                .teacherId(teacherId)
                .teacherName(teacherName)
                .build();
        labReservationRepository.save(reservation);
        List<LabReservation> allReservations = labReservationRepository.findByTeacherId(teacherId);
        return ResultVO.success(allReservations);
    }
    @Transactional
    public ResultVO createReservation(LabReservationRequest request, String teacherId, String teacherName) {
        Lab lab = labRepository.findById(request.getLabId()).orElseThrow(() -> new RuntimeException("Lab not found"));
        boolean exists = labReservationRepository.existsByLabIdAndWeekAndDayOfWeekAndSection(request.getLabId(), request.getWeek(), request.getDayOfWeek(), request.getSection());
        if (exists) {
            return ResultVO.reservedFailed();
        }
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        byte totalLessons = (byte) course.getLesson();
        byte reservedLessons = (byte) (labReservationRepository.countByCourseId(request.getCourseId())*2);
        if (reservedLessons + 2 > totalLessons) {
            return ResultVO.error(400,"课程学时已满，无法再预约");
        }
        LabReservation reservation = LabReservation.builder()
                .courseId(request.getCourseId())
                .courseName(request.getCourseName())
                .labId(request.getLabId())
                .labName(lab.getName())
                .week(request.getWeek())
                .dayOfWeek(request.getDayOfWeek())
                .section(request.getSection())
                .teacherId(teacherId)
                .teacherName(teacherName)
                .build();
        labReservationRepository.save(reservation);
        List<LabReservation> allReservations = labReservationRepository.findByTeacherId(teacherId);
        return ResultVO.success(allReservations);
    }
    @Transactional
    public ResultVO createReservationForRange(LabReservationRangeRequest request, String teacherId, String teacherName) {
        Lab lab = labRepository.findById(request.getLabId()).orElseThrow(() -> new RuntimeException("Lab not found"));
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found"));
        byte totalLessons = (byte) course.getLesson();
        byte reservedLessons = (byte) (labReservationRepository.countByCourseId(request.getCourseId())*2);
        byte selectedWeeks = (byte) (request.getEndWeek() - request.getStartWeek() + 1);
        byte selectedHours = (byte) (selectedWeeks * 2);
        if (reservedLessons + selectedHours > totalLessons) {
            return ResultVO.error(400, "所选区间学时超出课程总学时限制，无法预约");
        }
        for (Byte week = request.getStartWeek(); week <= request.getEndWeek(); week++) {
            boolean exists = labReservationRepository.existsByLabIdAndWeekAndDayOfWeekAndSection(
                    request.getLabId(), week, request.getDayOfWeek(), request.getSection());
            if (exists) {
                return ResultVO.reservedFailed();
            }
        }
        for (Byte week = request.getStartWeek(); week <= request.getEndWeek(); week++) {
            LabReservation reservation = LabReservation.builder()
                    .courseId(request.getCourseId())
                    .courseName(request.getCourseName())
                    .labId(request.getLabId())
                    .labName(lab.getName())
                    .week(week)
                    .dayOfWeek(request.getDayOfWeek())
                    .section(request.getSection())
                    .teacherId(teacherId)
                    .teacherName(teacherName)
                    .build();
            labReservationRepository.save(reservation);
        }
        List<LabReservation> allReservations = labReservationRepository.findByTeacherId(teacherId);
        return ResultVO.success(allReservations);
    }
}
