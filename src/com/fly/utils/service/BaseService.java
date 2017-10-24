package com.fly.utils.service;

import java.sql.SQLException;
import java.util.List;

import com.fly.utils.PageBean;
import com.fly.utils.annotation.Transaction;

public interface BaseService<T> {
	
	int getCount() throws SQLException;
	T getEntityById(Object id) throws SQLException;
	List<T> getAllEntities() throws SQLException;
	PageBean<T> getEntitiesForPage(PageBean<T> pageBean) throws SQLException;
	List<T> getEntitiesBySql(String sql) throws SQLException;
	
	@Transaction
	int deleteEntityById(Object id) throws SQLException;
	@Transaction
	int updateEntity(T t) throws SQLException;
	@Transaction
	int saveEntity(T t) throws SQLException;
	
	
}
