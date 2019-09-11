package com.wp.Books;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class SearchBook
 */
@WebServlet("/SearchBook")
public class SearchBook extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out=response.getWriter();
		String subject=request.getParameter("subject");
		
		try {
    		
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booksdata","root","root");
    		String sql="select bcode,btitle,author,subject,price from books where subject=?";
    		PreparedStatement ps=con.prepareStatement(sql);
    		ps.setString(1,subject);
    		
    		ResultSet rs= ps.executeQuery();
    		while(rs.next())
    		{
    			out.println("<html>");
    			out.println("<body>");
    			out.println("<table border=2>");
				out.println("<tr>");
				out.println("<td>BCode</td>");
				out.println("<td>" +rs.getString(1)+ "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Title</td>");
				out.println("<td>" + rs.getString(2) + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Author</td>");
				out.println("<td>" + rs.getString(3) + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Subject</td>");
				out.println("<td>" + rs.getString(4) + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Price</td>");
				out.println("<td>"+rs.getString(5)+"</td>");
				out.println("</tr>");
				out.println("</table>");
    			out.println("<hr>");
    			out.println("</body>");
    			out.println("</html>");
    			
    		}
    		
    		out.println("<a href=buyerpage.jsp>Buyer-Page</a><br>");
    			
	    	
    		
		}
		catch(Exception e)
		{
				out.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
