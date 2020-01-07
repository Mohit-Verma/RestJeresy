package web.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import web.bean.Customer;
import web.util.TableOperations;

@Path("/customer")
public class CustomerRestService {

	@SuppressWarnings("finally")
	@POST
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addCustomer(String customerData, @Context HttpServletResponse servletResponse, @Context HttpServletRequest servletRequest) throws IOException{
		Gson g = new Gson();
		Customer cust = g.fromJson(customerData, Customer.class);
		TableOperations op = new TableOperations();
		String msg = "";
		try {
			op.addCustomer(cust);
			msg = "0:Successfully added the customer in the table. Hit refresh to see the updated table records";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			msg = "1:Error on adding the customer in the table";
		} finally {
			return msg;
		}
	}
	
	@SuppressWarnings("finally")
	@GET
	@Path("/fetchall")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		Gson g = new Gson();
		TableOperations op = new TableOperations();
		String result = "";
		try {
			result = g.toJson(op.fetchAllCustomers());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			result = "[]";
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@POST
	@Path("/remove")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String removeCustomer(String customerData, @Context HttpServletResponse servletResponse, @Context HttpServletRequest servletRequest) throws IOException{
		Gson g = new Gson();
		Customer cust = g.fromJson(customerData, Customer.class);
		TableOperations op = new TableOperations();
		String msg = "";
		try {
			op.removeCustomer(cust);
			msg = "0:Successfully removed the customer from the table. Hit refresh to see the updated table records";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			msg = "1:Error in removing the customer from the table";
		} finally {
			return msg;
		}
	}
	
}
