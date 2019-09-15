package info.log.demoinflearnrestapi.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        Event event = new Event();
        String name = "Event";
        String description = "Spring";
        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    private Object[] parametersForTestFree() {
        return new Object[]{
                new Object[]{0, 0, true},
                new Object[]{0, 100, false},
                new Object[]{100, 0, false},
                new Object[]{100, 200, false}
        };
    }

    @Test
    @Parameters(method = "parametersForTestFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private Object[] parametersForTestOffline() {
        return new Object[]{
                new Object[]{"서울과학기술대학교 미래관 313호", true},
                new Object[]{"", false},
                new Object[]{null, false}
        };
    }

    @Test
    @Parameters(method = "parametersForTestOffline")
    public void testOffline(String location, boolean isOffline) {
        // Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .location(location)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(isOffline);

        // Given
        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .location("서울과학기술대학교 미래관 313호")
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isTrue();

    }

}