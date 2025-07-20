<%@ page import="java.sql.*" %>
<html>
<head><title>Manage Train Schedules</title></head>
<body>
<h2>Train Schedules</h2>
<table border="1">
<tr>
    <th>Name</th>
    <th>Origin</th>
    <th>Destination</th>
    <th>Departure</th>
    <th>Arrival</th>
    <th>Actions</th>
</tr>
<%
    String url = "jdbc:mysql://localhost:3306/railwaybookingsystem";
    String user = "root";
    String password = "Freestyle99+-";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM transitline");

        while(rs.next()) {
%>
<tr>
    <td><%= rs.getString("name") %></td>
    <td><%= rs.getString("origin") %></td>
    <td><%= rs.getString("destination") %></td>
    <td><%= rs.getTime("departure_time") %></td>
    <td><%= rs.getTime("arrival_time") %></td>
    <td>
        <form action="edit_schedule.jsp" method="get" style="display:inline;">
            <input type="hidden" name="name" value="<%= rs.getString("name") %>">
            <input type="submit" value="Edit">
        </form>
        <form action="delete_schedule.jsp" method="post" style="display:inline;">
            <input type="hidden" name="name" value="<%= rs.getString("name") %>">
            <input type="submit" value="Delete">
        </form>
    </td>
</tr>
<%
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
</table>
</body>
</html>
