package org.keitdk.commons.core.jdbc;

import java.util.List;

import javax.xml.validation.Schema;

import org.keitdk.commons.core.ItemPage;

/**
 * 本类用来兼容Hibernate版本的IEnterprise,<br>
 * 但由于配置信息问题需要比Hibernate稍有不同<br>
 * <li>需要配置Schema,可以通过Spring注入
 * <li>当Schema为null或找不到对应的配置时时，所有List返回的项为RequestParams<br>
 * 可以通过requestParams.getXX方法获取相关数据
 *
 * @author Administrator
 *
 */
public interface IJDBCEnterpriseService {

	/**
	 * 设置数据连接的映射配置信息
	 *
	 * @param schema
	 *            数据库配置信息，使用hibernate的配置文件
	 */
	public void setSchema(Schema schema);

	/**
	 * 通过JDBC获取分页数据
	 *
	 * @param sql
	 *            sql查询语句，
	 * @param pageIndex
	 *            需要显示的页码
	 * @param pageSize
	 *            每页大小
	 * @param type
	 *            查询结果行需要转换成的POJO类
	 * @return
	 */
	public List query(String sql, int pageIndex, int pageSize, Class type);

	/**
	 * 通过JDBC返回分页数据
	 *
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param recordCount
	 * @param type
	 * @return
	 */
	public ItemPage query(String sql, int pageIndex, int pageSize,
			int recordCount, Class type);

	// 以下为建议使用查询方法
	/**
	 * 通过JDBC返回所有数据
	 *
	 * @param sql
	 *            sql查询语句
	 * @param type
	 *            查询结果需要转换的目标类
	 * @return
	 */
	public List query(String sql, Class type);

	/**
	 * 通过sql返回记录的行数
	 *
	 * @param sql
	 *            使用如select count(*) from tableName where...
	 * @return
	 */

	public int getRecordCount(String sql);

	/**
	 * 通过JDBC返回带参数的查询记录条数年
	 *
	 * @param sql
	 *            使用JDBCBuilder生成的sql
	 * @param values
	 *            使用JDBCBuilder生成的values
	 * @return
	 */
	public int getRecordCount(String sql, List values);

	/**
	 * 通过JDBC返回分页数据
	 *
	 * @param sql
	 *            使用JDBCBuilder生成的sql
	 * @param values
	 *            使用JDBCBuilder生成的values
	 * @param pageIndex
	 *            当前页号
	 * @param pageSize
	 *            每页大小
	 * @param recordCount
	 *            总记录数
	 * @param type
	 *            返回类
	 * @return
	 */
	public ItemPage query(String sql, List values, int pageIndex, int pageSize,
			int recordCount, Class type);

	/**
	 * 通过JDBC返回所有数据
	 *
	 * @param sql
	 *            使用JDBCBuilder生成的sql
	 * @param values
	 *            使用JDBCBuilder生成的values
	 * @param type
	 *            返回类
	 * @return
	 */

	public List query(String sql, List values, Class type);
}
