package com.example.labmanagement.controller;

import com.example.labmanagement.dox.Announcement;
import com.example.labmanagement.dox.Course;
import com.example.labmanagement.dox.Lab;
import com.example.labmanagement.dox.LabReservation;
import com.example.labmanagement.dto.LabReservationRangeRequest;
import com.example.labmanagement.dto.LabReservationRequest;
import com.example.labmanagement.service.LabReservationService;
import com.example.labmanagement.service.TeacherService;
import com.example.labmanagement.service.UserService;
import com.example.labmanagement.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TeacherService teacherService;
    private final LabReservationService labReservationService;
    @Operation(summary = "更改用户密码哈哈")
    @PatchMapping("password/{password}")
    public ResultVO patchPassword(@RequestAttribute("uid") String uid,@PathVariable String password) {
        try {
            userService.updateUserPassword(uid, password);
            return ResultVO.success("更改密码成功！！！");
        } catch (Exception e) {
            return ResultVO.error(400, "更改密码失败:" + e.getMessage());
        }
    }
    @Operation(summary = "上传头像")
    @PostMapping("avatar")
    public ResultVO uploadAvatar(@RequestAttribute("uid") String uid, @RequestBody Map<String, String> request) {
        String base64Avatar = request.get("avatar");
        if (base64Avatar == null || base64Avatar.isEmpty()) {
            return ResultVO.error(400, "头像不能为空");
        }
        try {
            userService.updateUserAvatar(uid, base64Avatar);
            return ResultVO.success("头像上传成功！！！");
        } catch (Exception e) {
            return ResultVO.error(400, "头像上传失败:" + e.getMessage());
        }
    }
    @Operation(summary = "获取base64的字符串")
    @GetMapping("avatar")
    public ResultVO getAvatar(@RequestAttribute("uid") String uid) {
        try {
            String base64Avatar = userService.getUserAvatar(uid);
            return ResultVO.success(base64Avatar);
        } catch (Exception e) {
            return ResultVO.error(400,"获取头像失败："+e.getMessage());
        }
    }
    @Operation(summary = "获取全部课程")
    @GetMapping("courses")
    public ResultVO getCourses(@RequestAttribute("uid")String uid) {
        try {
            List<Course> courses = teacherService.getAllCourses(uid);
            for (Course course : courses) {
                System.out.println(course);
            }
            return ResultVO.success(courses);
        } catch (Exception e) {
            return ResultVO.error(400,"获取课程失败："+e.getMessage());
        }
    }
    @Operation(summary = "添加课程并返回全新的老师的所有课程")
    @PostMapping("addCourse")
    public ResultVO addCourse(@RequestBody Course course,@RequestAttribute("uid") String uid) {
        try {
            return teacherService.addCourse(course,uid);
        } catch (Exception e) {
            return ResultVO.error(400, "添加课程失败:" + e.getMessage());
        }
    }
    @Operation(summary = "获取全部实验室")
    @GetMapping("labs")
    public ResultVO getLabs() {
        List<Lab> labs = userService.listLabs();
        return ResultVO.success(labs);
    }
    @Operation(summary = "获取全部公告")
    @GetMapping("announcements")
    public ResultVO getAllAnnouncements() {
        List<Announcement> announcements = userService.getAllAnnouncements();
        return ResultVO.success(announcements);
    }
    @Operation(summary = "根据实验室状态获取全部实验室")
    @GetMapping("stateLabs/{state}")
    public ResultVO getStateLabs(@PathVariable short state) {
        List<Lab> labs = userService.listLabsByState(state);
        return ResultVO.success(labs);
    }
    @Operation(summary = "获取全部空闲实验室")
    @GetMapping("available-labs")
    public ResultVO getAvailableLabs() {
        List<Lab> labs = labReservationService.getAvailableLabs();
        for (Lab lab : labs) {
            System.out.println(lab);
        }
        return ResultVO.success(labs);
    }
    @Operation(summary = "预约实验室并返回全新的老师的全部预约记录")
    @PostMapping("reserve")
    public ResultVO createReservation(@RequestBody LabReservationRequest request, @RequestAttribute("uid") String uid,@RequestAttribute("name") String name) {
        return labReservationService.createReservation(request, uid, name);
    }
    @Operation(summary = "一键预约实验室并返回老师的全部预约记录")
    @PostMapping("reserve-range")
    public ResultVO createReservationForRange(@RequestBody LabReservationRangeRequest request, @RequestAttribute("uid") String uid, @RequestAttribute("name") String name) {
        return labReservationService.createReservationForRange(request, uid, name);
    }
    @Operation(summary = "临时预约实验室并返回老师的全部预约记录")
    @PostMapping("reserveTemporary")
    public ResultVO createTemporaryReservation(@RequestBody LabReservationRequest request, @RequestAttribute("uid") String uid,@RequestAttribute("name") String name) {
        return labReservationService.createTemporaryReservation(request, uid, name);
    }
    @Operation(summary = "根据实验室id获取全部预约记录")
    @GetMapping("lab/{labId}/reservations")
    public ResultVO getReservations(@PathVariable String labId) {
        List<LabReservation> reservations = labReservationService.getLabReservations(labId);
        return ResultVO.success(reservations);
    }
    @Operation(summary = "获取老师的全部预约记录")
    @GetMapping("teacher/reservations")
    public ResultVO getAllReservations(@RequestAttribute("uid") String uid) {
        List<LabReservation> reservations = teacherService.getAllLabReservations(uid);
        return ResultVO.success(reservations);
    }
    @Operation(summary = "根据课程id获取全部预约记录")
    @GetMapping("teacher/reservations/{courseId}")
    public ResultVO getReservationByCourseId(@PathVariable String courseId) {
        List<LabReservation> reservations = teacherService.getAllLabReservationsByCourseId(courseId);
        return ResultVO.success(reservations);
    }
    @Operation(summary = "根据预约id取消预约并返回老师的全部预约记录")
    @DeleteMapping("reservation/{reservationId}")
    public ResultVO deleteReservation(@RequestAttribute("uid") String uid,@PathVariable String reservationId) {
        return teacherService.deleteLabReservation(reservationId, uid);
    }
    @Operation(summary = "批量取消预约并返回老师的全部预约记录")
    @DeleteMapping("reservations")
    public ResultVO cancelReservations(@RequestBody List<String> reservationIds,@RequestAttribute("uid") String uid) {
        return teacherService.cancelReservations(reservationIds,uid);
    }
    @Operation(summary = "根据week和dayOfWeek查询空闲教室")
    @GetMapping("free-slots/{week}/{dayOfWeek}")
    public ResultVO getFreeSlotsByWeekAndSection(@PathVariable int week, @PathVariable int dayOfWeek) {
        return ResultVO.success(teacherService.getFreeSlotsByWeekAndDayOfWeek(week,dayOfWeek));
    }
    @Operation(summary = "根据week和dayOfWeek和section查询空闲教室")
    @GetMapping("free-slots/{week}/{dayOfWeek}/{section}")
    public ResultVO getFreeSlotsByWeekDayAndSection(@PathVariable int week, @PathVariable int dayOfWeek, @PathVariable int section) {
        return ResultVO.success(teacherService.getFreeSlotsByWeekDayAndSection(week,dayOfWeek,section));
    }
}
