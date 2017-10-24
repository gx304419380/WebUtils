package com.fly.utils.dao;

import java.sql.SQLException;
import java.util.List;

import com.fly.utils.PageBean;

/**
 * 对Dao层进抽取的接口
 * 包括基本的增、删、改、查、分页查询以及自定义sql查询
 * 需要传入泛型T
 */
public interface BaseDao<T> {

	int getCount() throws SQLException;
	T getEntityById(Object id) throws SQLException;
	List<T> getAllEntities() throws SQLException;
	List<T> getEntitiesForPage(PageBean<T> pageBean) throws SQLException;
	List<T> getEntitiesBySql(String sql) throws SQLException;
	
	int deleteEntityById(Object id) throws SQLException;
	int updateEntity(T t) throws SQLException;
	int saveEntity(T t) throws SQLException;
	
}
