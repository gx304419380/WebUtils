package com.fly.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Id注解，该注解用于javabean的属性
 * 如果一个javabean中的属性添加此注解
 * 则说明该属性对应于数据库表中的主键
 * 该注解主要用于拼接sql语句
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

}
