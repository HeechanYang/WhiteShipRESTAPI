package info.log.demoinflearnrestapi.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder(){
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){
        Event event = new Event();
        String name = "Event";
        String description = "Spring";
        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @Test
    public void testFree(){
        // Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isTrue();

        // Given
        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isTrue();
    }

    @Test
    public void testOffline(){
        // Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isFalse();

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