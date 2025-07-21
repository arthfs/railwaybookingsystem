
<%
if ((session.getAttribute("user") == null)) {
%>
You are not logged in<br/>
<a href="login.jsp">Please Login</a>
<%} else {
%>
Welcome <%=session.getAttribute("user")%> 

<a href='logout.jsp'>Log out</a>
<%
}
%>




<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<link href="/railwaysystem/css/style.css" rel="stylesheet">
</head>
<body>

<% if (request.getParameter("message") != null) { %>
    <div class="success-message">
        <%= request.getParameter("message").replace("+", " ") %>
    </div>
<% } %>

<% if (request.getParameter("error") != null) { %>
    <div class="error-message">
        <%= request.getParameter("error").replace("+", " ") %>
    </div>
<% } %>
		<h1>Home page</h1>
		<form action= 'question' method = 'post'> 
			<label for='question'>Ask a question</label>
			<input name ='question'> 
			<button type = 'submit'> submit </button>  
		</form>
		<div> 
				<form method= 'post' action = 'customer'>  	
					<input type='hidden' name = 'action' value = 'history'>
					<input type='submit' value = "Reservation history">
				</form>
				
				
		</div>
		<div>
			<a href='search_schedules.jsp'> Search schedules</a>
		</div>
		<div>
			<form method='post' action='customer'> 
				<input type='hidden' name = 'action' value='makereservation'>
				<input type='submit' value='Make a reservation'>
			</form> 
		
		</div>
		 
</body>
</html>
