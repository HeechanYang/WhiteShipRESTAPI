package info.log.demoinflearnrestapi.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

// id, offline, free 등의 필드값을 제외한 값들만 입력받을 수 있게 '입력값 제한'을 하고 싶음
// 도메인 클래스만을 사용한다면 도메인 클래스에 어노테이션이 너무 많아짐
// 따라서, 이 어노테이션이 어떤 어노테이션인지(Lombok, JPA, Validation 등) 헷갈리게 될 수 있음
// 그렇기 때문에 입력값을 받기 위한 DTO를 따로 생성
// 단점으로는, 중복이 생기기 마련

// 얘는 @Data를 줘도 될 듯~
@Builder @NoArgsConstructor
@Data @AllArgsConstructor
public class EventDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime beginEnrollmentDateTime;
    @NotNull
    private LocalDateTime closeEnrollmentDateTime;
    @NotNull
    private LocalDateTime beginEventDateTime;
    @NotNull
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 몽미
    @Min(0)
    private int basePrice; // (optional)
    @Min(0)
    private int maxPrice; // (optional)
    @Min(0)
    private int limitOfEnrollment;

}
