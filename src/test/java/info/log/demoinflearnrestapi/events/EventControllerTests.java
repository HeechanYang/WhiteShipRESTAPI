package info.log.demoinflearnrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.log.demoinflearnrestapi.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// 더 이상 슬라이싱 테스트면 안 됨
// @WebMvcTest
// @SpringBootTest를 사용하면 Mock들을 한 번에 다 등록할 수 있음(?)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    // Spring Test 핵심 클래스
    // 웹서버를 띄우지 않고도 스프링 MVC(DispatcherServlet)가 요청을 처리하는 과정을 확인할 수 있기 때문에
    // 컨트롤러 테스트용으로 자주 쓰임
    // Servlet에게 가짜 요청을 보낼 수 있음
    // 웹서버까지 띄우는 테스트보다는 빠르지만 단위테스트보다는 느림
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

//    @MockBean
//    EventRepository eventRepository;

    @Test
    @TestDescription("정상적으로 이벤트를 생성하는 테스트")
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 9, 1, 21, 56))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 9, 2, 21, 56))
                .beginEventDateTime(LocalDateTime.of(2019, 9, 2, 21, 56))
                .endEventDateTime(LocalDateTime.of(2019, 9, 2, 21, 56))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists("Location"))
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    @TestDescription("입력받을 수 없는 값을 사용하는 경우에 에러가 발생하는 테스트")
    public void createEventBadRequest() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 9, 1, 21, 56))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 9, 2, 21, 56))
                .beginEventDateTime(LocalDateTime.of(2019, 9, 2, 21, 56))
                .beginEventDateTime(LocalDateTime.of(2019, 9, 2, 21, 56))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(true)
                .offline(true)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("입력값이 비어있는 경우에 에러가 발생하는 테스트")
    public void createEventBadRequestEmptyInput() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("입력값이 잘못된 경우에 에러가 발생하는 테스트")
    public void createEventBadRequestWrongInput() throws Exception {
        // 틀린 인풋
        // 시작 시간이 끝나는 시간보다 빠르거나
        // maxPrice가 200원인데 basePrice가 10000원인 경우
        // 이런 경우 Annotation으로 검증하기 힘든 경우가 많음
        // 그래서 Validator를 따로 만들어 줄 것임
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2019, 9, 1, 23, 56))
                .closeEnrollmentDateTime(LocalDateTime.of(2019, 9, 2, 21, 56))
                .beginEventDateTime(LocalDateTime.of(2019, 9, 2, 24, 56))
                .endEventDateTime(LocalDateTime.of(2019, 9, 2, 23, 56))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }


}