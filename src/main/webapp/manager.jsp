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
<title>Insert</title> 
<link href="/railwaysystem/css/style.css" rel="stylesheet">
</head>
<body >
<h1>Welcome manager</h1>
		
		<div> 
				
				<div>
					<form method='get' action ='manager'>
							<input type='hidden' name='action' value='update'>
							<input  type= 'submit' value ="Update a customer representative's informations"  >
					</form>    
				</div>

				<div>
					<form method="get" action="manager">
						<input type="hidden" name="action" value="get_revenue">
						<label>Search by:
							<select name="by">
								<option value="customer">Customer Username</option>
								<option value="transitline">Transit Line Name</option>
							</select>
						</label>
						<input type="text" name="keyword" placeholder="Enter value" required>
						<input type="submit" value="Get Revenue By Transitline or Customer">
					</form>
				</div>

				  
				<div>
					    <form method="get" action="manager">
					        <input type="hidden" name="action" value="sales">
					        <label for="year">Year:</label>
					        <input type="number" name="year" id="year" min="2000" max="2099" required>
					        <label for="month">Month:</label>
					        <select name="month" id="month" required>
					            <option value="">Select Month</option>
					            <option value="01">January</option>
					            <option value="02">February</option>
					            <option value="03">March</option>
					            <option value="04">April</option>
					            <option value="05">May</option>
					            <option value="06">June</option>
					            <option value="07">July</option>
					            <option value="08">August</option>
					            <option value="09">September</option>
					            <option value="10">October</option>
					            <option value="11">November</option>
					            <option value="12">December</option>
					        </select>
					        <input type="submit" value="Get Sales Report">
					    </form>
			</div>

				
				<div>
					<form method="get" action="manager">
						<input type="hidden" name="action" value="get_reservation">
						<label>Search by:
								<select name="by">
										<option value="customer">Customer Username</option>
										<option value="transitline">Transit Line Name</option>
								</select>
						</label>
						<input type="text" name="keyword" placeholder="Enter value" required>
						<input type="submit" value="Get list of reservations by transit line or by customer name">
					</form>
				</div>
				
				<div> 
					<form method='get' action ='manager'>
						<input type='hidden' name='action' value='highest_revenue'>
						<input  type= 'submit' value ='Get the customer who generated the most total revenue '  >
					</form>  
				
				</div>
				
				<div>
				    <form method='get' action='manager'>
				        <input type='hidden' name='action' value='active'>
				        <label for="month">Month (optional):</label>
				        <select name="month" id="month">
				            <option value="">All Months</option>
				            <option value="01">January</option>
				            <option value="02">February</option>
				            <option value="03">March</option>
				            <option value="04">April</option>
				            <option value="05">May</option>
				            <option value="06">June</option>
				            <option value="07">July</option>
				            <option value="08">August</option>
				            <option value="09">September</option>
				            <option value="10">October</option>
				            <option value="11">November</option>
				            <option value="12">December</option>
				        </select>
				        <label for="year">Year (optional):</label>
				        <input type="number" name="year" id="year" min="2000" max="2099">
				        <input type='submit' value='Show Top 5 Transit Lines'>
				    </form>  
</div>
		
		</div>
</body>
</html>