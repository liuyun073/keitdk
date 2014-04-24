package org.keitdk.commons.core.jdbc;

import java.util.List;

public interface IJDBCConnection {

	public void close();

	/**
	 * 执行查询，sql为标准的查询语句，可以有多个，多个查询一般情况下使用半角分号分隔
	 */
	public boolean execute(String sql);

	/**
	 * 执行查询
	 */
	public boolean execute(String sql, boolean ComposeTrans);

	/**
	 * 本方法未实现
	 * 
	 * @param sql
	 * @param list
	 * @return
	 */
	public int execute(String sql, List<FieldValue> list);

	/**
	 * 执行如 where keyName in (values)的查询
	 */
	public int execute(String sql, String keyName, String[] values);

	/**
	 * 以事务方式执行查询
	 * 
	 * @param sql
	 * @return
	 */
	public int executeA(String sql);

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
	 * 执行存储过程
	 * 
	 * @param sql
	 *            存储过程调用语句如 exec storeprocedurename paramlist
	 * @return 影响的数据行数
	 */
	public long executeStore(String sql,String parameter);
	/**
	 * 通过查询返回第一行的第一个字段的值，通常查询形为 SELECT FIELDNAME from Table
	 */
	public String getFieldValue(String sql);

	/**
	 * 返回指定名称的序列号
	 */
	public int getNextCode(String name);

	/**
	 * 返回以0作前缀的指定长度的序列号
	 */
	public String getNextCode(String name, int FixLen);

	/**
	 * 返回指定名称和长度以及指定前缀的序列号，通常使用0作前缀
	 */
	public String getNextCode(String name, int fixLen, char fixChar);

	/**
	 * 通过查询语句返回记录数,当查询失败时返回0
	 */
	public int getRecordCountBysql(String sql);

	/**
	 * 通过查询语句返回记录条数
	 * 
	 * @param sql
	 *            使用QueryBuilder构成的查询语句
	 * @param values
	 *            通过QueryBuilder返回的List
	 */
	public int getRecordCountBysql(String sql, List<IConditionValue> values);

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
