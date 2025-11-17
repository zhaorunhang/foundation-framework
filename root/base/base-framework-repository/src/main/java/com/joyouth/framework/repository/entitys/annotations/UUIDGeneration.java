package com.joyouth.framework.repository.entitys.annotations;

import com.joyouth.framework.repository.entitys.CustomUUIDGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@IdGeneratorType(CustomUUIDGenerator.class)  // 元注解指定生成器
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, METHOD})
public @interface UUIDGeneration {
}
