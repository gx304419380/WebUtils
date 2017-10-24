package com.fly.utils.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.fly.utils.C3P0Utils;
import com.fly.utils.annotation.Transaction;

/**
 * 用于实现事务控制
 */
public class InvocationHandlerImpl implements InvocationHandler {

	private Object target;

	public InvocationHandlerImpl() {

	}

	public InvocationHandlerImpl(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		Object returnValue = null;
		
		//是否开启事务注解
		boolean isTransaction = method.isAnnotationPresent(Transaction.class);
		if (!isTransaction) {
			//如果未开启了事务
			System.out.println("未开启事务");
			System.out.println(method.getName());
			returnValue = method.invoke(target, args);  
			return returnValue;  
		} 
		
		//如果开启事务
		System.out.println("开启了事务");
		System.out.println(method.getName());
		try {
			C3P0Utils.beginTransaction();
			returnValue = method.invoke(target, args);  
			C3P0Utils.commitTransaction();
		} catch (Exception e) {
			C3P0Utils.rollbackTransaction();
			throw e;
		} finally {
			C3P0Utils.closeConnection();
		}
		
		return returnValue;  
	}

}
