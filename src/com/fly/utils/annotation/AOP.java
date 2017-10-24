package com.fly.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * AOP注解
 * 用于类的注解，
 * 增加此注解后，用工厂生成bean时会自动为该对象增加一个代理对象，
 * 该代理对象可以对内部的方法进行注解扫描，
 * 如果方法含有注解@Transaction，则开启事务操作。
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AOP {

}
