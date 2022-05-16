package com.example.revenueshare;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ContextConfiguration(classes = RevenueShareApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
class RevenueShareApplicationTests {


    /* ================================================
    * [테스트 Plan]
    *  -. Import 또는 ApplicationRunner를 이용한 테스트 Data Setup
    *  -. CRUD 테스트 케이스 작성
    *  -. @Display에 테스트 케이스 목적 작성
    *  -. then 절 Assert 지향
    ================================================ */

//    @Test
//    void contextLoads() {
//    }

}
