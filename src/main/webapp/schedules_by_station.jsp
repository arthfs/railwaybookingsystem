<%@ page import="java.sql.*" %>
<html>
<head><title>Schedules by Station</title></head>
<body>
<h2>Find Schedules by Station</h2>

<form method="get" action="schedules_by_station.jsp">
    Station Name or ID: <input type="text" name="station"><br>
    <input type="submit" value="Search">
</form>

<%
    String input = request.getParameter("station");

    if (input != null && !input.trim().isEmpty()) {
        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem";
        String user = "root";
        String password = "Freestyle99+-";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            // Use station name or ID to get valid station IDs
            String stationSQL = "SELECT id FROM station WHERE id = ? OR name = ?";
            PreparedStatement stStmt = conn.prepareStatement(stationSQL);
            stStmt.setString(1, input);
            stStmt.setString(2, input);
            ResultSet stRs = stStmt.executeQuery();

            boolean found = false;
            while (stRs.next()) {
                found = true;
                String stationId = stRs.getString("id");

                out.println("<h3>Schedules where station is ORIGIN:</h3>");
                PreparedStatement originStmt = conn.prepareStatement(
                    "SELECT * FROM transitline WHERE origin = ?");
                originStmt.setString(1, stationId);
                ResultSet rsOrigin = originStmt.executeQuery();

                out.println("<table border='1'><tr><th>Name</th><th>Destination</th><th>Departure</th><th>Arrival</th></tr>");
                while (rsOrigin.next()) {
                    out.println("<tr><td>" + rsOrigin.getString("name") + "</td>");
                    out.println("<td>" + rsOrigin.getString("destination") + "</td>");
                    out.println("<td>" + rsOrigin.getString("departure_time") + "</td>");
                    out.println("<td>" + rsOrigin.getString("arrival_time") + "</td></tr>");
                }
                out.println("</table>");

                out.println("<h3>Schedules where station is DESTINATION:</h3>");
                PreparedStatement destStmt = conn.prepareStatement(
                    "SELECT * FROM transitline WHERE destination = ?");
                destStmt.setString(1, stationId);
                ResultSet rsDest = destStmt.executeQuery();

                out.println("<table border='1'><tr><th>Name</th><th>Origin</th><th>Departure</th><th>Arrival</th></tr>");
                while (rsDest.next()) {
                    out.println("<tr><td>" + rsDest.getString("name") + "</td>");
                    out.println("<td>" + rsDest.getString("origin") + "</td>");
                    out.println("<td>" + rsDest.getString("departure_time") + "</td>");
                    out.println("<td>" + rsDest.getString("arrival_time") + "</td></tr>");
                }
                out.println("</table>");

                rsOrigin.close();
                rsDest.close();
                originStmt.close();
                destStmt.close();
            }

            if (!found) {
                out.println("<p>No station found matching: " + input + "</p>");
            }

            stRs.close();
            stStmt.close();
            conn.close();

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
%>
</body>
</html>
