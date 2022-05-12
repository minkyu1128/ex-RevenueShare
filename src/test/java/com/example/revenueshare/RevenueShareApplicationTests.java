package com.example.revenueshare;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ContextConfiguration(classes = RevenueShareApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
class RevenueShareApplicationTests {

    @Test
    void contextLoads() {
    }

}
