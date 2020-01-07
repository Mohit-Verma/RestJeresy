package web.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import web.util.DBUtil;
import web.util.Reference;
import web.util.TableOperations;

@Path("/db")
public class DBConfigRestServices {
	
	@SuppressWarnings("finally")
	@POST
	@Path("/config")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String isValidDBCredentials(String dbData, @Context HttpServletResponse servletResponse, @Context HttpServletRequest servletRequest) throws IOException{
		if(dbData == null || dbData.trim() == "")
			return "-1:No data recieved for establishing the connection";
		Gson gson = new Gson();
		DBUtil db = gson.fromJson(dbData, DBUtil.class);
		Reference.DB_UTIL = db;
		String msg = "";
		
		try {
			db.getConn();
			new TableOperations().tableExists();
			msg = "0:Successfully connected to Database";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			msg = "1:Error in communicating with the Database";
		} finally {
			return msg;
		}
	}
}
