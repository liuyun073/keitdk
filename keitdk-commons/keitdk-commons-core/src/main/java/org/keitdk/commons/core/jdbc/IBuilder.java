package org.keitdk.commons.core.jdbc;

import java.util.Date;
import java.util.List;

public interface IBuilder {

	/**
	 * 在非Papare中不能使用。
	 * 
	 * BLOB字段时使用
	 * 
	 * @param fieldName
	 *            字段名，O/R表示属性名
	 * @param value
	 *            字段值
	 */
	public void addField(String fieldName, byte[] value);

	/**
	 * 日期字段
	 * 
	 * @param fieldName
	 * @param value
	 *            日期值。建议转换成 东八区(GMT+8)时间，取出时不转换
	 */
	public void addField(String fieldName, Date value);

	public void addField(String fieldName, double value);

	public void addField(String fieldName, float value);

	public void addField(String fieldName, int value);

	public void addField(String fieldName, long value);

	/**
	 * 
	 * @param fieldName
	 * @param value
	 * @param type
	 *            sql.Types中的值
	 */
	public void addField(String fieldName, Object value, int type);

	public void addField(String fieldName, String value);

	/**
	 * 对应Hibernate返回HQL,JDBC返回sql
	 * 
	 * 注意byte[]等数据只能使用参数方式
	 * 
	 * @param prepare
	 *            true表示返回使用参数的查询，否则所有参数合成在查询语句中
	 * @return
	 */
	public String getsql(boolean prepare);

	/**
	 * 参数值
	 * 
	 * @return
	 */
	public List getValues();

}