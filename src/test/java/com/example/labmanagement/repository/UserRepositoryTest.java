package com.example.labmanagement.repository;

import com.example.labmanagement.dox.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByAccount() {
        User user = userRepository.findByAccount("user1");
        if (user != null) {
            log.info("Found user: {}", user);
        } else {
            log.info("No user found with account 'user1'");
        }
    }
    @Test
    void testDeleteById() {
        userRepository.deleteById("1321677837063520256");
    }
}