package com.example.labmanagement.controller;

import com.example.labmanagement.dox.Announcement;
import com.example.labmanagement.dox.Lab;
import com.example.labmanagement.dto.UserDTO;
import com.example.labmanagement.service.AdminService;
import com.example.labmanagement.service.UserService;
import com.example.labmanagement.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;
    @Operation(summary = "添加用户并返回全部用户")
    @PostMapping("users")
    public ResultVO postUser(@RequestBody UserDTO userDTO) {
        return adminService.addUser(userDTO);
    }
    @Operation(summary = "删除用户并返回全部用户")
    @DeleteMapping("users/{id}")
    public ResultVO deleteUser(@PathVariable String id) {
        return adminService.deleteUser(id);
    }
    @Operation(summary = "重置用户密码")
    @PutMapping("users/{account}/password")
    public ResultVO putPassword(@PathVariable String account) {
        userService.updateUserPassword(account);
        return ResultVO.success("重置用户密码成功！！！");
    }
    @Operation(summary = "返回全部用户")
    @GetMapping("getUsers")
    public ResultVO getUsers() {
        return ResultVO.success(adminService.listUsers());
    }
    @Operation(summary = "添加实验室并返回全部实验室")
    @PostMapping("labs")
    public ResultVO postLab(@RequestBody Lab lab) {
        return adminService.addLab(lab);
    }
    @Operation(summary = "删除实验室并返回全部实验室")
    @DeleteMapping("labs/{labId}")
    public ResultVO deleteLab(@PathVariable String labId) {
        return adminService.removeLab(labId);
    }
    @Operation(summary = "更新实验室状态并返回实验室信息")
    @PutMapping("labs/state/{labId}")
    public ResultVO updateLabState(@PathVariable String labId,@RequestParam short state) {
        return adminService.updateLabState(labId,state);
    }
    @Operation(summary = "更新实验室描述并返回实验室信息")
    @PutMapping("labs/description/{labId}")
    public ResultVO updateLabDescription(@PathVariable String labId,@RequestParam String description) {
        return adminService.updateLabDescription(labId,description);
    }
    @Operation(summary = "更新实验室管理员并返回实验室信息")
    @PutMapping("labs/manage/{labId}")
    public ResultVO updateLabManage(@PathVariable String labId,@RequestParam String manage) {
        return adminService.updateLabManage(labId,manage);
    }
    @Operation(summary = "添加公告并返回全部公告")
    @PostMapping("announcements")
    public ResultVO postAnnouncement(@RequestBody Announcement announcement) {
        return adminService.addAnnouncement(announcement);
    }
    @Operation(summary = "返回全部公告")
    @GetMapping("announcements")
    public ResultVO getAllAnnouncements() {
        List<Announcement> announcements = userService.getAllAnnouncements();
        return ResultVO.success(announcements);
    }
    @Operation(summary = "删除公告并返回全部公告")
    @DeleteMapping("announcements/{id}")
    public ResultVO deleteAnnouncement(@PathVariable String id) {
        return adminService.deleteAnnouncement(id);
    }

}
