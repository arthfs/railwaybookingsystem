<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Register</h1>
	<form method ='post' action='customer'>
		
		<div>
		 <label for = 'firstname'> First Name</label>
		 <input name= 'firstname'>
		</div>
		 
		 <div>
		  <label for = 'lastname'> Last Name</label>
		 <input name= 'lastname'>
		 </div>
		 
		 <div>
		  <label for = 'username'> Username</label>
		 <input name='username'>
		 </div>
		 
		 <div> 
		  <label for = 'password'> Password </label>
		 <input name= 'password'>
		 </div>
		 
		 
		 <div>
		 <label for = 'email'> Email </label>
		 <input name= 'email'>
		 </div>
		 
		 <div>
		 <label for = 'birthdate'>Birthdate</label>
		 <input type = 'date' name= 'birthdate'>
		 </div>
		 
		 <div>
		 <label>disabled</legend>
		  <label for = 'disabledno'>no</label>
		 <input id='disabledno' type='radio' name = 'disabled' value='no' checked>
		 
		  <label for = 'disabledyes'>yes</label>
		 <input id='disabledyes' type='radio' name = 'disabled' value='yes'>
		 </div>
		 
		 
		 
		 <input type='hidden' name = 'action' value='registercustomer'>
		<button type='submit'> Register</button>
	</form>
</body>
</html>