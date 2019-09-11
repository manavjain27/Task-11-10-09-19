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

/**
 * Servlet implementation class BookDetailsServlet
 */
@WebServlet("/BookDetailsServlet")
public class BookDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("code");
		PrintWriter out = response.getWriter();
		
		ServletContext context=getServletContext();
		String driver=context.getInitParameter("driver");
		String url=context.getInitParameter("url");
		String dbusername=context.getInitParameter("dbusername");
		String dbpassword=context.getInitParameter("dbpassword");
		
		
		try {
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url,dbusername,dbpassword);
			String sql = "SELECT * from books where bcode=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(code));
			ResultSet rs = ps.executeQuery();

			//Second Approach
			/*
			 * boolean found = false; Cookie ck[] = request.getCookies();
			 * 
			 * int s = 0; if (ck != null) { for (Cookie c : ck) { String name = c.getName();
			 * if (name.equals("bcode")) { s = Integer.parseInt(c.getValue()) + 1;
			 * c.setValue(String.valueOf(Integer.parseInt(c.getValue()) + 1));
			 * response.addCookie(c); found = true; } }
			 * 
			 * if (!found) { Cookie c1 = new Cookie("bcode", String.valueOf(1));
			 * c1.setMaxAge(60 * 60 * 24 * 7); response.addCookie(c1); }
			 * 
			 * }
			 * 
			 * else { Cookie c2 = new Cookie("bcode", String.valueOf(1)); c2.setMaxAge(60 *
			 * 60 * 24 * 7); response.addCookie(c2); }
			 */
			
			out.println("<html>");
			out.println("<html><body>");
			out.println("<h3>Book-Details</h3>");
			out.println("<hr>");
			 
				int count=1;
				String visit="";
			  Cookie cookie[] = request.getCookies();
			
			  
			  int found=0;
			  if(cookie==null)
				  {
				  Cookie c = new Cookie("visits",code); 
				  c.setMaxAge(60*60*24); 
				  response.addCookie(c); 
				  visit=c.getValue();
				  }
			  else
			  {
			  for(Cookie cc:cookie)
			  {
				  if(cc.getName().equals("visits")) 
					  found=1;
			  }
			  if(found==0)
			  {
			  Cookie c = new Cookie("visits",code); 
			  c.setMaxAge(60*60*24); 
			  response.addCookie(c); 
			  visit=c.getValue();
			  }
			  
			  else if(found==1) 
			  {
				  
				  for (Cookie ck : cookie ) {
					  if(ck.getName().equals("visits"))
					  {
					  visit = ck.getValue();
					  String tmp="_";
					  ck.setValue(visit+tmp+code);
					  response.addCookie(ck);
					
					  }  
					}
				  
				  String srr[] = visit.split("_");
				  
				  
				  for(int i=0;i<srr.length;i++)
				  {
					  if(srr[i].equals(code))
						  count++;
				  }
				 
			  }
			 
			  }
			  
			  

			
			
			
			while (rs.next()) {
				String bcode = rs.getString(1);
				String title = rs.getString(2);
				String author = rs.getString(3);
				String subject = rs.getString(4);
				String price = rs.getString(5);
				out.println("<table border=2>");
				out.println("<tr>");
				out.println("<td>BCode</td>");
				out.println("<td>" + bcode + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Title</td>");
				out.println("<td>" + title + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Author</td>");
				out.println("<td>" + author + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Subject</td>");
				out.println("<td>" + subject + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Price</td>");
				
				int p=Integer.parseInt(price);
				int newprice=Integer.parseInt(price);
				
				if(count>10)
				{
					out.println("<td>"+(newprice=newprice+p*20/100)+"</td>");
				}
				else if(count>5 && count<=10)
				{
					out.println("<td>"+(newprice=newprice+p*10/100)+"</td>");

				}
				else
				{
					out.println("<td>"+p+"</td>");					
				}
				
				
				out.println("</tr>");
				out.println("</table>");
			}
			out.println("<hr>");
			out.println("<a href=CartManager?code=" + code + ">Add-To-Cart</a><br>");
			out.println("<a href=SubjectPageServlet>Subject-Page</a><br>");
			out.println("<a href=buyerpage.jsp>Buyer-Page</a><br>");
			out.println("</body></html>");

		} catch (Exception e) {
			out.println(e);
		}
	}
}
