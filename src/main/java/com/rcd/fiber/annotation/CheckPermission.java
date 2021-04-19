package com.rcd.fiber.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
    boolean value() default true;

    String object() default "";

    String action() default "";

    boolean checkDepartment() default false;
}
