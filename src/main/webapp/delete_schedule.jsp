<%@ page import="java.sql.*" %>
<%
    String name = request.getParameter("name");

    String url = "jdbc:mysql://localhost:3306/railwaybookingsystem";
    String user = "root";
    String password = "Freestyle99+-";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
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
