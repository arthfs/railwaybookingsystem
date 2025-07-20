<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Train Schedules</title>
</head>
<body>
<h2>Search for Train Schedules</h2>

<form method="get" action="search_schedules.jsp">
    <label for="origin">Origin Station ID or Name:</label>
    <input type="text" name="origin" required><br><br>

    <label for="destination">Destination Station ID or Name:</label>
    <input type="text" name="destination" required><br><br>

    <label for="travel_date">Date of Travel:</label>
    <input type="date" name="travel_date" required><br><br>

    <label for="sort_by">Sort By:</label>
    <select name="sort_by">
        <option value="">None</option>
        <option value="arrival_time">Arrival Time</option>
        <option value="departure_time">Departure Time</option>
        <option value="fare">Fare</option>
    </select><br><br>

    <input type="submit" value="Search">
</form>

<%
    String origin = request.getParameter("origin");
    String destination = request.getParameter("destination");
    String travelDate = request.getParameter("travel_date");
    String sortBy = request.getParameter("sort_by");
    String viewStops = request.getParameter("view_stops");

    if (origin != null && destination != null && travelDate != null) {
        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem";
        String dbUser = "root";
        String dbPass = "Freestyle99+-";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);

            String stationSql = "SELECT id FROM station WHERE id = ? OR name = ?";
            PreparedStatement psOrigin = conn.prepareStatement(stationSql);
            psOrigin.setString(1, origin);
            psOrigin.setString(2, origin);
            ResultSet rsOrigin = psOrigin.executeQuery();

            PreparedStatement psDest = conn.prepareStatement(stationSql);
            psDest.setString(1, destination);
            psDest.setString(2, destination);
            ResultSet rsDest = psDest.executeQuery();

            if (rsOrigin.next() && rsDest.next()) {
                String originId = rsOrigin.getString("id");
                String destinationId = rsDest.getString("id");

                String query = "SELECT * FROM transitline WHERE origin = ? AND destination = ?";
                if (sortBy != null && !sortBy.isEmpty()) {
                    if (sortBy.equals("arrival_time")) query += " ORDER BY arrival_time ASC";
                    else if (sortBy.equals("departure_time")) query += " ORDER BY departure_time ASC";
                    else if (sortBy.equals("fare")) query += " ORDER BY fare ASC";
                }

                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, originId);
                ps.setString(2, destinationId);
                ResultSet rs = ps.executeQuery();

                out.println("<h3>Results for travel on " + travelDate + ":</h3>");
                out.println("<table border='1'>");
                out.println("<tr><th>Transit Line</th><th>Departure</th><th>Arrival</th><th>Fare</th></tr>");
                while (rs.next()) {
                    String lineName = rs.getString("name");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(lineName + " ");
                    out.println("<form style='display:inline;' method='get' action='search_schedules.jsp'>");
                    out.println("<input type='hidden' name='origin' value='" + origin + "'>");
                    out.println("<input type='hidden' name='destination' value='" + destination + "'>");
                    out.println("<input type='hidden' name='travel_date' value='" + travelDate + "'>");
                    out.println("<input type='hidden' name='sort_by' value='" + sortBy + "'>");
                    out.println("<input type='hidden' name='view_stops' value='" + lineName + "'>");
                    out.println("<input type='submit' value='View Stops'>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("<td>" + rs.getString("departure_time") + "</td>");
                    out.println("<td>" + rs.getString("arrival_time") + "</td>");
                    out.println("<td>$" + rs.getFloat("fare") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");

                rs.close();
                ps.close();
            } else {
                out.println("<p>No matching station(s) found for your input.</p>");
            }

            rsOrigin.close();
            rsDest.close();
            psOrigin.close();
            psDest.close();
            conn.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }

    if (viewStops != null) {
        out.println("<h3>Stops for line: " + viewStops + "</h3>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railwaybookingsystem", "root", "Freestyle99+-");

            String stopQuery = "SELECT s.name, h.arrival_time, h.departure_time FROM has_stop h JOIN station s ON h.id_station = s.id WHERE h.transitline_name = ? ORDER BY h.arrival_time";
            PreparedStatement ps = conn.prepareStatement(stopQuery);
            ps.setString(1, viewStops);
            ResultSet rs = ps.executeQuery();

            out.println("<table border='1'><tr><th>Station</th><th>Arrival</th><th>Departure</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("arrival_time") + "</td>");
                out.println("<td>" + rs.getString("departure_time") + "</td></tr>");
            }
            out.println("</table>");

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            out.println("<p>Error fetching stops: " + e.getMessage() + "</p>");
        }
    }
%>

</body>
</html>
