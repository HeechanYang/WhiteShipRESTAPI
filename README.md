# WhiteShipRESTAPI
백기선씨의 Inflearn 강의인 `스프링 기반 REST API` 강의를 들으며 실습한 프로젝트

## 추가 의존성

- Spring Web Starter
- Spring Data JPA
- Spring HATEOAS
- REST Docs
- H2
    - test
- PostgreSQL
    - compile
- Lombok
- Java 11

## Domain

### Event

- 입력값
    - name
    - description
    - beginEnrollmentDateTime
    - closeEnrollmentDateTime
    - beginEventDateTime
    - closeEventDateTime
    - location (optional) 이게 없으면 온라인 모임
    - basePrice (optional)
    - maxPrice (optional)
    - limitOfEnrollment
- 결과값
    - id
    - name
    - ...
    - eventStatus : DRAFT, PUBLISHED, ENROLLMENT_STARTED, ...
    - offline
    - free
    - _link
        - profile (for the self-descriptive message)
        - self
        - publish
        - ...