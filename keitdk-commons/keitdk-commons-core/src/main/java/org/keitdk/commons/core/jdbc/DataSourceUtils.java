package org.keitdk.commons.core.jdbc;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DataSourceUtils {
    private static DataSource ds = null;

    private static String dataSourceJNDI;

    public static DataSource getInstance(String dsJNDI) {
        if (ds == null || dataSourceJNDI.compareToIgnoreCase(dsJNDI) != 0) {
            dataSourceJNDI = dsJNDI;

            System.out.println("getDataSource begin...");
            try {
                Context ctx = new InitialContext();
                ds = (DataSource) ctx.lookup(dataSourceJNDI);// java:jdbc/comp/evn/
                System.out.println("Success get a DataSource");
            } catch (Exception e) {
            	System.out.println("数据连接池错误：" + e.getMessage());
                // e.printStackTrace();
            }
        }
        return ds;
    }
}
