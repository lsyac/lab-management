package com.example.labmanagement.service;

import com.example.labmanagement.dox.Announcement;
import com.example.labmanagement.dox.Lab;
import com.example.labmanagement.dox.User;
import com.example.labmanagement.exception.Code;
import com.example.labmanagement.exception.XException;
import com.example.labmanagement.repository.AnnouncementRepository;
import com.example.labmanagement.repository.LabRepository;
import com.example.labmanagement.repository.LabReservationRepository;
import com.example.labmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LabRepository labRepository;
    private final AnnouncementRepository announcementRepository;
    private final LabReservationRepository labReservationRepository;
    public User getUser(String account) {
        return userRepository.findByAccount(account);
    }
    @Transactional
    public void updateUserPassword(String account) {
        User user = userRepository.findByAccount(account);
        if(user == null) {
            throw XException.builder()
                    .number(Code.ERROR)
                    .message("用户不存在")
                    .build();
        }
        user.setPassword(passwordEncoder.encode(account));
        userRepository.save(user);
    }
    @Transactional
    public void updateUserPassword(String uid, String password) {
        User user = userRepository.findById(uid).orElse(null);
        if(user == null) {
            throw XException.builder()
                    .number(Code.ERROR)
                    .message("用户不存在")
                    .build();
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
    @Transactional
    public List<Lab> listLabs() {
        return labRepository.findAll();
    }
    @Transactional
    public List<Lab> listLabsByState(short state) {
        return labRepository.findByState(state);
    }

    @Transactional
    public List<Announcement> getAllAnnouncements() {
        return (List<Announcement>) announcementRepository.findAll();
    }

    @Transactional
    public void updateUserAvatar(String uid, String base64Avatar) {
        User user = userRepository.findById(uid).orElse(null);
        if(user == null) {
            throw XException.builder()
                    .number(Code.ERROR)
                    .message("用户不存在!!!")
                    .build();
        }
        user.setAvatar(base64Avatar);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String getUserAvatar(String uid) {
        User user = userRepository.findById(uid).orElse(null);
        if(user == null) {
            throw XException.builder()
                    .number(Code.ERROR)
                    .message("用户不存在")
                    .build();
        }
        return user.getAvatar();
    }

}
