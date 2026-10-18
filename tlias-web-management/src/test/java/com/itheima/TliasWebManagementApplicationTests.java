package com.itheima;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;


@ServletComponentScan//开启了spring boot对servlet组件的支持
@SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    void contextLoads() {
    }

}
