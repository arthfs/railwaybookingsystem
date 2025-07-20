<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <% if (request.getParameter("registrationSuccess") != null) { %>
    <script>
        alert("Account created successfully! You can now log in.");
    </script>
<% } %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/railwaysystem/css/style.css" rel="stylesheet">
</head>
<body>
<h1> Login Page</h1>

	<form action='login' method = "post">
		<div class='loginsection' style = "display: flex; row-gap: 30px; flex-direction: column ;">
		<div> 
			<label for='username'> username </label>  
			<input name ='username' id='username' > 
		
		</div>	
		
		<div> 
		
		<label for='password'> password </label> 
		<input name = 'password' id='password'> 
		
		
		</div> 
		
		<div> <button type= "submit"> Login</button> </div> 
		<div> <a href='register.jsp'> Register</a> </div> 
		</div>
	</form>

</body>
</html>