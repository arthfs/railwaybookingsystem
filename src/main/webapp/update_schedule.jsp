<%@ page import="java.sql.*" %>
<%@ page import="java.io.InputStream, java.util.Properties" %>
<%
    String name = request.getParameter("name");
    String origin = request.getParameter("origin");
    String destination = request.getParameter("destination");
    String departure = request.getParameter("departure");
    String arrival = request.getParameter("arrival");
    
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

        PreparedStatement ps = conn.prepareStatement(
            "UPDATE transitline SET origin = ?, destination = ?, departure_time = ?, arrival_time = ? WHERE name = ?"
        );
        ps.setString(1, origin);
        ps.setString(2, destination);
        ps.setString(3, departure);
        ps.setString(4, arrival);
        ps.setString(5, name);
        ps.executeUpdate();

        ps.close();
        conn.close();

        response.sendRedirect("manage_schedules.jsp");
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
