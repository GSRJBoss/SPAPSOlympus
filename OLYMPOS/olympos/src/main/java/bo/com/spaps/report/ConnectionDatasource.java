package bo.com.spaps.report;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 
 * @author David.Ticlla.Felipe
 *
 */
@Stateless
public class ConnectionDatasource {

	private static Connection conn = null;

	public ConnectionDatasource() {
	}

	static public Connection getConnection() {
		try{
			if(conn == null){
				Context ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup("java:jboss/datasources/spapsDS");
				conn = ds.getConnection();
			}
			return conn;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public void closeConnection(){
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
