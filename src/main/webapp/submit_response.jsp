<%@ page import="java.sql.*" %>
<%@ page import="java.io.InputStream, java.util.Properties" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String responseText = request.getParameter("response");
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
