package org.keitdk.commons.core.jdbc;

import java.sql.Types;
import java.util.Date;

import org.keitdk.commons.core.jdbc.impl.AbstractBuilder;
import org.keitdk.commons.utils.date.DateUtils;

/**
 * @fun 本类实现基于JDBC的插入sql合成,不支持prepare
 * @author Administrator
 *
 */
public class InsertBuilder extends AbstractBuilder {

	public <T> InsertBuilder(Class<T> classType) {
		super(classType);
	}

	public InsertBuilder(String tableName) {
		super(tableName);
	}

	private Object getExpress(FieldValue item, boolean prepare) {
		if (prepare)
			return "?";
		else {

			switch (item.getType()) {
			case Types.VARCHAR:
			case Types.CHAR:
				return item.getValue() == null ? "null" : "'" + item.getValue() + "'";
			case Types.DATE:
				return "'"
						+ DateUtils.format((Date) item.getValue(), "yyyy-MM-dd")
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
	}

	public String getsql(boolean prepare) {
		;
		StringBuffer fields = new StringBuffer("");
		StringBuffer values = new StringBuffer("");
		for (int i = 0; i < items.size(); i++) {
			FieldValue item = (FieldValue) items.get(i);

			if (i == 0) {
				fields.append(item.getFieldName());
				values.append(getExpress(item, prepare));
			} else {
				fields.append("," + item.getFieldName());
				values.append("," + getExpress(item, prepare));
			}
		}
		if (tableName == null || tableName.trim().equals(""))
			return "(" + fields.toString() + ") values(" + values.toString()
					+ ")";
		else

			return "insert into " + tableName + "(" + fields.toString()
					+ ") values(" + values.toString() + ")";

	}

	/*
	 * private StringBuffer fields = new StringBuffer("");
	 *
	 * private StringBuffer values = new StringBuffer("");
	 *
	 * private String tableName = "";
	 *
	 * private void addField(String fieldName) { if
	 * (!fields.toString().equals("")) fields.append("," + fieldName); else {
	 * fields.append(fieldName); } }
	 *
	 * private void addValue(String value) { if (!values.toString().equals(""))
	 * values.append("," + value); else { values.append(value); } }
	 *
	 * public InsertBuilder() { }
	 *
	 * public InsertBuilder(Class<T> type) { tableName = getClassName(type); }
	 *
	 * public InsertBuilder(String tableName) { this.tableName = tableName; }
	 *
	 * public void addField(String fieldName, Date value, String format, boolean
	 * locate) { addField(fieldName); addValue("'" +
	 * DataConvert.formatDateTime(value, format, locate) + "'"); }
	 *
	 * public void addField(String fieldName, float value) {
	 * addField(fieldName); addValue(String.valueOf(value)); }
	 *
	 * public void addField(String fieldName, int value) { addField(fieldName);
	 * addValue(String.valueOf(value)); }
	 *
	 * public void addField(String fieldName, long value) { addField(fieldName);
	 * addValue(String.valueOf(value)); }
	 *
	 * public void addField(String fieldName, String value) {
	 * addField(fieldName); addValue("'" + value + "'"); }
	 *
	 * public String getsql() { if (fields.toString().equals("")) return "";
	 * else { String updatesql = "(" + fields.toString() + ") values(" +
	 * values.toString() + ")"; if (tableName.equals("")) return updatesql; else
	 * return "insert into " + tableName + updatesql; } }
	 */

}
