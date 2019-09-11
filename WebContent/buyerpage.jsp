<%
String id = (String) session.getAttribute("user");
			if (id == null)
			{
				response.sendRedirect("index.jsp");
			}
%>

<html>
<body>
	<h3>DashBoard-For-Buyer</h3>
	<hr>
	<h2>Welcome : <%=id%></h2>
	<hr>
	<pre>
		<a href="SubjectPageServlet">Explore-Store</a>
		<a href="BookSearch.jsp">Search-Book</a>
		<a href="DisplayCart">View-Cart</a>
		<a href="">Trace-Order</a>
		<a href="Logout">Logout</a>
	</pre>		
	<hr>
</body>
</html>