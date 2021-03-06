package com.fly.utils.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.fly.utils.C3P0Utils;
import com.fly.utils.PageBean;
import com.fly.utils.annotation.Id;
import com.fly.utils.annotation.NoneProperty;

/**
 * BaseDao的抽象实现类
 * 包括基本的增、删、改、查、分页查询以及自定义sql查询
 * 需要传入泛型T
 * 1.利用反射获取泛型参数类型===>对应于表名
 * 2.利用反射和注解获取主键Field===>对应于主键
 * 3.利用反射获取其他字段Field===>对应于普通字段
 * 4.拼接sql语句
 * 5.利用反射获取对象属性值
 * 6.拼接传入参数
 * 7.利用DBUtils QueryRunner来执行sql语句，获取返回值
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	private Class classT;
	private Field idField;
	private List<Field> normalFields;

	public BaseDaoImpl() {
		//获取泛型参数类型
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		classT = (Class) types[0];

		//通过注解的方式，获取Id字段以及普通字段
		Field[] fields = classT.getDeclaredFields();
		normalFields = new ArrayList<>();
		for (Field field : fields) {
			boolean isId = field.isAnnotationPresent(Id.class);
			boolean isNoneProperty = field.isAnnotationPresent(NoneProperty.class);
			if (isId) {
				idField = field;
			} else if (!isNoneProperty) {
				normalFields.add(field);
			}
		}
	}

	/**
	 * 获取记录总数
	 */
	@Override
	public int getCount() throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "select count(*) from " + classT.getSimpleName().toLowerCase();
		System.out.println(sql);
		Long count = (Long) qr.query(C3P0Utils.getConnnection(), sql, new ScalarHandler());
		return count.intValue();
	}

	/**
	 * 查询单条数据，简单
	 */
	@Override
	public T getEntityById(Object id) throws SQLException {
		QueryRunner qr = new QueryRunner();
		//select * from table_name where id_name = ?
		String sql = "select * from " + classT.getSimpleName().toLowerCase() + " where " + idField.getName() + " = ?";
		System.out.println(sql);
		Object[] params = {id};
		T t = qr.query(C3P0Utils.getConnnection(), sql, new BeanHandler<T>(classT), params);
		return t;
	}

	/**
	 * 查询所有数据，简单
	 */
	@Override
	public List<T> getAllEntities() throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "select * from " + classT.getSimpleName().toLowerCase();
		System.out.println(sql);
		List<T> list = qr.query(C3P0Utils.getConnnection(), sql, new BeanListHandler<T>(classT));
		return list;
	}
	/**
	 * 获取分页数据
	 */
	@Override
	public List<T> getEntitiesForPage(PageBean<T> pageBean) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "select * from " + classT.getSimpleName().toLowerCase() + " limit ?,?";
		System.out.println(sql);
		Object[] params = {pageBean.getStartIndex(), pageBean.getPageSize()};
		List<T> list = qr.query(C3P0Utils.getConnnection(), sql, new BeanListHandler<T>(classT), params);
		return list;
	}

	/**
	 * 自定义查询
	 */
	@Override
	public List<T> getEntitiesBySql(String sql) throws SQLException {
		QueryRunner qr = new QueryRunner();
		System.out.println(sql);
		List<T> list = qr.query(C3P0Utils.getConnnection(), sql, new BeanListHandler<T>(classT));
		return list;
	}


	/**
	 * 删除单条数据，简单
	 */
	@Override
	public int deleteEntityById(Object id) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "delete from " + classT.getSimpleName().toLowerCase() + " where " + idField.getName() + " = ?";
		Object[] params = {id};
		System.out.println(sql);
		int rows = qr.update(C3P0Utils.getConnnection(), sql, params);
		return rows;
	}

	/**
	 * 更新单条数据，复杂
	 * 通过反射找到所有字段
	 * 通过反射获取各字段具体值
	 * 组合sql语句
	 * 组合参数数组
	 */
	@Override
	public int updateEntity(T t) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update " + classT.getSimpleName().toLowerCase() + " set ";
		List<Object> paramList = new ArrayList<>();
		for (int i = 0; i < normalFields.size(); i++) {
			paramList.add(getProperty(t, normalFields.get(i)));
			if (i == normalFields.size() - 1) {
				sql += normalFields.get(i).getName() + "=? ";
			} else {
				sql += normalFields.get(i).getName() + "=?,";
			}
		}
		paramList.add(getProperty(t, idField));
		sql += "where " + idField.getName() + "=?";
		System.out.println(sql);

		Object[] params = paramList.toArray();
		int rows = qr.update(C3P0Utils.getConnnection(), sql, params);
		return rows;
	}

	/**
	 * 保存单条数据，复杂
	 * 通过反射找到所有字段
	 * 通过反射获取各字段具体值
	 * 组合sql语句
	 * 组合参数数组
	 */
	@Override
	public int saveEntity(T t) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into " + classT.getSimpleName().toLowerCase() + " (" + idField.getName();
		String sql2 = ") values (?";

		List<Object> paramList = new ArrayList<>();
		paramList.add(getProperty(t, idField));

		for (int i = 0; i < normalFields.size(); i++) {
			paramList.add(getProperty(t, normalFields.get(i)));
			sql += "," + normalFields.get(i).getName();
			sql2 += ",?";
		}

		sql += sql2 + ")";
		System.out.println(sql);

		Object[] params = paramList.toArray();
		int rows = qr.update(C3P0Utils.getConnnection(), sql, params);
		return rows;
	}

	/**
	 * 通过反射获取字段的值
	 */
	private Object getProperty(T t, Field field) {
		String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
		try {
			Method getter = classT.getMethod(getterName);
			return getter.invoke(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
