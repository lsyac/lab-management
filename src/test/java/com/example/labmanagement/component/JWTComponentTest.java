package com.example.labmanagement.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JWTComponentTest {

    @Autowired
    private JWTComponent jwtComponent;

    @Test
    public void testEncode() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("uid", "123456");
        payload.put("role", "user");
        payload.put("password", "abc123");
        String token = jwtComponent.encode(payload);
        System.out.println("Generated JWT Token: " + token);
    }
}