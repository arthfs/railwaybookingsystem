<%@ page import="java.sql.*" %>
<%@ page import="java.io.InputStream, java.util.Properties" %>
<%
    String name = request.getParameter("name");

   

    String origin = "", destination = "", departure = "", arrival = "";
    Properties props = new Properties();
    try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
        props.load(input);
    } catch (Exception e) {
        e.printStackTrace();
    }
       

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
        	    props.getProperty("db.url"),
        	    props.getProperty("db.user"),
        	    props.getProperty("db.password")
        	);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM transitline WHERE name = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            origin = rs.getString("origin");
            destination = rs.getString("destination");
            departure = rs.getString("departure_time");
            arrival = rs.getString("arrival_time");
        }

        rs.close();
        ps.close();
        conn.close();
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>

<html>
<head><title>Edit Schedule</title></head>
<body>
<h2>Edit Schedule for: <%= name %></h2>
<form action="update_schedule.jsp" method="post">
    <input type="hidden" name="name" value="<%= name %>">
    Origin (station ID): <input type="text" name="origin" value="<%= origin %>"><br>
    Destination (station ID): <input type="text" name="destination" value="<%= destination %>"><br>
    Departure Time (HH:MM:SS): <input type="text" name="departure" value="<%= departure %>"><br>
    Arrival Time (HH:MM:SS): <input type="text" name="arrival" value="<%= arrival %>"><br>
    <input type="submit" value="Update Schedule">
</form>
</body>
</html>
