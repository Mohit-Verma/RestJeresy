package web.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import web.bean.Customer;

public class TableOperations {
	
	public void addCustomer(Customer cust) throws ClassNotFoundException, SQLException {
		Connection conn = Reference.DB_UTIL.getConn();
		String query = "insert into Customer values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, cust.getFirstName());
		statement.setString(2, cust.getLastName());
		statement.setString(3, cust.getEmail());
		statement.setString(4, cust.getAddress());
		statement.setString(5, cust.getCity());
		statement.setString(6, cust.getState());
		statement.setString(7, cust.getCountry());
		statement.setInt(8, cust.getPin());
		statement.execute();
		conn.close();		
	}

	public ArrayList<Customer> fetchAllCustomers() throws ClassNotFoundException, SQLException {
		Connection conn = Reference.DB_UTIL.getConn();
		String query = "select * from " + Reference.TABLE_NAME;
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		ArrayList<Customer> list = new ArrayList<Customer>();
		Customer cust;
		while(result.next()) {
			cust = new Customer();
			cust.setFirstName(result.getString(1));
			cust.setLastName(result.getString(2));
			cust.setEmail(result.getString(3));
			cust.setAddress(result.getString(4));
			cust.setCity(result.getString(5));
			cust.setState(result.getString(6));
			cust.setCountry(result.getString(7));
			cust.setPin(result.getInt(8));
			list.add(cust);
		}
		return list;
	}
	
	public void removeCustomer(Customer cust) throws ClassNotFoundException, SQLException {
		Connection conn = Reference.DB_UTIL.getConn();
		String query = "delete from customer where firstName=? and lastName=? and email=? and address=? and city=? and state=? and country=? and pin=? LIMIT 1";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, cust.getFirstName());
		statement.setString(2, cust.getLastName());
		statement.setString(3, cust.getEmail());
		statement.setString(4, cust.getAddress());
		statement.setString(5, cust.getCity());
		statement.setString(6, cust.getState());
		statement.setString(7, cust.getCountry());
		statement.setInt(8, cust.getPin());
		statement.execute();
	}
	
	public boolean tableExists() throws ClassNotFoundException, SQLException {
		Connection conn = Reference.DB_UTIL.getConn();
		String query = "SHOW TABLES LIKE '" + Reference.TABLE_NAME + "'";
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		boolean flag = result.next();
		conn.close();
		if(!flag)
			this.createTable();
		return flag;
	}
	
	private void createTable() throws ClassNotFoundException, SQLException {
		Connection conn = Reference.DB_UTIL.getConn();
		String query = "create table Customer(\r\n" + 
				"	firstName varchar(10),\r\n" + 
				"	lastName varchar(10),\r\n" + 
				"	email varchar(50),\r\n" + 
				"	address varchar(100),\r\n" + 
				"	city varchar(20),\r\n" + 
				"	state varchar(20),\r\n" + 
				"	country varchar(20),\r\n" + 
				"	pin int\r\n" + 
				");";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.execute();
		conn.close();
	}

}
