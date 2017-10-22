package com.fly.utils;

import java.sql.SQLException;
import java.util.List;

public interface BaseService<T> {
	
	public T getEntityById(Object id) throws SQLException;
	public List<T> getAllEntities() throws SQLException;
	public List<T> getAllEntitiesForPage(PageBean<T> pageBean) throws SQLException;
	public int deleteEntityById(Object id) throws SQLException;
	public int updateEntity(T t) throws SQLException;
	public int saveEntity(T t) throws SQLException;
	
}
