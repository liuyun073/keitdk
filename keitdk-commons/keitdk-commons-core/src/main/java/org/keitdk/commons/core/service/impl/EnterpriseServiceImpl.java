package org.keitdk.commons.core.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.keitdk.commons.core.ItemPage;
import org.keitdk.commons.core.OrderBy;
import org.keitdk.commons.core.Pager;
import org.keitdk.commons.core.jdbc.FieldValue;
import org.keitdk.commons.core.jdbc.IConditionValue;
import org.keitdk.commons.core.jdbc.IORTransform;
import org.keitdk.commons.core.jdbc.impl.DefaultConnection;
import org.keitdk.commons.core.service.EnterpriseService;

public class EnterpriseServiceImpl extends CommonServiceImpl implements
		EnterpriseService {

	// ********************hql***************************//

	/**
	 *
	 * @param <T>
	 * @param hql
	 *            查询HQL语句
	 * @param items
	 *            org.trustel.hibernate.FieldValue
	 * @return 更新的行数
	 *
	 *         <pre>
	 *       更新编码为abc的用户名为张三，住址为天河城
	 *       UpdateBuilder builder = new UpdateBuilder(User.class, &quot; where code='abc'&quot;);
	 *       builder.add(&quot;username&quot;, &quot;张三&quot;);
	 *       builder.add(&quot;address&quot;, &quot;天河城&quot;);
	 *       int ret = service.update(builder.getsql(), builder.getValues());
	 * </pre>
	 */
	@Override
	public int update(String hql, List<FieldValue> items) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		if (items != null)
			for (int i = 0; i < items.size(); i++) {
				FieldValue value = (FieldValue) items.get(i);
				if (value.getType() == Types.TIMESTAMP)
					query.setTimestamp(i, (Date) value.getValue());
				else
					query.setParameter(i, value.getValue());

			}
		return query.executeUpdate();
	}

	/**
	 * 通过HQL查询返回所有数据
	 *
	 * @param <T>
	 */
	@Override
	public List<?> query(String hql) {
		List<?> list = getQuery(hql, null).list();
		return list;
	}

	/**
	 * 返回全部数据
	 */
	@Override
	public List<?> query(String hql, List<IConditionValue> values) {
		return getQuery(hql, values).list();
	}

	@Override
	public Pager query(String hql, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}

		Query query = getQuery(hql, null);
		query.setFirstResult((pager.getPageNumber() - 1) * pager.getPageSize());
		query.setMaxResults(pager.getPageSize());

		pager.setList(query.list());

		return pager;
	}

	/**
	 * 返回多条件查询分页数据
	 */
	@Override
	public Pager query(String hql, List<IConditionValue> values, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}

		Query query = getQuery(hql, values);
		query.setFirstResult((pager.getPageNumber() - 1) * pager.getPageSize());
		query.setMaxResults(pager.getPageSize());

		pager.setList(query.list());

		return pager;
	}

	/**
	 * 获取查询总数的HQL
	 *
	 * @param hql
	 * @return
	 */
	@Override
	public String getCountHQL(String hql) {
		return "select count(*) " + hql.substring(hql.indexOf("from"), hql.length());
	}

	/**
	 * 通过查询返回记录数
	 */
	@Override
	public int getRecordCount(String hql) {

		List<?> list = getQuery(hql, null).list();

		return ((Integer) list.get(0)).intValue();

	}

	@Override
	public int getRecordCount(String hql, List<IConditionValue> values) {
		// if (values == null || values.size() == 0)
		// return getRecordCount(hql);

		Query query = getQuery(hql, values);
		List<?> list = query.list();
		return ((Long) list.get(0)).intValue();
	}


	@Override
	public int execute(String hql, List<IConditionValue> list) {
		Query query = getQuery(hql, list);
		int ret = query.executeUpdate();
		return ret;
	}


	// 以下为特殊查询使用的
	@Override
	public List<?> queryByCriteria(DetachedCriteria detachedCriteria, List<OrderBy> orderbys) {

		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());

		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

		addOrder(criteria, orderbys);

		List<?> list = criteria.list();

		return list;

	}

	@Override
	public ItemPage queryByCriteria(DetachedCriteria detachedCriteria, List<OrderBy> orderbys,
			int pageIndex, int pageSize) {


		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());


		Integer total = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(null);

		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageIndex - 1) * pageSize);
		criteria.setMaxResults(pageSize);

		addOrder(criteria, orderbys);

		List<?> list = criteria.list();

		return new ItemPage(list, total, pageIndex, pageSize);
	}



	private void addOrder(Criteria criteria, List<OrderBy> orderbys) {

		if(orderbys==null){
			return;
		}

		Iterator<OrderBy> iter = orderbys.iterator();
		while(iter.hasNext()){
			OrderBy ob = iter.next();

			if (ob.ascend) {
				criteria.addOrder(Order.asc(ob.property));
			} else {
				criteria.addOrder(Order.desc(ob.property));
			}

		}

	}


	/**
	 * 通过HQL查询语句返回查询对像,本方法一般情况下不建议使用
	 *
	 * @param hql
	 * @param values
	 *            org.trustel.hibernate.IConditionValue item
	 * @return
	 */
	protected Query getQuery(String hql, List<IConditionValue> values) {

		Query query = getSession().createQuery(hql);
		if (values != null)
			for (int i = 0; i < values.size(); i++) {
				IConditionValue value = (IConditionValue) values.get(i);
				if (value.getType() == Types.TIMESTAMP)
					query.setTimestamp(i, (Date) value.getValue());
				else
					query.setParameter(i, value.getValue());

			}
		return query;

	}

	/** ******以上为推荐使用查询方法********** */

	/**
	 * 通过HQL查询语句返回查询对像,本方法一般情况下不建议使用
	 *
	 * @param hql
	 * @param values
	 *            org.trustel.hibernate.IConditionValue item
	 * @return sqlQuery
	 */
	protected SQLQuery getsqlQuery(String hql) {
		Session session = getSessionFactory().getCurrentSession();
		return session.createSQLQuery(hql);
	}


	// ********************sql***************************//

	/**
	 * 获取查询总数的sql
	 *
	 * @param sql
	 * @return
	 */
	@Override
	public String getCountsql(String sql) {
		return "select count(1) from (" + sql + ") t";
	}

	/**
	 * 通过sql语句查询Map数据集，只返回一条记录
	 *
	 * @param sql
	 *            sql语句
	 * @param values
	 *            参数
	 * @return
	 */
	@Override
	public Map<String, ?> getBysql(String sql, List<IConditionValue> values) {
		// 所有记录数
		int maxRows = getRecordCountBysql(getCountsql(sql), values);
		// 获取连接
		getConnection();
		// 获取记录集
		ResultSet rs = conn.getDataset(sql, values, maxRows);
		Map<String, ?> map = new HashMap<String, String>();
		try {
			List<String> fieldNames = conn.getFieldNames(rs.getMetaData());
			while (rs.next()) {
				// 将记录集转换成Map
				map = conn.row2Map(rs, fieldNames);
				break;
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		closeDefaultConnection();
		return map;
	}


	/**
	 * 通过sql语句查询全部
	 *
	 * @param sql
	 * @param values
	 * @return
	 */
	@Override
	public List<Map<String, String>> queryBysql(String sql, List<IConditionValue> values) {
		// 所有记录数
		int maxRows = getRecordCountBysql(getCountsql(sql), values);
		// 获取连接
		getConnection();
		// 获取记录集
		ResultSet rs = conn.getDataset(sql, values, maxRows);
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();
		try {
			List<String> fieldNames = conn.getFieldNames(rs.getMetaData());
			while (rs.next()) {
				// 将记录集转换成Map
				items.add(conn.row2Map(rs, fieldNames));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		closeDefaultConnection();
		return items;
	}

	/**
	 * sql 分页查询
	 */
	@Override
	public Pager queryBysql(String sql, List<IConditionValue> values,
			Pager pager) {
		// 总数
		int total = getRecordCountBysql(getCountsql(sql), values);
		pager.setTotalCount(total);

		// 查询
		pager = query(null, sql, values, pager);
		return pager;
	}

	/**
	 * 通过JDBC查询返回对像列表。其中sql和Vaules使用sqlBuilder生成
	 *
	 * @param <T>
	 *
	 * @param transform
	 *            IORTransform 转换查询结果的行集到POJO对象的类
	 * @param nativesql
	 * @param values
	 * @param maxRows
	 *            当maxRows小于1时返回全部数据
	 * @return
	 */
	@Override
	public <T> List<T> query(IORTransform<T> transform, String nativesql,
			List<IConditionValue> values, int maxRows) {
		getConnection();
		ResultSet rs = conn.getDataset(nativesql, values, maxRows);
		List<T> items = new ArrayList<T>();
		try {
			List<String> fieldNames = conn.getFieldNames(rs.getMetaData());
			while (rs.next()) {
				items.add(transform.transform(conn.row2Event(rs, fieldNames)));
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		closeDefaultConnection();
		return items;
	}

	/*
	 * JDBC:通过JDBC查询列出分页数据
	 */
	@Override
	public <T> Pager query(IORTransform<T> transform, String nativesql,
			List<IConditionValue> values, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}

		getConnection();
		ResultSet rs = conn.getDataset(nativesql, values, pager.getPageNumber()
				* pager.getPageSize());
		List<T> items = new ArrayList<T>();
		try {
			List<String> fieldNames = conn.getFieldNames(rs.getMetaData());
			int iFrom = (pager.getPageNumber() - 1) * pager.getPageSize();
			int iTarget = pager.getPageNumber() * pager.getPageSize();
			int index = 1;
			while (rs.next()) {
				if (index > iFrom && index <= iTarget) {
					items.add(transform.transform(conn
							.row2Event(rs, fieldNames)));
				}
				index++;
			}
			rs.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		closeDefaultConnection();
		return pager;
	}

	/**
	 * JDBC:执行查询,代理IConnectionA的方法
	 */
	public boolean execute(String sql, boolean ComposeTrans) {
		getConnection();
		boolean l = conn.execute(sql, ComposeTrans);
		closeDefaultConnection();
		return l;
	}

	/**
	 * JDBC:执行查询
	 *
	 * @param sql
	 * @param composeTrans
	 *            true--合成事务 false--不合成事务
	 * @return
	 */
	public int executeA(String sql, boolean composeTrans) {
		getConnection();
		int l = conn.executeA(sql, composeTrans);
		closeDefaultConnection();
		return l;
	}

	/**
	 * 执行存储过程,支持一个String的参数
	 */
	public long executeStore(String sql, String parameter) {
		getConnection();
		long ret = conn.executeStore(sql, parameter);
		closeDefaultConnection();
		return ret;
	}

	/**
	 * JDBC:通过原始查询语句语句返回字段值
	 */
	public String getFieldValue(String nativesql) {
		getConnection();
		String value = conn.getFieldValue(nativesql);
		closeDefaultConnection();
		return value;

	}

	/**
	 * JDBC:通过sql返回记录数或使用聚合函数返回单值数据
	 *
	 * Example:<br>
	 * select count(*) from tableName
	 *
	 * select sum(f1) from tableName
	 */
	@Override
	public int getRecordCountBysql(String sql) {
		getConnection();
		int value = conn.getRecordCount(sql);
		closeDefaultConnection();
		return value;
	}

	/**
	 * JDBC:通过sql返回记录数或使用聚合函数返回单值数据 <br>
	 * sql和Values使用sqlBuilder生成 Example:<br>
	 * select count(*) from tableName
	 *
	 * select sum(f1) from tableName
	 */
	@Override
	public int getRecordCountBysql(String sql, List<IConditionValue> values) {
		getConnection();
		int value = conn.getRecordCount(sql, values);
		closeDefaultConnection();
		return value;
	}

	/**
	 * 请使用AbstractBuilder构造查询和items
	 *
	 * @param <T>
	 *
	 * @param sql
	 * @param items
	 *            item com.trustel.jdbc.FieldValue
	 * @return
	 */
	@Override
	public int updateBysql(String sql, List<FieldValue> items) {
		getConnection();
		int ret = conn.execute(sql, items);
		closeDefaultConnection();
		return ret;
	}

	private DefaultConnection conn = null;

	/**
	 * JDBC连接代理,仅在类内部使用
	 *
	 * @return
	 */
	private DefaultConnection getConnection() {
		if (conn == null)
			conn = new DefaultConnection();
		conn.setTransBegin(transBegin);
		conn.setTransEnd(transEnd);

		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory())
				.getConnectionProvider();

		try {
			conn.setConnection(cp.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void closeDefaultConnection() {
		if (conn != null)
			conn.close();
	}

	private String transBegin = "begin ";

	private String transEnd = " end;";

	public void setTransBegin(String transBegin) {
		this.transBegin = transBegin;
	}

	public void setTransEnd(String transEnd) {
		this.transEnd = transEnd;
	}





}
