package info.log.demoinflearnrestapi.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

    // Spring Test 핵심 클래스
    // 웹서버를 띄우지 않고도 스프링 MVC(DispatcherServlet)가 요청을 처리하는 과정을 확인할 수 있기 때문에
    // 컨트롤러 테스트용으로 자주 쓰임
    // Servlet에게 가짜 요청을 보낼 수 있음
    // 웹서버까지 띄우는 테스트보다는 빠르지만 단위테스트보다는 느림
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createEvent() throws Exception {
        mockMvc.perform(
                post("/api/evenets/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isCreated());
    }

}
