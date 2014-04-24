package org.keitdk.commons.core.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.keitdk.commons.core.Condition;
import org.keitdk.commons.core.Formula;
import org.keitdk.commons.core.ItemPage;
import org.keitdk.commons.core.Select;
import org.keitdk.commons.core.service.CommonService;
import org.keitdk.commons.utils.ArrayUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class CommonServiceImpl implements CommonService {

	private SessionFactory  sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public Session getSession() {

		return getSessionFactory().getCurrentSession();
	}


	/***************hql*********************/


	/**
	 * 生成查询
	 *
	 * @param hql
	 *            查询输出。例如："select a, b.name from A a, B b"
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @return
	 * @throws RuntimeException
	 */
	private Query createQuery(String hql, Collection conditions, String groupBys,
			String orderBys) throws RuntimeException {
		Query query = getSession().createQuery(new Select(hql, conditions, groupBys, orderBys).toString());
		setValue(query, 0, conditions);

		return query;
	}




	/**
	 * 保存对象
	 * @param <T>
	 *
	 * @param item
	 * @return 记录主键
	 */
	public <T> Serializable save(T item) {
		return getSession().save(item);
	}

	/**
	 * 保存对象
	 * @param <T>
	 *
	 * @param item
	 * @return 记录主键
	 */
	public <T> void save(T[] items) {
		for(T item : items){
			save(item);
		}
	}


	/**
	 * 保存对象集合
	 * @param <T>
	 *
	 * @param items
	 * @return
	 */
	public <T> void save(Collection<T> items) {
		Iterator<T> iter = items.iterator();
		while(iter.hasNext()){
			save(iter.next());
		}
	}


	@Override
	public <T> void update(T item) {
		getSession().update(item);
	}

	/**
	 * 修改对象
	 * @param <T>
	 *
	 * @param item
	 */
	@Override
	public <T> void update(T[] items) {
		for(T t : items){
			update(t);
		}

	}

	/**
	 * 修改对象集合
	 * @param <T>
	 *
	 * @param items
	 */
	@Override
	public <T> void update(Collection<T> items) {
		Iterator<T> iter = items.iterator();
		while(iter.hasNext()){
			update(items);
		}
	}


	/**
	 * 删除对象 根据int类型ID
	 * @param <T>
	 *
	 * @param classType
	 * @param ids
	 */
	@Override
	public <T> void delete(Class<T> classType, Serializable id) {

		getSession().delete(getById(classType, id));
	}

	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param ids
	 */
	@Override
	public <T> void delete(Class<T> classType, Serializable[] ids) {
		for(Serializable id : ids){
			delete(classType, id);
		}
	}

	/**
	 * 删除对象
	 * @param <T>
	 *
	 * @param classType
	 * @param ids
	 */
	@Override
	public <T> void delete(Class<T> classType, Collection<Serializable> ids) {
		for(Serializable id : ids){
			delete(classType, id);
		}
	}


	@Override
	public <T> void delete(T item) {
		getSession().delete(item);
	}

	@Override
	public <T> void delete(T[] items) {
		for(T t : items){
			delete(t);
		}
	}

	@Override
	public <T> void delete(Collection<T> items) {
		for(T t : items){
			delete(t);
		}
	}


	/**
	 * 批量更新/删除
	 *
	 * @param query
	 * @return
	 */
	@Override
	public int execute(String hql) {

		return createQuery(hql, null, null, null).executeUpdate();
	}




	/**
	 * 批量更新/删除
	 *
	 * @param hql
	 * @return
	 */
	@Override
	public int execute(String hql, Object... args) {

		List conditions = ArrayUtils.arrayToList(args);

		int ret = createQuery(hql, conditions, null, null).executeUpdate();

		return ret;
	}

	/**
	 * 批量更新/删除
	 *
	 * @param hql
	 * @return
	 */
	@Override
	public int execute(String hql, Collection args) {

		int ret = createQuery(hql, args, null, null).executeUpdate();

		return ret;
	}



	/**
	 * 取所有对象
	 * @param <T>
	 *
	 * @param classType
	 * @param orderBys
	 *            排序字段
	 * @return
	 */
	@Override
	public <T> Collection<T> getAll(Class<T> classType, String orderBys) {

		return query("from " + getClassName(classType), null, null, orderBys);
	}

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
	@Override
	public <T> ItemPage getAll(Class<T> classType, String orderBys, int pageNo,
			int pageSize) {
		return getPage("select count(*) from", "from "
				+ getClassName(classType), null, null, orderBys, pageNo,
				pageSize);
	}

	/**
	 * 根据ID取对象
	 * @param <T>
	 *
	 * @param temp
	 * @param classType
	 * @param id
	 * @return
	 */
	@Override
	public <T> T getById(Class<T> classType, Serializable id) {

		return (T) getSession().get(classType, id);
	}


	@Override
	public <T> Collection<T> getByCondition(Class<T> classType, Formula formula, String orderBys)
			throws RuntimeException {
		List conditions = new ArrayList();
		conditions.add(Condition.newInstance(formula));

		return query("from " + getClassName(classType), conditions, null,
				orderBys);
	}

	@Override
	public <T> ItemPage getByCondition(Class<T> classType, Formula formula,
			String orderBys, int pageNo, int pageSize) throws RuntimeException {
		List conditions = new ArrayList();
		conditions.add(Condition.newInstance(formula));
		return getPage("select count(*) from " + getClassName(classType),
				"from " + getClassName(classType), conditions, null, orderBys,
				pageNo, pageSize);
	}


	/**
	 * 取类名(去掉包名)
	 * @param <T>
	 *
	 * @param type
	 *            类
	 * @return
	 */
	protected synchronized <T> String getClassName(Class<T> type) {
		String name = type.getName();

		return name.substring(name.lastIndexOf('.') + 1);
	}

	public long getCount(String hql) throws RuntimeException {
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	/**
	 * 取记录总数
	 *
	 * @param output
	 *            查询输出。例如："select count(*) from Object"
	 * @param conditions
	 *            条件集合
	 * @return
	 */
	public long getCount(String output, Collection conditions)
			throws RuntimeException {
		return (Long) createQuery(output, conditions, null, null)
				.uniqueResult();
	}


	/**
	 * 查询一页记录
	 *
	 * @param objectName
	 *            对象名称
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 * @return 行数
	 * @throws RuntimeException
	 */
	protected ItemPage getPage(String objectName, Collection conditions,
			String groupBys, String orderBys, int pageNo, int pageSize)
			throws RuntimeException {
		return getPage("select count(*) from " + objectName, "from "
				+ objectName, conditions, groupBys, orderBys, pageNo, pageSize);
	}

	/**
	 * 查询一页记录
	 *
	 * @param countOutput
	 *            总数查询输出。例如："select count(*) from Object"
	 * @param itemOutput
	 *            记录查询输出。例如："select o from Object o"
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 * @return 行数
	 * @throws RuntimeException
	 */
	protected ItemPage getPage(String countOutput, String itemOutput,
			Collection conditions, String groupBys, String orderBys, int pageNo,
			int pageSize) throws RuntimeException {
		return new ItemPage(createQuery(itemOutput, conditions, groupBys,
				orderBys).setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list(), getCount(countOutput,
				conditions), pageNo, pageSize);
	}

	/**
	 * 执行HQL语句查询
	 *
	 * @param hql
	 *            HQL语句
	 * @return
	 * @throws RuntimeException
	 */
	public List query(String hql) throws RuntimeException {
		return getSession().createQuery(hql).list();
	}

	/**
	 * 执行HQL语句查询分页数据
	 *
	 * @param hql
	 *            查询语句
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            行数
	 * @return
	 * @throws RuntimeException
	 */

	public ItemPage query(String hql, int pageNo, int pageSize)
			throws RuntimeException {
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return new ItemPage(query.list(), getCount(hql), pageNo, pageSize);
	}

	/**
	 * 取记录集
	 *
	 * @param output
	 *            查询输出。例如："select o from Object o"
	 * @param conditions
	 *            条件集合
	 * @param groupBys
	 *            'group by' 条件
	 * @param orderBys
	 *            'order by' 条件
	 * @return
	 * @throws RuntimeException
	 */
	protected List query(String output, Collection conditions, String groupBys,
			String orderBys) throws RuntimeException {

		return createQuery(output, conditions, groupBys, orderBys).list();
	}

	/**
	 * 设置变量
	 *
	 * @param query
	 *            查询实例
	 * @param index
	 *            变量序号
	 * @param condition
	 *            条件实例
	 */
	private int setValue(Query query, int index, Formula formula) {
		switch (formula.operator) {
		case Formula.IN:
		case Formula.NI:
			if (formula.type == Formula.SELECT)
				index = setValue(query, index,
						((Select) formula.value).conditions);
			else {
				query
						.setParameterList(formula.name,
								(Collection) formula.value);
				index++;
			}
			break;

		default:
			switch (formula.type) {
			case Formula.BIGDECIMAL:
				query.setBigDecimal(index, (BigDecimal) formula.value);
				break;

			case Formula.BIGINTEGER:
				query.setBigInteger(index, (BigInteger) formula.value);
				break;

			case Formula.BINARY:
				query.setBinary(index, (byte[]) formula.value);
				break;

			case Formula.BOOLEAN:
				query.setBoolean(index, (Boolean) formula.value);
				break;

			case Formula.BYTE:
				query.setByte(index, (Byte) formula.value);
				break;

			case Formula.DATE:
				query.setDate(index, (Date) formula.value);
				break;

			case Formula.DOUBLE:
				query.setDouble(index, (Double) formula.value);
				break;

			case Formula.FLOAT:
				query.setFloat(index, (Float) formula.value);
				break;

			case Formula.INTEGER:
				query.setInteger(index, (Integer) formula.value);
				break;

			case Formula.LONG:
				query.setLong(index, (Long) formula.value);
				break;

			case Formula.SHORT:
				query.setShort(index, (Short) formula.value);
				break;

			case Formula.STRING:
				query.setString(index, (String) formula.value);
				break;

			case Formula.TEXT:
				query.setText(index, (String) formula.value);
				break;

			case Formula.TIME:
				query.setTime(index, (Date) formula.value);
				break;

			case Formula.TIMESTAMP:
				query.setTimestamp(index, (Date) formula.value);
				break;
			}

			index++;
			break;
		}

		return index;
	}

	/**
	 * 设置变量集合
	 *
	 * @param query
	 *            查询实例
	 * @param conditions
	 *            条件集合
	 * @return
	 */
	private int setValue(Query query, int index, Collection<Condition> conditions) {
		if (conditions != null) {
			Iterator<Condition> iter = conditions.iterator();
			while(iter.hasNext()){
				Condition condition = iter.next();

				if (condition.formula instanceof Formula) {
					if (((Formula) condition.formula).containValue())
						index = setValue(query, index, (Formula) condition.formula);
				} else if (condition.formula instanceof List)
					index = setValue(query, index, (List) condition.formula);
			}
		}

		return index;
	}


}
