---
title: WebUtils
tags: Dao,AOP,反射
grammar_cjkRuby: true
---
1.采用反射、注解以及DBUtils，对Dao层进行了抽取，包含了基本的增删改查操作；
2.对Service层也做了一定的抽取；
3.采用工厂模式，实现了降低耦合的作用
4.采用动态代理模式和注解，实现了对service层的事务控制；
5.对servlet进行了抽取，通过判断request中的参数method，利用反射来调用对应的方法。

