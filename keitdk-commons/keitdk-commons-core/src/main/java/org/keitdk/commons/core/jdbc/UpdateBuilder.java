package org.keitdk.commons.core.jdbc;

import java.sql.Types;
import java.util.Date;

import org.keitdk.commons.core.jdbc.impl.AbstractBuilder;
import org.keitdk.commons.utils.date.DateUtils;

/**
 * 更新查询语句合成器，支持prepare
 *
 * 当前版本 2.0
 *
 *
 * @author Administrator
 */
public class UpdateBuilder extends AbstractBuilder {
	/**
	 * 构造HQL语句，Hibernate使用
	 *
	 * @param classType
	 *            O/R映射类名
	 */
	public UpdateBuilder(Class classType) {
		super(classType);
	}
	/**
	 * 构造sql语句，JDBC使用
	 *
	 * @param tableName
	 *            DBMS库表名称
	 */
	public UpdateBuilder(String tableName) {
		super(tableName);
	}
	/**
	 * 构造方法
	 * @param classType 类
	 * @param whereCondition where 条件
	 */
	public UpdateBuilder(Class classType , String where) {
		super(classType,where);
	}
	/**
	 * 构造方法
	 * @param tableName 表名
	 * @param whereCondition where 条件
	 */
	public UpdateBuilder(String tableName , String where) {
		super(tableName,where);
	}

	private Object getExpress(FieldValue item, boolean prepare) {
		if (prepare)
			return "?";

		switch (item.getType()) {
		case Types.VARCHAR:
		case Types.CHAR:
			return "'" + item.getValue() + "'";
		case Types.DATE:
			return "'" + DateUtils.format((Date) item.getValue(), "yyyy-MM-dd")
					+ "'";

		case Types.TIME:
			return "'" + DateUtils.format((Date) item.getValue(), "HHmm:ss")
					+ "'";
		case Types.TIMESTAMP:
			return "'"
					+ DateUtils.format((Date) item.getValue(),
							"yyyy-MM-dd HHmm:ss") + "'";
		default:
			return item.getValue();

		}
	}

	public String getsql(boolean prepare) {
		StringBuffer sql = new StringBuffer("");
		for (int i = 0; i < items.size(); i++) {
			FieldValue item = (FieldValue) items.get(i);
			if (i == 0)
				sql.append(item.getFieldName() + "=" + getExpress(item, prepare));
			else
				sql.append("," + item.getFieldName() + "="
						+ getExpress(item, prepare));
		}
		if (tableName == null || tableName.trim().equals(""))
			return sql.toString()+" " + this.whereCondition;
		else
			return "update " + tableName + " set " + sql.toString()+" "+ this.whereCondition;
	}

}
