<%@ page import="java.sql.*" %>
<%@ page import="java.io.InputStream, java.util.Properties" %>
<html>
<head><title>Reply to Customer Questions</title></head>
<body>
<h2>Customer Questions</h2>

<!-- Search Form -->
<form method="get" action="">
    Search by keywords: 
    <input type="text" name="keywords" value="<%= request.getParameter("keywords") != null ? request.getParameter("keywords") : "" %>">
    <input type="submit" value="Search">
    <% if (request.getParameter("keywords") != null && !request.getParameter("keywords").isEmpty()) { %>
        <a href="<%= request.getRequestURI() %>">Clear search</a>
    <% } %>
</form>

<table border="1">
<tr>
    <th>ID</th>
    <th>Customer</th>
    <th>Question</th>
    <th>Response</th>
</tr>

<%
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
        Statement stmt = conn.createStatement();
        
        String keywords = request.getParameter("keywords");
        String query;
        
        if (keywords != null && !keywords.trim().isEmpty()) {
            // Build a search query that looks for any of the keywords in the question
            String[] keywordArray = keywords.split("\\s+");
            StringBuilder whereClause = new StringBuilder();
            
            for (int i = 0; i < keywordArray.length; i++) {
                if (i > 0) {
                    whereClause.append(" OR ");
                }
                whereClause.append("question LIKE '%").append(keywordArray[i]).append("%'");
            }
            
            query = "SELECT * FROM questions WHERE " + whereClause.toString() + " ORDER BY id DESC";
        } else {
            query = "SELECT * FROM questions ORDER BY id DESC";
        }
        
        ResultSet rs = stmt.executeQuery(query);

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