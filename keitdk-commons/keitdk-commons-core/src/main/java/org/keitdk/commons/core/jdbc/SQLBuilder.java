package org.keitdk.commons.core.jdbc;

import java.util.Date;

import org.keitdk.commons.core.QueryAction;
import org.keitdk.commons.core.data.DataConvert;


/**
 * @fun sql合成对象,建议使用HQLBuilder
 * @deprecated
 * @author Administrator
 * @version 0.0.0.1 2003年06月
 */

public class SQLBuilder extends QueryAction {

	public static final boolean ForHQL = true;

	/**
	 *
	 */
	public static String composeWhere(String sql, String fieldName,
			String fieldValue, int action) {
		if (fieldValue == null || fieldValue.trim().compareTo("") == 0)
			return sql;
		String sTmp = fieldValue.trim();
		String sOper = "=";
		switch (action) {
		case EQUAL:
			sTmp = "'" + sTmp + "'";
			break;
		case LIKE:
			sTmp = "'%" + sTmp + "%'";
			sOper = " like ";
			break;
		case GE:
			sTmp = "'" + sTmp + "'";
			sOper = " >= ";
			break;
		case LE:
			sTmp = "'" + sTmp + "'";
			sOper = " <= ";
			break;
		case IN:
			sTmp = "('" + sTmp + "')";
			sOper = " in ";
			break;
		default:
			sTmp = "'" + sTmp + "'";
		}
		if (ForHQL) {
			if (sql == null || sql.trim().compareTo("") == 0)
				return " WHERE " + fieldName + sOper + sTmp;
			else
				return sql + " AND " + fieldName + sOper + sTmp;
		} else if (sql == null || sql.trim().compareTo("") == 0)
			return " WHERE RTrim(" + fieldName + ")" + sOper + sTmp;
		else
			return sql + " AND RTrim(" + fieldName + ")" + sOper + sTmp;
	}

	public static String composeDateWhere(String sql, String fieldName,
			String fieldValue, int action) {
		if (fieldValue == null || fieldValue.trim().compareTo("") == 0)
			return sql;
		String sTmp = fieldValue;
		String sOper = "=";
		switch (action) {
		case EQUAL:
			sTmp = "'" + sTmp + "'";
			break;
		case LIKE:
			sTmp = "'%" + sTmp + "%'";
			sOper = " like ";
			break;
		case GE:
			sTmp = "'" + sTmp + "'";
			sOper = " >= ";
			break;
		case LE:
			sTmp = "'" + sTmp + "'";
			sOper = " <= ";
			break;
		case IN:
			sTmp = "('" + sTmp + "')";
			sOper = " in ";
			break;
		default:
			sTmp = "'" + sTmp + "'";
		}
		if (sql == null || sql.trim().compareTo("") == 0)
			return " WHERE " + fieldName + sOper + sTmp;
		else
			return sql + " AND " + fieldName + sOper + sTmp;
	}

	/**
	 *
	 */
	public static String composeWhere(String sql, String fieldName,
			int fieldValue, int action) {

		return composeWhere(sql, fieldName, Integer.toString(fieldValue),
				action);

	}

	/**
	 *
	 */
	public static String composeWhere(String sql, String fieldName,
			boolean fieldValue, int action) {
		String value = "1";
		if (fieldValue)
			value = "2";
		return composeWhere(sql, fieldName, value, action);
	}

	/**
	 *
	 */
	public static String composeWhere(String sql, String fieldName,
			float fieldValue, int action) {

		// if(fieldValue.toString().compareToIgnoreCase("0.0")==0)
		// return sql;
		String value = Float.toString(fieldValue);
		if (value.compareToIgnoreCase("0.0") == 0)
			return sql;
		return composeWhereForNum(sql, fieldName, value, action);

	}

	/**
	 *
	 */
	public static String composeWhereForNum(String sql, String fieldName,
			String fieldValue, int action) {
		// String sTmp = String.valueOf(fieldValue);
		if (fieldValue == null || fieldValue.trim().compareTo("") == 0)
			return sql;
		String sTmp = fieldName + " = " + fieldValue;
		switch (action) {
		case EQUAL:
			break;
		case LIKE:
			break;
		case GE:
			sTmp = fieldName + " >= " + fieldValue;
			break;
		case LE:
			sTmp = fieldName + " <= " + fieldValue;
			break;
		case IN:
			sTmp = fieldName + " in (" + fieldValue + ")";
			break;
		}
		if (sql == null || sql.trim().compareTo("") == 0)
			return " WHERE " + sTmp;
		else
			return sql + " AND " + sTmp;
	}

	/**
	 *
	 */
	public static String composeInsertFields(String sql, String fieldName) {
		if (sql == null || sql.trim().compareTo("") == 0)
			return fieldName;
		else
			return sql + "," + fieldName;
	}

	/**
	 *
	 *
	 */
	public static String composeInsertValues(String sql, String fieldValue) {
		if (fieldValue == null) {
			fieldValue = "null";
			if (sql == null || sql.trim().compareTo("") == 0)
				return fieldValue;
			else
				return sql + "" + fieldValue.trim() + ",";
		} else if (sql == null || sql.trim().compareTo("") == 0)
			return "'" + fieldValue.trim() + "'";
		else
			return sql + ",'" + fieldValue.trim() + "'";
	}

	public static String composeInsertValues(String sql, Date fieldValue,
			String format) {
		if (fieldValue == null) {
			if (sql == null || sql.trim().compareTo("") == 0)
				return "null";
			else
				return sql + ",null";
		} else
			return composeInsertValues(sql, DataConvert.formatDateTime(
					fieldValue, format));

	}

	public static String composeInsertValues(String sql, Date fieldValue) {
		return composeInsertValues(sql, fieldValue, "yyyy-MM-dd");
	}

	/**
	 *
	 */
	public static String composeInsertValues(String sql, int fieldValue) {
		return composeInsertValuesForNum(sql, Integer.toString(fieldValue));
	}

	/**
	 *
	 */
	public static String composeInsertValues(String sql, float fieldValue) {
		return composeInsertValuesForNum(sql, Float.toString(fieldValue));
	}

	/**
	 *
	 */
	public static String composeInsertValues(String sql, boolean fieldValue) {
		String value = "1";
		if (fieldValue)
			value = "2";
		return composeInsertValuesForNum(sql, value);
	}

	/**
	 *
	 */
	public static String composeInsertValuesForNum(String sql, String fieldValue) {
		if (fieldValue == null || fieldValue.trim().compareTo("") == 0)
			fieldValue = "null";
		if (sql == null || sql.trim().compareTo("") == 0)
			return fieldValue;
		else
			return sql + "," + fieldValue;
	}
}
