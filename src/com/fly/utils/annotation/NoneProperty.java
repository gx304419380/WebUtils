package com.fly.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NoneProperty注解，该注解用于javabean的属性
 * 如果javabean的某个属性使用该注解
 * 说明此属性在数据库表中无对应字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoneProperty {

}