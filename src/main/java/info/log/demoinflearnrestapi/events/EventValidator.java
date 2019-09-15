package info.log.demoinflearnrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getMaxPrice() < eventDto.getBasePrice() && eventDto.getMaxPrice() != 0) {
            errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "maxPrice is wrong.");
        }

        LocalDateTime endEventTime = eventDto.getEndEventDateTime();
        if (endEventTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
        }

        // TODO: 나머지 validate도 만들자
    }
}
