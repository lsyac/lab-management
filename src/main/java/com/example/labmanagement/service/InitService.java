package com.example.labmanagement.service;

import com.example.labmanagement.dox.User;
import com.example.labmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        long count = userRepository.count();
        if (count > 0) {
            return;
        }
        User u1 = User.builder()
                .name("刘松于")
                .role(User.ADMIN)
                .account("admin")
                .password(passwordEncoder.encode("123456"))
                .build();
        u1 = userRepository.save(u1);
        String[] accounts = {"user1", "user2", "user3", "user4"};
        String[] names = {"王波", "李莉", "李琰", "单颖"};
        for (int i = 0; i < names.length; i++) {
            User u = User.builder()
                    .name(names[i])
                    .account(accounts[i])
                    .password(passwordEncoder.encode("123456"))
                    .role(User.USER)
                    .build();
            userRepository.save(u);
        }
    }
}
