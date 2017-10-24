package com.fly.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Transaction注解
 * 该注解用于方法
 * 如果方法上有该注解，则此方法会开启事务
 * （注意，需要在该方法所在的类上添加AOP注解）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Transaction {

}
