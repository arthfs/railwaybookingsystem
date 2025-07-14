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
<title></title>
<link href="/railwaysystem/css/style.css" rel="stylesheet">
</head>
<body>
		<h1>welcome customer service</h1>
		
		<div> <a href=''> Update train schedules</a>  </div>
		<div> <a href=''> Reply to customer questions</a>  </div>
		<div> <a href=''> 	Get	list of all customers who have reservations on a given transit line and
date</a>  </div>
		<div> <a href=''> Get list of all schedules for a given station </a>  </div>
		


</body>
</html>