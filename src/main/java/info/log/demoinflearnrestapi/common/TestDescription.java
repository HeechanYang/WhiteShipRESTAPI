package info.log.demoinflearnrestapi.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
// Retention : 이 어노테이션을 붙인 코드를 얼마나 오래 가져갈 것이냐?
// Default : Class
@Retention(RetentionPolicy.SOURCE)
public @interface TestDescription {

    String value() ;
}
