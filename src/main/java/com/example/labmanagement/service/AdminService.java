package com.example.labmanagement.service;

import com.example.labmanagement.dox.Announcement;
import com.example.labmanagement.dox.Lab;
import com.example.labmanagement.dox.User;
import com.example.labmanagement.dto.UserDTO;
import com.example.labmanagement.repository.AnnouncementRepository;
import com.example.labmanagement.repository.LabRepository;
import com.example.labmanagement.repository.UserRepository;
import com.example.labmanagement.vo.ResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final LabRepository labRepository;
    private final AnnouncementRepository announcementRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Transactional
    public ResultVO addUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAccount(userDTO.getAccount());
        user.setRole(userDTO.getRole());
        user.setPassword(passwordEncoder.encode(user.getAccount()));
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        return ResultVO.success(users);
    }
    @Transactional
    public ResultVO deleteUser(String id) {
        userRepository.deleteById(id);
        List<User> users = userRepository.findAll();
        return ResultVO.success(users);
    }
    @Transactional
    public List<User> listUsers() {
        return userRepository.findAll();
    }
    @Transactional
    public ResultVO addLab(Lab lab) {
        Lab l = Lab.builder()
                .name(lab.getName())
                .quantity(lab.getQuantity())
                .state(lab.getState())
                .manage(lab.getManage())
                .description(lab.getDescription())
                .build();
        labRepository.save(l);
        List<Lab> labs = labRepository.findAll();
        return ResultVO.success(labs);
    }
    @Transactional
    public ResultVO removeLab(String labId) {
        labRepository.deleteById(labId);
        List<Lab> labs = labRepository.findAll();
        return ResultVO.success(labs);
    }
    @Transactional
    public ResultVO addAnnouncement(Announcement announcement) {
        Announcement a = Announcement.builder()
                .content(announcement.getContent())
                .title(announcement.getTitle())
                .build();
        announcementRepository.save(a);
        return ResultVO.success((List<Announcement>) announcementRepository.findAll());
    }

    @Transactional
    public ResultVO deleteAnnouncement(String id) {
        announcementRepository.deleteById(id);
        return ResultVO.success((List<Announcement>) announcementRepository.findAll());
    }

    @Transactional
    public ResultVO updateLabState(String labId,short newState) {
        Lab lab = labRepository.findById(labId).orElse(null);
        if(lab != null) {
            lab.setState(newState);
            labRepository.save(lab);
            return ResultVO.success(lab);
        }
        return ResultVO.error(400,"未找到指定实验室!!!");
    }
    @Transactional
    public ResultVO updateLabDescription(String labId,String newDescription) {
        Lab lab = labRepository.findById(labId).orElse(null);
        if(lab != null) {
            lab.setDescription(newDescription);
            labRepository.save(lab);
            return ResultVO.success(lab);
        }
        return ResultVO.error(400,"未找到指定实验室!!!");
    }
    @Transactional
    public ResultVO updateLabManage(String labId,String newManage) {
        Lab lab = labRepository.findById(labId).orElse(null);
        if(lab != null) {
            lab.setManage(newManage);
            labRepository.save(lab);
            return ResultVO.success(lab);
        }
        return ResultVO.error(400,"未找到指定实验室!!!");
    }
}
