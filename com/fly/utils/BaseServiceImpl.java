package com.fly.utils;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T>{

	public abstract BaseDao<T> getDao();
	
	@Override
	public T getEntityById(Object id) throws SQLException {
		return getDao().getEntityById(id);
	}

	@Override
	public List<T> getAllEntities() throws SQLException {
		return getDao().getAllEntities();
	}
	
	@Override
	public List<T> getAllEntitiesForPage(PageBean<T> pageBean) throws SQLException {
		return getDao().getAllEntitiesForPage(pageBean);
	}

	@Override
	public int deleteEntityById(Object id) throws SQLException {
		return getDao().deleteEntityById(id);
	}

	@Override
	public int updateEntity(T t) throws SQLException {
		return getDao().updateEntity(t);
	}

	@Override
	public int saveEntity(T t) throws SQLException {
		return getDao().saveEntity(t);
	}

}
