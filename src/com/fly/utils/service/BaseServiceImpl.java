package com.fly.utils.service;

import java.sql.SQLException;
import java.util.List;

import com.fly.utils.PageBean;
import com.fly.utils.dao.BaseDao;

public abstract class BaseServiceImpl<T> implements BaseService<T>{

	public abstract BaseDao<T> getDao();
	
	@Override
	public int getCount() throws SQLException {
		return getDao().getCount();
	}
	
	@Override
	public T getEntityById(Object id) throws SQLException {
		return getDao().getEntityById(id);
	}

	@Override
	public List<T> getAllEntities() throws SQLException {
		return getDao().getAllEntities();
	}
	
	@Override
	public PageBean<T> getEntitiesForPage(PageBean<T> pageBean) throws SQLException {
		int total = getDao().getCount();
		List<T> rows = getDao().getEntitiesForPage(pageBean);
		pageBean.setTotal(total);
		pageBean.setRows(rows);
		return pageBean;
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
	
	@Override
	public List<T> getEntitiesBySql(String sql) throws SQLException {
		return getDao().getEntitiesBySql(sql);
	}

}
