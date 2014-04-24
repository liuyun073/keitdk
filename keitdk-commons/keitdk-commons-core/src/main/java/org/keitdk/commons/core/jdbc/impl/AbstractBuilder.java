package org.keitdk.commons.core.jdbc.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.keitdk.commons.core.jdbc.FieldValue;
import org.keitdk.commons.core.jdbc.IBuilder;

public abstract class AbstractBuilder implements IBuilder {
	protected List items = new ArrayList();
	/** 表名 */
	protected String tableName;
	/** where 条件 */
	protected String whereCondition ;

	/**
	 * 构造HQL语句，Hibernate使用
	 * 
	 * @param classType
	 *            O/R映射类名
	 */
	public AbstractBuilder(Class classType) {
		this.tableName = getClassName(classType);
	}

	/**
	 * 构造sql语句，JDBC使用
	 * 
	 * @param tableName
	 *            DBMS库表名称
	 */
	public AbstractBuilder(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * 构造方法
	 * @param classType 类
	 * @param whereCondition where 条件
	 */
	public AbstractBuilder(Class classType , String whereCondition){
		this.tableName = getClassName(classType) ;
		this.whereCondition = whereCondition ;
	}
	
	/**
	 * 构造方法
	 * @param tableName 表名
	 * @param whereCondition where 条件
	 */
	public AbstractBuilder(String tableName , String whereCondition){
		this.tableName = tableName;
		this.whereCondition = whereCondition ;
	}


	private void add(FieldValue item) {
		items.add(item);
	}

	/**
	 * 增加一个字节数组更新处理,在非Papare中不能使用。
	 * 
	 * BLOB字段时使用
	 * 
	 * @param fieldName
	 *            字段名，O/R表示属性名
	 * @param value
	 *            字段值
	 */
	public void addField(String fieldName, byte[] value) {
		add(new FieldValue(fieldName, value));
	}

	/**
	 * 日期字段
	 * 
	 * @param fieldName
	 * @param value
	 *            日期值。建议转换成 东八区(GMT+8)时间，取出时不转换
	 */
	public void addField(String fieldName, Date value) {
		add(new FieldValue(fieldName, value));
	}

	public void addField(String fieldName, double value) {
		add(new FieldValue(fieldName, value));
	}

	public void addField(String fieldName, float value) {
		add(new FieldValue(fieldName, value));
	}

	public void addField(String fieldName, int value) {

		add(new FieldValue(fieldName, value));
	}

	public void addField(String fieldName, long value) {
		add(new FieldValue(fieldName, value));
	}

	/**
	 * 
	 * @param fieldName
	 * @param value
	 * @param type
	 *            sql.Types中的值
	 */
	public void addField(String fieldName, Object value, int type) {
		add(new FieldValue(fieldName, value, type));
	}

	public void addField(String fieldName, String value) {
		add(new FieldValue(fieldName, value));
	}

	protected synchronized String getClassName(Class type) {
		String name = type.getName();

		return name.substring(name.lastIndexOf('.') + 1);
	}

	/**
	 * 对应Hibernate返回HQL,JDBC返回sql
	 * 
	 * 注意byte[]等数据只能使用参数方式
	 * 
	 * @param prepare
	 *            true表示返回使用参数的查询，否则所有参数合成在查询语句中
	 * @return
	 */
	public abstract String getsql(boolean prepare);

	/**
	 * 参数值
	 * 
	 * @return
	 */
	public List getValues() {
		return items;
	}

}
