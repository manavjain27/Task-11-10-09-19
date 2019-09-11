package com.wp.Books;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SubjectPageServlet
 */
@WebServlet("/SubjectPageServlet")
public class SubjectPageServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		
		HttpSession session=request.getSession();
		String userid=(String)session.getAttribute("user");
        
        if(userid==null){
            response.sendRedirect("index.jsp");
        }
        
        
		ServletContext context = getServletContext();
		String driver = context.getInitParameter("driver");
		String url = context.getInitParameter("url");
		String dbusername = context.getInitParameter("dbusername");
		String dbpassword = context.getInitParameter("dbpassword");
		
		
		try{
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url,dbusername,dbpassword);
		String sql="SELECT distinct subject from books";
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		
		
		String s="All Books";
		Cookie ck[]=request.getCookies();
		if(ck!=null)
		{
			for(Cookie c:ck)
			{
				String name=c.getName();	
				if(name.equals("subjectchoice"))
				{
					s=c.getValue();
				}
			}
			
		}
		out.println("<html>");
		out.println("<html><body>");
		out.println("<marquee><h4>Attractive Offers On "+s+"</h4></marquee>");
		out.println("<h3>Select The Desired Subject</h3>");
		out.println("<hr>");
		while(rs.next()){
			String sub=rs.getString(1);
			out.println("<a href=BookListServlet?subject="+sub+">");
			out.println(sub);
			out.println("</a><br>");
		}
		out.println("<hr>");
		out.println("<a href=buyerpage.jsp>Buyer-Page</a>");
		out.println("</body></html>");
		
		
		
		
		}catch(Exception e){
			out.println(e);
		}
	}

}
