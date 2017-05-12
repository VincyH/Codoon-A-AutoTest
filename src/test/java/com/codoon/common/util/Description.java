package com.codoon.common.util;

/**
 * Created by huangjingqing on 17/5/11.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
    int P1 = 1;
    int P2 = 2;
    int P3 = 3;
    int P4 = 4;

    String expectation();

    String steps();

    int priority();
}

