package web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private String dbUrl = "";
	private String userName = "";
	private String password = "";
	private final String driver = "com.mysql.cj.jdbc.Driver";
	
	private Connection conn;

	public Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(dbUrl, userName, password);
		return conn;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}