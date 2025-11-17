package com.zrh.framework.core.annotations;

import com.zrh.framework.core.validators.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通用日期范围校验器
 * 支持类型: String, LocalDate, LocalDateTime, Date, Instant, ZonedDateTime
 */
@Target(ElementType.TYPE)  // 类级别注解
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {
    String message() default "开始时间必须早于结束时间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startField();

    String endField();

    String pattern(); // 字符串日期格式
}
