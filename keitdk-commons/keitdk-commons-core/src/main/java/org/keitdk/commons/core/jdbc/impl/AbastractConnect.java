package org.keitdk.commons.core.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keitdk.commons.core.RequestParams;
import org.keitdk.commons.core.jdbc.FieldValue;
import org.keitdk.commons.core.jdbc.IConditionValue;
import org.keitdk.commons.core.jdbc.IConnection;
import org.keitdk.commons.utils.date.DateUtils;
//Debug.//Debug;

/**
 * 本类仅实现主要方法，资源控制由具体类实现
 *
 * @author Administrator
 *
 */
public abstract class AbastractConnect implements IConnection {

	protected Connection conn;

	private String transBegin = "begin ";

	private String transEnd = " end;";

	public void close() {
		conn = null;
	}

	/**
	 * 返回只有一列的数据列表
	 */
	public void disconnect() {
		try {
			if (conn != null)
				conn.close();
			//Debug.println("释放数据连接!", true);
		} catch (Exception e) {
			//Debug.print(e, "释放数据连接失败!");
		}
		conn = null;

	}

	/**
	 * 执行查询，sql为标准的查询语句，可以有多个，多个查询一般情况下使用半角分号分隔
	 */
	public boolean execute(String sql) {
		return execute(sql, true);
	}

	/**
	 * 执行查询
	 */
	public boolean execute(String sql, boolean ComposeTrans) {
		try {
			getConnection();
			Statement st = conn.createStatement();
			if (ComposeTrans)
				sql = transBegin + sql + transEnd;
			// if (MSsql)
			// sql = "begin begin tran " + sql + " commit tran; end;";
			// else
			// sql = "begin " + sql + " commit" + sqlSplit + " end "
			// + tranSplit;
			st.execute(sql);
			st.close();
			st = null;
			//Debug.println("执行过程成功:" + sql, true);
			return true;
		} catch (Exception e) {
			//Debug.print(e, sql);
			return false;
		}
	}

	/**
	 * 本方法未实现
	 *
	 * @param sql
	 * @param list
	 * @return
	 */
	public int execute(String sql, List<FieldValue> list) {
		try {
			PreparedStatement query = conn.prepareStatement(sql);

			for (int i = 0; i < list.size(); i++) {
				FieldValue item = (FieldValue) list.get(i);
				// query.setObject(i, item.value, item.type);
				switch (item.getType()) {
				case Types.INTEGER:
					query.setInt(i, ((Integer) item.getValue()).intValue());
					break;
				case Types.SMALLINT:
					query.setShort(i, ((Integer) item.getValue()).shortValue());
				case Types.VARCHAR:
				case Types.CHAR:
					query.setString(i, (String) item.getValue());
					break;
				case Types.FLOAT:
					query.setFloat(i, ((Float) item.getValue()).floatValue());
				case Types.DECIMAL:
				case Types.DOUBLE:
					query.setDouble(i, ((Double) item.getValue()).doubleValue());
				}
			}

			int ret = query.executeUpdate();
			query.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 执行如 where keyName in (values)的查询
	 */
	public int execute(String sql, String keyName, String[] values) {
		int result = 0;
		try {
			if (sql.toUpperCase().lastIndexOf(" where ") > 0)
				sql = sql + " and " + keyName + "in (";
			else
				sql = sql + " where " + keyName + " in (";
			for (int i = 0; i < values.length; i++)
				if (i > 0)
					sql = sql + ",?";
				else
					sql = sql + "?";

			//Debug.println("[//Debug]" + sql + ")", true);
			PreparedStatement query = conn.prepareStatement(sql + ")");

			for (int i = 0; i < values.length; i++)
				query.setString(i + 1, values[i]);
			result = query.executeUpdate();
			query.close();
			query = null;
		} catch (Exception e) {
			//Debug.print(e, "更新");
		}
		return result;
	}

	/**
	 * 以事务方式执行查询
	 *
	 * @param sql
	 * @return
	 */
	public int executeA(String sql) {
		return executeA(sql, true);
	}

	/**
	 * 执行查询
	 *
	 * @param sql
	 * @param composeTrans
	 *            true--合成事务 false--不合成事务
	 * @return
	 */
	public int executeA(String sql, boolean composeTrans) {
		int result = -1;
		getConnection();
		try {
			if (conn != null) {
				if (composeTrans)
					sql = transBegin + sql + transEnd;
				// if (this.MSsql)
				// sql = "begin begin tran" + sql + " commit tran; end;";
				// else
				// sql = "begin " + sql + " commit" + sqlSplit + "end "
				// + tranSplit;
				Statement st = conn.createStatement();

				if (st.execute(sql))
					result = st.getUpdateCount();
				//Debug.println("[success]" + sql, true);
				st.close();
				st = null;
			}
		} catch (Exception e) {
			//Debug.println("[failure]" + sql);
			//Debug.println("execute : " + e.getMessage());
		}
		return result;
	}

	public long executeStore(String sql,String parameter) {
		long ret = 0;
		try {
			conn.setAutoCommit(true);
			CallableStatement st = conn.prepareCall(sql);
			st.setString(1, parameter);
			st.executeUpdate();
			ret = st.getUpdateCount();
			st.close();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			ret = -1;
		}
		return ret;
	}

	public Connection getConnection() {
		return conn;
	}

	/**
	 * 通过查询返回数据集
	 */
	public ResultSet getDataset(String sql) {
		return getDataset(sql, -1);
	}

	public ResultSet getDataset(String sql, int maxRows) {
		ResultSet rs = null;
		getConnection();
		try {
			Statement st = conn.createStatement();
			if (maxRows > 0)
				st.setMaxRows(maxRows);
			rs = st.executeQuery(sql);
			//Debug.println("getDataset 执行成功：" + sql, true);
		} catch (Exception e) {

			//Debug.println("getDataset 执行失败：" + sql);
		}
		return rs;
	}

	/**
	 * 通过查询语句返回数据集
	 *
	 * @param sql
	 *            使用QueryBuilder构成的查询
	 * @param values
	 *            使用QueryBuilder构成的Values
	 * @return
	 */
	public ResultSet getDataset(String sql, List<IConditionValue> values) {
		return getDataset(sql, values, -1);
	}

	public ResultSet getDataset(String sql, List<IConditionValue> values, int maxRows) {
		ResultSet rs = null;
		getConnection();
		try {
			CallableStatement query = conn.prepareCall(sql);
			if (values != null)
				for (int i = 0; i < values.size(); i++) {
					IConditionValue value = (IConditionValue) values.get(i);
					switch (value.getType()) {
					case Types.INTEGER:
						query.setInt(i + 1, ((Integer) value.getValue()).intValue());
						break;
					case Types.SMALLINT:
						query.setShort(i + 1, ((Integer) value.getValue()).shortValue());
						break;
					case Types.VARCHAR:
					case Types.CHAR:
						query.setString(i + 1, (String) value.getValue());
						break;
					case Types.FLOAT:
						query.setFloat(i + 1, ((Float) value.getValue()).floatValue());
						break;
					case Types.DECIMAL:
					case Types.DOUBLE:
						query.setDouble(i + 1, ((Double) value.getValue()).doubleValue());
						break;
					case Types.DATE:
						Date d = (Date) value.getValue();
						// 格式化日期 yyyy-MM-dd HH:mm:ss
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date s = sdf.parse(DateUtils.format(d, "yyyy-MM-dd HH:mm:ss"));

						java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(s.getTime());
						query.setTimestamp(i + 1, sqlTimestamp);
						//query.setDate(i + 1, sqlDate);
						break;
					case Types.TIMESTAMP:
						Timestamp tt = (Timestamp) value.getValue();
						query.setTimestamp(i + 1, tt);
						break;
					}
				}
			if (maxRows > 0)
				query.setMaxRows(maxRows);
			rs = query.executeQuery();
			//Debug.println("getDataset 执行成功：" + sql, true);
		} catch (Exception e) {

			//Debug.println("getDataset 执行失败：" + sql);
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 通过MetaData返回字段名称列表
	 */
	public List<String> getFieldNames(ResultSetMetaData metaData) {
		List<String> arr = new ArrayList<String>();
		try {
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				arr.add(metaData.getColumnName(i));
			}
		} catch (Exception e) {
			//Debug.println("获取字段列表错误:" + e.getMessage());
		}
		return arr;
	}

	/**
	 * 通过查询返回第一行的第一个字段的值，通常查询形为 SELECT FIELDNAME from Table
	 */
	public String getFieldValue(String sql) {
		try {
			getConnection();
			Statement st = conn.createStatement();
			//Debug.println(sql, true);
			ResultSet rs = st.executeQuery(sql);
			String sReturn = null;
			if (rs.next())
				sReturn = rs.getString(1);
			rs.close();
			st.close();
			rs = null;
			st = null;
			return sReturn;

		} catch (Exception e) {
			//Debug.print(e, sql);
			return null;
		}

	}

	/**
	 * 通过查询返回第一行
	 */
	public RequestParams getListBysql(String sql) {
		ResultSet rs = getDataset(sql);
		RequestParams pc = new RequestParams();
		if (rs == null)
			return pc;
		try {
			while (rs.next()) {
				pc.putA(rs.getString(1).trim(), rs.getString(2).trim());
			}
			rs.close();
		} catch (Exception e) {

		}
		return pc;
	}

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
	public String getListBysql(String sql, String itemSplit, String valueSplit) {
		ResultSet rs = getDataset(sql);
		if (rs == null)
			return "";
		try {
			StringBuffer items = new StringBuffer();
			boolean isFirst = true;
			while (rs.next()) {
				if (isFirst)
					items.append(rs.getString(1).trim() + valueSplit + rs.getString(2));
				else
					items.append(itemSplit + rs.getString(1).trim() + valueSplit + rs.getString(2));
				isFirst = false;
			}
			rs.close();
			return items.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 通过查询返回使用指定分隔符的单列串
	 */
	public String getListOneBysql(String sql, String valueSplit) {
		// 返回只有一列的数据列表
		ResultSet rs = getDataset(sql);
		if (rs == null)
			return "";
		try {
			StringBuffer items = new StringBuffer();
			boolean isFirst = true;
			while (rs.next()) {
				if (isFirst)
					items.append(rs.getString(1).trim());
				else
					items.append(valueSplit + rs.getString(1).trim());
				isFirst = false;
			}
			rs.close();
			return items.toString();
		} catch (Exception e) {
			//Debug.print(e, "getlist");
			return "";
		}
	}

	/**
	 * 通过编码取名称
	 *
	 * @param code
	 *            编码
	 * @param list
	 *            使用;分隔项和,分隔值的字符串
	 */
	public String getNameByList(String code, String list) {
		String[] items = list.split(";");
		for (int i = 0; i < items.length; i++) {
			String[] value = items[i].split(",");
			if (value.length == 2)
				if (value[0].compareToIgnoreCase(code) == 0)
					return value[1];
		}
		return code;
	}

	/**
	 * 返回指定名称的序列号
	 */
	public int getNextCode(String name) {
		int iNextCode = 0;
		try {
			getConnection();
			CallableStatement cst = conn.prepareCall("{call getnextcode(?,?) }");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, name);
			cst.execute();
			iNextCode = cst.getInt(1);
			cst.close();
			cst = null;
		} catch (Exception e) {
			//Debug.print(e, "获取名称为" + name + "序号失败:");
		}
		return iNextCode;

	}

	/**
	 * 返回以0作前缀的指定长度的序列号
	 */
	public String getNextCode(String name, int FixLen) {
		return getNextCode(name, FixLen, '0');
	}

	/**
	 * 返回指定名称和长度以及指定前缀的序列号，通常使用0作前缀
	 */
	public String getNextCode(String name, int fixLen, char fixChar) {
		int nextcode = getNextCode(name);
		String stmp = "";
		for (int i = 0; i < fixLen - String.valueOf(nextcode).length(); i++) {
			stmp = stmp + fixChar;
		}

		return stmp + String.valueOf(nextcode);
	}

	/**
	 * 通过查询语句返回记录数,当查询失败时返回0
	 */
	public int getRecordCount(String sql) {
		int iCount = 0;
		try {
			getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
				iCount = rs.getInt(1);
			rs.close();
			rs = null;
		} catch (Exception e) {
			//Debug.print(e, "取数据行失败:" + sql);
		}
		//Debug.println("当前查询有（条）：" + Integer.toString(iCount), true);
		return iCount;
	}

	/**
	 * 通过查询语句返回记录条数
	 *
	 * @param sql
	 *            使用QueryBuilder构成的查询语句
	 * @param values
	 *            通过QueryBuilder返回的List
	 */
	public int getRecordCount(String sql, List<IConditionValue> values) {
		if (values == null || values.size() == 0)
			return getRecordCount(sql);
		ResultSet rs = getDataset(sql, values);
		int iCount = 0;
		if (rs != null) {
			try {
				if (rs.next())
					iCount = rs.getInt(1);
				rs.close();
				rs = null;
			} catch (Exception e) {
				//Debug.print(e, "取数据行失败:" + sql);
			}

		}
		return iCount;
	}

	public int getRecordCountBysql(String sql) {
		return getRecordCount(sql);
	}

	public int getRecordCountBysql(String sql, List<IConditionValue> values) {
		return getRecordCount(sql, values);
	}

	/**
	 * 将当前数据行转换为RequestParams对像
	 *
	 * @param row
	 *            数据集
	 * @param fieldNames
	 *            字段列表
	 */
	public RequestParams row2Event(ResultSet row, List<String> fieldNames) {
		RequestParams arr = new RequestParams();
		try {
			for (int i = 0; i < fieldNames.size(); i++) {
				String sFieldName = (String) fieldNames.get(i).toString();
				arr.putA(sFieldName, row.getString(sFieldName));
			}
		} catch (Exception e) {
			//Debug.print(e, "数据行转event出错");
		}
		arr.putA("database", this);
		return arr;
	}

	/**
	 * 将记录集的一行数据设置到Map对象
	 * by pengfch(2011-6-21)
	 * @param row
	 * @param fieldNames
	 * @return
	 */
	public Map<String, String> row2Map(ResultSet row , List<String> fieldNames) {
		Map<String, String> resultMap = new HashMap<String, String>() ;
		try {
			for (int i = 0; i < fieldNames.size(); i++) {
				String sFieldName = (String) fieldNames.get(i).toString();
				resultMap.put(sFieldName, row.getString(sFieldName));
			}
		} catch (Exception e) {
			//Debug.print(e, "数据行转map出错");
		}
		return resultMap ;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void setTransBegin(String transBegin) {
		this.transBegin = transBegin;
	}

	public void setTransEnd(String transEnd) {
		this.transEnd = transEnd;
	}

}
