package me.leewonjun.dewminas;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/*
이력서 CURD 테스트용 클래스
 */


@SpringBootTest
@AutoConfigureMockMvc
public class ResumeApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void mockSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
