package org.keitdk.commons.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.keitdk.commons.core.Formula;
import org.keitdk.commons.core.ItemPage;


/**
 * @author Administrator
 *
 */
public interface CommonService {



	public Session getSession();

	public SessionFactory getSessionFactory() ;

	/***************hql*********************/

	/**
	 * 保存对象
	 * @param <T>
	 *
	 * @param item
	 * @return 记录主键
	 */
	public <T> Serializable save(T item);

	/**
	 * 保存对象
	 * @param <T>
	 *
	 * @param item
	 * @return 记录主键
	 */
	public <T> void save(T[] items);

	/**
	 * 保存对象集合
	 * @param <T>
	 *
	 * @param items
	 */
	public <T> void save(Collection<T> items);

	public <T> void update(T item);

	/**
	 * 修改对象
	 * @param <T>
	 *
	 * @param item
	 */
	public <T> void update(T[] items);

	/**
	 * 修改对象集合
	 * @param <T>
	 *
	 * @param items
	 */
	public <T> void update(Collection<T> items);



	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param id
	 */
	public <T> void delete(Class<T> classType, Serializable id);

	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param id
	 */
	public <T> void delete(Class<T> classType, Serializable[] ids);

	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param ids
	 */
	public <T> void delete(Class<T> classType, Collection<Serializable> ids);



	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param ids
	 */
	public <T> void delete(T item);

	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param ids
	 */
	public <T> void delete(T[] items);

	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param ids
	 */
	public <T> void delete(Collection<T> items);

	/**
	 * 批量更新/删除
	 *
	 * @param hql
	 *            sql语句
	 * @return
	 */
	public int execute(String hql);

	/**
	 * 批量更新/删除
	 *
	 * @param hql
	 *            sql语句
	 * @param args
	 *            参数
	 * @return
	 */
	public int execute(String hql, Object... args);

	/**
	 * 批量更新/删除
	 *
	 * @param hql
	 *            sql语句
	 * @param args
	 *            参数
	 * @return
	 */
	public int execute(String hql, Collection args);

	/**
	 * 取所有对象
	 * @param <T>
	 *
	 * @param classType
	 *            类型
	 * @param orderBys
	 *            排序字段
	 * @return
	 */
	public <T> Collection<T> getAll(Class<T> classType, String orderBys);

	/**
	 * 分页取所有对象
	 * @param <T>
	 *
	 * @param classType
	 *            类型
	 * @param orderBys
	 *            排序字段
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            行数
	 * @return
	 */
	public <T> ItemPage getAll(Class<T> classType, String orderBys, int pageNo,
			int pageSize);

	/**
	 * 根据ID取对象
	 * @param <T>
	 *
	 * @param classType
	 *            类类型
	 * @param id
	 * @return
	 */
	public <T> T getById(Class<T> classType, Serializable id);

	/**
	 * 单个条件取对象
	 * @param <T>
	 *
	 * @param classType
	 *            类型
	 * @param formula
	 *            条件公式
	 * @param orderBys
	 *            排序
	 * @return
	 */
	public <T> Collection<T> getByCondition(Class<T> classType, Formula formula, String orderBys)
			throws RuntimeException;

	/**
	 * 单个条件取一页对象
	 * @param <T>
	 *
	 * @param classType
	 *            类型
	 * @param formula
	 *            条件公式
	 * @param orderBys
	 *            排序
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            行数
	 * @return
	 */
	public <T> ItemPage getByCondition(Class<T> classType, Formula formula, String orderBys, int pageNo,
			int pageSize) throws RuntimeException;


	/**
	 * 取记录总数
	 *
	 * @param hql
	 *            查询语句
	 * @return
	 */
	public long getCount(String hql) throws RuntimeException;

	/**
	 * 取记录总数
	 *
	 * @param output
	 *            查询输出。例如："select count(*) from Object"
	 * @param conditions
	 *            条件集合
	 * @return
	 */
	public long getCount(String output, Collection conditions) throws RuntimeException;

	/**
	 * 建议使用：通过HQL返回数据列表
	 *
	 * @param hql
	 * @return
	 */
	public List<?> query(String hql);

}
