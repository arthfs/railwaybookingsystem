<%@ page import="java.sql.*" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String responseText = request.getParameter("response");

    String url = "jdbc:mysql://localhost:3306/railwaybookingsystem";
    String user = "root";
    String password = "Freestyle99+-";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);

        PreparedStatement ps = conn.prepareStatement("UPDATE questions SET answer = ? WHERE id = ?");
        ps.setString(1, responseText);
        ps.setInt(2, id);
        ps.executeUpdate();

        ps.close();
        conn.close();

        response.sendRedirect("reply_questions.jsp");
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    }
%>
