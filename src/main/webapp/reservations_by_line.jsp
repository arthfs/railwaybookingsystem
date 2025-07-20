<%@ page import="java.sql.*" %>
<%@ page import="java.io.InputStream, java.util.Properties" %>
<html>
<head><title>Customers by Transit Line & Date</title></head>
<body>
<h2>Find Customers with Reservations</h2>

<form method="get" action="reservations_by_line.jsp">
    Transit Line Name: <input type="text" required name="line"><br>
    Date (YYYY-MM-DD): <input type="text" required name="date"><br>
    <input type="submit" value="Search">
</form>

<%
    String line = request.getParameter("line");
    String date = request.getParameter("date");
    Properties props = new Properties();
   	try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
     	    props.load(input);
     	} catch (Exception e) {
     	    e.printStackTrace();
     	}

    if (line != null && date != null) {
    	 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
            	    props.getProperty("db.url"),
            	    props.getProperty("db.user"),
            	    props.getProperty("db.password")
            	);

            String query = "SELECT c.first_name, c.last_name, c.email, r.number " +
                           "FROM reservation r JOIN customer c ON r.username = c.username " +
                           "WHERE r.transitline_name = ? AND r.date = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, line);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();

            out.println("<h3>Results:</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>Name</th><th>Email</th><th>Reservation Number</th></tr>");
            while (rs.next()) {
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                String email = rs.getString("email");
                String resNum = rs.getString("number");

                out.println("<tr><td>" + name + "</td><td>" + email + "</td><td>" + resNum + "</td></tr>");
            }
            out.println("</table>");

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
%>

</body>
</html>
