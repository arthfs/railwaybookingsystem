<%@ page import="java.sql.*" %>
<html>
<head><title>Reply to Customer Questions</title></head>
<body>
<h2>Customer Questions</h2>
<table border="1">
<tr>
    <th>ID</th>
    <th>Customer</th>
    <th>Question</th>
    <th>Response</th>
</tr>

<%
    String url = "jdbc:mysql://localhost:3306/railwaybookingsystem";
    String user = "root";
    String password = "Freestyle99+-";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

        while(rs.next()) {
            int id = rs.getInt("id");
            String customer = rs.getString("customer");
            String question = rs.getString("question");
            String responseText = rs.getString("answer");
%>
<tr>
    <td><%= id %></td>
    <td><%= customer %></td>
    <td><%= question %></td>
    <td>
        <% if (responseText == null || responseText.trim().isEmpty()) { %>
            <form action="submit_response.jsp" method="post">
                <input type="hidden" name="id" value="<%= id %>">
                <textarea name="response" rows="3" cols="40"></textarea><br>
                <input type="submit" value="Submit Response">
            </form>
        <% } else { %>
            <%= responseText %>
        <% } %>
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
