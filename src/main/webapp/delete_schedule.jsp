<%@ page import="java.sql.*" %>
<%@ page import="java.io.InputStream, java.util.Properties" %>
<%
    String name = request.getParameter("name");

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
        Statement st = conn.createStatement();
    
        PreparedStatement ps = conn.prepareStatement("DELETE FROM transitline WHERE name = ?");
        ps.setString(1, name);
        ps.executeUpdate();

        ps.close();
        conn.close();

        response.sendRedirect("manage_schedules.jsp");
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
