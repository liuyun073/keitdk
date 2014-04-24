package org.keitdk.commons.core.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

import org.keitdk.commons.core.jdbc.impl.AbastractConnect;

/**
 * 本类可以使用连接池，但通常不接管第三方提供的连接，如果第三方要求接管者释放连接则可以
 *
 * @author Administrator
 *
 */
public class JNDIConnection extends AbastractConnect {

	protected String dataSourceJNDI = "java:/comp/env/jdbc/ringsdb";

	protected String getDataSourceJNDI() {
		return dataSourceJNDI;
	}

	public void setDataSourceJNDI(String jndi) {
		dataSourceJNDI = jndi;
	}

	public Connection getConnection() {
		if (conn == null) {

			//Debug.println("getConnection begin...", true);
			try {
				DataSource ds = DataSourceUtils.getInstance(dataSourceJNDI);// (DataSource)
				//Debug.println("getDatasource Ok!", true);
				conn = ds.getConnection();
				//Debug.println("Success get connection", true);
			} catch (Exception e) {
				//Debug.println("数据连接池错误：" + e.getMessage());
				e.printStackTrace();
			}
		}
		return conn;
	}

	public void close() {
		disconnect();
	}

}
