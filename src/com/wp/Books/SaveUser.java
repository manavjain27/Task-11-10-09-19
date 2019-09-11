package com.wp.Books;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SaveUser
 */
@WebServlet("/SaveUser")
public class SaveUser extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;
	ServletContext context = getServletContext();
	String driver = context.getInitParameter("driver");
	String url = context.getInitParameter("url");
	String dbusername = context.getInitParameter("dbusername");
	String dbpassword = context.getInitParameter("dbpassword");
	
	public void init(){
		try{
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url,dbusername,dbpassword);
		String sql="insert into users values(?,?,?,?,?,?)";
		ps=con.prepareStatement(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void destroy(){
		try{
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		//reads-request
		String userid=request.getParameter("userid");
		String password=request.getParameter("password");
		String username=request.getParameter("username");
		String address=request.getParameter("address");
		String mobile=request.getParameter("mobile");
		String email=request.getParameter("email");
		//process
		try{
			ps.setString(1, userid);
			ps.setString(2, password);
			ps.setString(3, username);
			ps.setString(4, address);
			ps.setString(5, mobile);
			ps.setString(6, email);
			ps.executeUpdate();
			out.println("Registration Completed");
			
		}catch(Exception e){
			out.println(e);
		}
		//provides-response
		
	}

}
