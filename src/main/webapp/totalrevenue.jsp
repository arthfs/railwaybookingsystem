<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>total revenue</h1>
	<form method='get' action = 'manager'>
		<label for= 'datatosearch'> enter the name of the transitline or customer</label>
		<input type= 'hidden' name='action' value = 'gettotalrevenue'>
		<input name='datatosearch'>
		
		<button  type='submit'>Submit</button>
	</form>
	
</body>
</html>