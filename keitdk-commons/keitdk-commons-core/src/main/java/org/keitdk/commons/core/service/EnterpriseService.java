package org.keitdk.commons.core.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.keitdk.commons.core.ItemPage;
import org.keitdk.commons.core.OrderBy;
import org.keitdk.commons.core.Pager;
import org.keitdk.commons.core.jdbc.FieldValue;
import org.keitdk.commons.core.jdbc.IConditionValue;
import org.keitdk.commons.core.jdbc.IORTransform;


public interface EnterpriseService extends CommonService {


	// ********************hql***************************//

	public int update(String hql, List<FieldValue> items) ;


	/**
	 * 建议使用：通过HQL返回数据列表
	 *
	 * @param hql
	 * @return
	 */
	public List<?> query(String hql);


	/**
	 * 建议使用:通过HQL返回全部数据
	 *
	 * @param hql
	 * @param values
	 *            org.trustel.hibernate.ConditionValue
	 * @return
	 */
	public List<?> query(String hql, List<IConditionValue> values);

	/**
	 * 分页对象更改为 pager
	 *
	 * 李桥文
	 */
	public Pager query(String hql, Pager pager);


	/**
	 * 建议使用:通过 HQL返回分页数据
	 *
	 * @param hql
	 * @param values
	 *            org.trustel.hibernate.ConditionValue
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            页大小
	 * @param recordCount
	 *            记录总数
	 * @return ItemPage
	 */
	public Pager query(String hql, List<IConditionValue> values, Pager pager);



	/**
	 * 获取查询总数的HQL
	 * @param hql
	 * @return
	 *
	 * by 彭逢春
	 */
	public String getCountHQL(String hql);


	/**
	 * 建议使用：通过HQL返回记录总数
	 *
	 * @param hql
	 * @return
	 */
	public int getRecordCount(String hql);

	/**
	 * 建议使用：通过HQL返回记录总数
	 *
	 * @param hql
	 * @param values
	 *            org.trustel.hibernate.ConditionValue item
	 * @return int
	 */
	public int getRecordCount(String hql, List<IConditionValue> values);


	public int execute(String hql, List<IConditionValue> list);


	public List<?> queryByCriteria(DetachedCriteria detachedCriteria, List<OrderBy> orderbys);

	public ItemPage queryByCriteria(DetachedCriteria detachedCriteria, List<OrderBy> orderbys,
			int pageIndex, int pageSize) ;




	// ********************sql***************************//


	/**
	 * 获取查询总数的sql
	 * @param sql
	 * @return
	 *
	 * by 彭逢春
	 */
	public String getCountsql(String sql);


	/**
	 * 通过sql语句查询Map数据集，只返回一条记录
	 * @param sql sql语句
	 * @param values 参数
	 * @return
	 */
	public Map<String, ?> getBysql(String sql , List<IConditionValue> values) ;

	/**
	 * 通过sql语句查询全部<br/>
	 * 2011-6-23 by pengfch<br/>
	 * @param sql sql语句
	 * @param values 参数
	 * @return
	 */
	public List<Map<String, String>> queryBysql(String sql , List<IConditionValue> values) ;


	/**
	 * 使用sql语句分页查询，建议使用该方法<br/>
	 * 2011-6-21 by pengfch<br/>
	 * @param sql sql查询语句
	 * @param values 查询条件，是ConditionValue的集合
	 * @param pager 分页对象，列表数据的格式List&lt;Map&gt;
	 * @return
	 */
	public Pager queryBysql(String sql, List<IConditionValue> values , Pager pager);


	/*
	 * JDBC:通过JDBC查询列出分页数据
	 */
	public <T> Pager query(IORTransform<T> transform, String nativesql, List<IConditionValue> values, Pager pager);

	/**
	 * 通过JDBC查询返回对像列表。其中sql和Vaules使用sqlBuilder生成
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
	public <T> List<T> query(IORTransform<T> transform, String nativesql, List<IConditionValue> values, int maxRows);


	/**
	 * JDBC:执行查询
	 */
	public boolean execute(String sql, boolean ComposeTrans);

	/**
	 * 执行查询
	 *
	 * @param sql
	 * @param composeTrans
	 *            true--合成事务 false--不合成事务
	 * @return
	 */
	public int executeA(String sql, boolean composeTrans);


	/**
	 *
	 * 执行存储过程,支持一个String参数
	 *
	 * @param sql
	 *            存储过程调用语句如 exec storeprocedurename paramlist
	 * @return 影响的数据行数
	 */
	public long executeStore(String sql,String parameter);


	/**
	 * JDBC:通过原始查询语句语句返回字段值
	 */
	public String getFieldValue(String nativesql);

	/**
	 * JDBC:通过sql返回记录数或使用聚合函数返回单值数据
	 *
	 * Example:<br>
	 * select count(*) from tableName
	 *
	 * select sum(f1) from tableName
	 */
	public int getRecordCountBysql(String sql);

	/**
	 * JDBC:通过sql返回记录数或使用聚合函数返回单值数据 <br>
	 * sql和Values使用sqlBuilder生成 Example:<br>
	 * select count(*) from tableName
	 *
	 * select sum(f1) from tableName
	 */
	public int getRecordCountBysql(String sql, List<IConditionValue> values);

	/**
	 * 请使用AbstractBuilder构造查询和items
	 *
	 * @param sql
	 * @param items
	 *            item com.trustel.jdbc.FieldValue
	 * @return
	 */
	public int updateBysql(String sql, List<FieldValue> items);



	/**
	 * Oracle:begin<br>
	 * MYsql:begin;<br>
	 * MSsql:begin begin tran
	 *
	 * @param transBegin
	 */
	public void setTransBegin(String transBegin);

	/**
	 * Oracle: end;<br>
	 * MYsql: 空串<br>
	 * MSsql: commit tran; end;
	 *
	 * @param transEnd
	 */
	public void setTransEnd(String transEnd);

}
