package org.keitdk.commons.core.jdbc.impl;

import java.sql.Connection;


/**
 * 本类接管第三方获得的连接，但不负责关闭连接
 * 
 * @author Administrator
 * 
 */
public class DefaultConnection extends AbastractConnect {
	
	public DefaultConnection(Connection conn) {
		this.conn = conn;
	}

	public DefaultConnection() {
	}

}
