package org.keitdk.commons.core.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

import org.keitdk.commons.core.RequestParams;

public interface IConnection extends IJDBCConnection {

	public Connection getConnection();

	public void setConnection(Connection conn);

	/**
	 * 通过查询返回数据集
	 */
	public ResultSet getDataset(String sql);

	public ResultSet getDataset(String sql, int maxRows);

	/**
	 * 通过查询语句返回数据集
	 *
	 * @param sql
	 *            使用QueryBuilder构成的查询
	 * @param values
	 *            使用QueryBuilder构成的Values
	 * @return
	 */
	public ResultSet getDataset(String sql, List<IConditionValue> values);

	public ResultSet getDataset(String sql, List<IConditionValue> values, int maxRows);

	/**
	 * 通过查询语句返回记录数,当查询失败时返回0
	 */
	public int getRecordCount(String sql);

	/**
	 * 通过查询语句返回记录条数
	 *
	 * @param sql
	 *            使用QueryBuilder构成的查询语句
	 * @param values
	 *            通过QueryBuilder返回的List
	 */
	public int getRecordCount(String sql, List<IConditionValue> values);

	/**
	 * 将当前数据行转换为RequestParams对像
	 *
	 * @param row
	 *            数据集
	 * @param fieldNames
	 *            字段列表
	 */
	public RequestParams row2Event(ResultSet row, List<String> fieldNames);

	/**
	 * 通过查询返回第一行
	 */
	public RequestParams getListBysql(String sql);

	/**
	 * 通过查询将编码+名称保存成按指定分隔符的字符串格式
	 *
	 * @param sql
	 *            查询语句
	 * @param itemSplit
	 *            项分隔符
	 * @param valueSplit
	 *            值分隔符
	 */
	public String getListBysql(String sql, String itemSplit, String valueSplit);

	/**
	 * 通过查询返回使用指定分隔符的单列串
	 */
	public String getListOneBysql(String sql, String valueSplit);

	/**
	 * 通过编码取名称
	 *
	 * @param code
	 *            编码
	 * @param list
	 *            使用;分隔项和,分隔值的字符串
	 */
	public String getNameByList(String code, String list);

	/**
	 * 通过MetaData返回字段名称列表
	 */
	public List<String> getFieldNames(ResultSetMetaData metaData);

	/**
	 * 返回只有一列的数据列表
	 */
	public void disconnect();


}
