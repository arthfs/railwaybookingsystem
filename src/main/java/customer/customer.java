package customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Servlet implementation class customer
 */
@WebServlet("/customer")
public class customer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public customer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		PrintWriter out = response.getWriter();
		if (request.getParameter("action").compareTo("registercustomer") == 0)
		{
			System.out.println("register");
			try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
	            String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
	            String root_username = "root"; 
	            String root_password = "Freestyle99+-"; 
	            Connection conn = DriverManager.getConnection(url, root_username, root_password);
	            Statement st = conn.createStatement();
	            String firstname = request.getParameter("firstname"),lastname = request.getParameter("lastname"),birthdate = request.getParameter("birthdate"),username = request.getParameter("username"),
	            		password = request.getParameter("password"),email = request.getParameter("email");
	           boolean disabled = false? true: request.getParameter("disabled").compareTo("yes") == 0;
	    

			 
	           int rs = st.executeUpdate("insert into customer (first_name,last_name,birthdate,disabled,email,username,password) values('"+ firstname +"','" + lastname +"','" +birthdate +"'," + disabled+",'" +email +"','"+ username +"','"+ password+ "')");
	           response.sendRedirect("login.jsp?registrationSuccess=true");
				}
			catch (Exception e) 
		    {
	            out.println("<h3>Error: " + e.getMessage() + "</h3>");
	            e.printStackTrace(out);
	        }
		}
		
		if (request.getParameter("action").compareTo("cancelreservation") == 0)
		{
		if (request.getParameter("reservation_number") != null) {
		    try {
		        String reservationNumber = request.getParameter("reservation_number");
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
		        String root_username = "root"; 
		        String root_password = "Freestyle99+-"; 
		        Connection conn = DriverManager.getConnection(url, root_username, root_password);
		        
		        // Delete the reservation
		        PreparedStatement pst = conn.prepareStatement("DELETE FROM reservation WHERE number = ?");
		        pst.setString(1, reservationNumber);
		        int rowsAffected = pst.executeUpdate();
		        
		        if (rowsAffected > 0) {
		            response.sendRedirect("index.jsp?message=Reservation+cancelled+successfully");
		        } else {
		            response.sendRedirect("index.jsp?error=Failed+to+cancel+reservation");
		        }
		        
		    } catch (Exception e) {
		        response.sendRedirect("index.jsp?error=Error+cancelling+reservation");
		    }
			}
		}
		
		if (request.getParameter("action").compareTo("history") == 0) {
		    Connection conn = null;
		    Statement st = null;
		    ResultSet currentRs = null;
		    ResultSet pastRs = null;
		    
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver"); 
		        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
		        String root_username = "root"; 
		        String root_password = "Freestyle99+-"; 
		        conn = DriverManager.getConnection(url, root_username, root_password);
		        st = conn.createStatement();
		        HttpSession session = request.getSession();
		        String username = session.getAttribute("user").toString();
		        
		        // Get current date for comparison
		        java.util.Date today = new java.util.Date();
		        java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		        
		        // Use PreparedStatement to prevent SQL injection
		        String currentQuery = "SELECT r.username, r.number, r.id_train, r.date, r.total_fare, r.type, r.transitline_name, " +
		                            "s1.name origin, s2.name destination, t1.departure_time, t2.arrival_time " +
		                            "FROM reservation r " +
		                            "JOIN station s1 ON s1.id = r.origin " +
		                            "JOIN station s2 ON s2.id = r.destination " +
		                            "JOIN transitline t1 ON t1.name = r.transitline_name " +
		                            "JOIN transitline t2 ON t2.name = r.transitline_name " +
		                            "WHERE username = ? AND date >= ?";
		        
		        String pastQuery = "SELECT r.username, r.number, r.id_train, r.date, r.total_fare, r.type, r.transitline_name, " +
		                         "s1.name origin, s2.name destination, t1.departure_time, t2.arrival_time " +
		                         "FROM reservation r " +
		                         "JOIN station s1 ON s1.id = r.origin " +
		                         "JOIN station s2 ON s2.id = r.destination " +
		                         "JOIN transitline t1 ON t1.name = r.transitline_name " +
		                         "JOIN transitline t2 ON t2.name = r.transitline_name " +
		                         "WHERE username = ? AND date < ?";
		        
		        // Execute current reservations query
		        PreparedStatement currentPs = conn.prepareStatement(currentQuery);
		        currentPs.setString(1, username);
		        currentPs.setDate(2, sqlToday);
		        currentRs = currentPs.executeQuery();
		        
		        // Execute past reservations query
		        PreparedStatement pastPs = conn.prepareStatement(pastQuery);
		        pastPs.setString(1, username);
		        pastPs.setDate(2, sqlToday);
		        pastRs = pastPs.executeQuery();
		        
		        out.write("<!DOCTYPE html>");
		        out.write("<html>");
		        out.write("<head>");
		        out.write("<title>Reservations</title>");
		        out.write("<link href=\"/railwaysystem/css/style.css\" rel=\"stylesheet\">");
		        out.write("<style>");
		        out.write(".cancel-btn { background-color: #ff4444; color: white; border: none; padding: 5px 10px; border-radius: 3px; cursor: pointer; }");
		        out.write(".cancel-btn:hover { background-color: #cc0000; }");
		        out.write("h2 { margin-top: 20px; }");
		        out.write("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
		        out.write("th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }");
		        out.write("th { background-color: #f2f2f2; }");
		        out.write("</style>");
		        out.write("</head>");
		        out.write("<body>");
		        
		        // Current Reservations Section
		        out.write("<h2>Current Reservations</h2>");
		        out.write("<table>");
		        out.write("<tr>");
		        out.write("<th>Date</th>");
		        out.write("<th>Number</th>");
		        out.write("<th>Train ID</th>");
		        out.write("<th>Transit Line</th>");
		        out.write("<th>Origin</th>");
		        out.write("<th>Destination</th>");
		        out.write("<th>Departure Time</th>");
		        out.write("<th>Arrival Time</th>");
		        out.write("<th>Type</th>");
		        out.write("<th>Total Fare</th>");
		        out.write("<th>Action</th>");
		        out.write("</tr>");
		        
		        boolean hasCurrent = false;
		        while (currentRs.next()) {
		            hasCurrent = true;
		            out.write("<tr>");
		            out.write("<td>" + currentRs.getString("date") + "</td>");
		            out.write("<td>" + currentRs.getString("number") + "</td>");
		            out.write("<td>" + currentRs.getString("id_train") + "</td>");
		            out.write("<td>" + currentRs.getString("transitline_name") + "</td>");
		            out.write("<td>" + currentRs.getString("origin") + "</td>");
		            out.write("<td>" + currentRs.getString("destination") + "</td>");
		            out.write("<td>" + currentRs.getString("departure_time") + "</td>");
		            out.write("<td>" + currentRs.getString("arrival_time") + "</td>");
		            out.write("<td>" + currentRs.getString("type") + "</td>");
		            out.write("<td>$" + currentRs.getString("total_fare") + "</td>");
		            out.write("<td><form method='post' action='customer'>");
		            out.write("<input type = 'hidden' name ='action' value='cancelreservation' >");
		            out.write("<input type='hidden' name='reservation_number' value='" + currentRs.getString("number") + "'>");
		            out.write("<button type='submit' class='cancel-btn'>Cancel</button>");
		            out.write("</form></td>");
		            out.write("</tr>");
		        }
		        
		        if (!hasCurrent) {
		            out.write("<tr><td colspan='11'>No current reservations found</td></tr>");
		        }
		        
		        out.write("</table>");
		        
		        // Past Reservations Section
		        out.write("<h2>Past Reservations</h2>");
		        out.write("<table>");
		        out.write("<tr>");
		        out.write("<th>Date</th>");
		        out.write("<th>Number</th>");
		        out.write("<th>Train ID</th>");
		        out.write("<th>Transit Line</th>");
		        out.write("<th>Origin</th>");
		        out.write("<th>Destination</th>");
		        out.write("<th>Departure Time</th>");
		        out.write("<th>Arrival Time</th>");
		        out.write("<th>Type</th>");
		        out.write("<th>Total Fare</th>");
		        out.write("</tr>");
		        
		        boolean hasPast = false;
		        while (pastRs.next()) {
		            hasPast = true;
		            out.write("<tr>");
		            out.write("<td>" + pastRs.getString("date") + "</td>");
		            out.write("<td>" + pastRs.getString("number") + "</td>");
		            out.write("<td>" + pastRs.getString("id_train") + "</td>");
		            out.write("<td>" + pastRs.getString("transitline_name") + "</td>");
		            out.write("<td>" + pastRs.getString("origin") + "</td>");
		            out.write("<td>" + pastRs.getString("destination") + "</td>");
		            out.write("<td>" + pastRs.getString("departure_time") + "</td>");
		            out.write("<td>" + pastRs.getString("arrival_time") + "</td>");
		            out.write("<td>" + pastRs.getString("type") + "</td>");
		            out.write("<td>$" + pastRs.getString("total_fare") + "</td>");
		            out.write("</tr>");
		        }
		        
		        if (!hasPast) {
		            out.write("<tr><td colspan='10'>No past reservations found</td></tr>");
		        }
		        
		        out.write("</table>");
		        out.write("</body>");
		        out.write("</html>");
		        
		    } catch (Exception e) {
		        out.println("<h3>Error: " + e.getMessage() + "</h3>");
		        e.printStackTrace(out);
		    } finally {
		        // Close resources in reverse order of creation
		        try { if (pastRs != null) pastRs.close(); } catch (Exception e) { e.printStackTrace(); }
		        try { if (currentRs != null) currentRs.close(); } catch (Exception e) { e.printStackTrace(); }
		        try { if (st != null) st.close(); } catch (Exception e) { e.printStackTrace(); }
		        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
		    }
		}
		if (request.getParameter("action").compareTo("makereservation") == 0) make_reservation(request,response);
		if (request.getParameter("action").compareTo("insert") == 0) {
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
		        String root_username = "root"; 
		        String root_password = "Freestyle99+-"; 
		        Connection conn = DriverManager.getConnection(url, root_username, root_password);
		        Statement st = conn.createStatement();
		        HttpSession session = request.getSession();
		        String username = session.getAttribute("user").toString();
		        
		        String idtrain = request.getParameter("train"), 
		               transitline_name = request.getParameter("transitline"),
		               type = request.getParameter("type"),
		               date = request.getParameter("date"),
		               origin = request.getParameter("origin"),
		               destination = request.getParameter("destination"); 
		        
		        Double total_fare = Double.parseDouble(request.getParameter("base_fare"));
		        ArrayList<String> transitlines = new ArrayList<String>();
		        
		        ResultSet rs = st.executeQuery("select * from has_stop h where h.transitline_name = '" + request.getParameter("transitline") + "'");
		        while (rs.next()) transitlines.add(rs.getString("id_station")); 
		        rs.close();
		        Collections.sort(transitlines);
		        
		        int i = transitlines.indexOf(origin), j = transitlines.indexOf(destination);
		        rs = st.executeQuery("select t.fare/ count(h.transitline_name) stop_fare from transitline t join has_stop h on h.transitline_name = t.name group by transitline_name having transitline_name = \"" + transitline_name + "\"");
		        
		        String customerQuery = "SELECT *, TIMESTAMPDIFF(YEAR, birthdate, CURDATE()) AS age FROM customer WHERE username = ?";
		        PreparedStatement pst = conn.prepareStatement(customerQuery);
		        pst.setString(1, username);
		        ResultSet rs1 = pst.executeQuery();
		        
		        String discountMessage = null;
		        double original_fare = total_fare;
		        
		        if (rs.next()) {
		            total_fare += Math.abs(j-i) * rs.getDouble("stop_fare");
		            original_fare = total_fare; // Store before applying discounts
		            
		            if ("roundtrip".equalsIgnoreCase(type)) {
		                total_fare *= 2;
		                original_fare = total_fare;
		            }
		            
		            if (rs1.next()) {
		                int age = rs1.getInt("age");
		                
		                if (rs1.getBoolean("disabled")) {
		                    discountMessage = "50% disability discount applied!";
		                    total_fare *= 0.5;
		                } 
		                else if (age >= 65) {
		                    discountMessage = "35% senior discount applied!";
		                    total_fare *= 0.65;
		                } 
		                else if (age < 12) {
		                    discountMessage = "25% child discount applied!";
		                    total_fare *= 0.75;
		                }
		            }
		            
		            total_fare = Math.round(total_fare * 100.0) / 100.0;
		        }
		        
		        int rt = st.executeUpdate("insert into reservation(id_train,date,total_fare,type,username,transitline_name,origin,destination) "
		               + "values('"+ idtrain + "','"+ date + "',round(" + total_fare + ",3),'" + type + "','" + username + "','" + transitline_name + "','" + origin + "','" + destination + "')");
		        
		        // Show confirmation page with discount notification
		        response.setContentType("text/html");
		       // PrintWriter out = response.getWriter();
		        out.println("<!DOCTYPE html>");
		        out.println("<html><head><title>Reservation Confirmation</title>");
		        out.println("<style>");
		        out.println(".discount-notification {");
		        out.println("    background-color: #4CAF50; color: white;");
		        out.println("    padding: 15px; margin: 20px 0; border-radius: 5px;");
		        out.println("}");
		        out.println("</style></head><body>");
		        out.println("<h1>Reservation Confirmed!</h1>");
		        out.println("<p>Train: " + idtrain + "</p>");
		        out.println("<p>Date: " + date + "</p>");
		        out.println("<p>Route: " + origin + " to " + destination + "</p>");
		        
		        if (discountMessage != null) {
		            out.println("<div class='discount-notification'>");
		            out.println("<h3>" + discountMessage + "</h3>");
		            out.println("<p>Original fare: $" + original_fare + "</p>");
		            out.println("<p>Discounted fare: $" + total_fare + "</p>");
		            out.println("</div>");
		        } else {
		            out.println("<p>Total fare: $" + total_fare + "</p>");
		        }
		        
		        out.println("<a href='index.jsp?action=makereservation'>Make Another Reservation</a>");
		        out.println("</body></html>");
		        
		        conn.close();
		        return; // Important to prevent calling make_reservation again
		        
		    } catch (Exception e) {
		        response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
		        e.printStackTrace(response.getWriter());
		    }
		}}

		
		
	

	public void make_reservation (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
        String root_username = "root"; 
        String root_password = "Freestyle99+-"; 
        HttpSession session = request.getSession();
        //String username = request.getParameter("username") , password = request.getParameter("password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
            Connection conn = DriverManager.getConnection(url, root_username, root_password);
            Statement st = conn.createStatement();
            ResultSet rs =  st.executeQuery("select s1.id originid ,s2.id destinationid, train.id train, t1.fare base_fare,t1.name transitline ,s1.name origin, s2.name destination, t1.departure_time,h.arrival_time,subtime( h.arrival_time , t1.departure_time) travel_time\r\n"
            		+ " from (select t.*,h.id_station from transitline t join has_stop h on h.transitline_name = t.name) t1 join train on t1.name = train.transitline_name\r\n"
            		+ " join has_stop h on t1.name = h.transitline_name join station s1 on t1.id_station = s1.id join station s2 on h.id_station = s2.id where t1.id_station<h.id_station");
            if (rs.next()) {
            	System.out.println("valid");
            }
            else System.out.println("invalid");
            
            out.write("<!DOCTYPE html>");
            out.write("<html>");
            out.write("<head>");
            out.write("<title>Reservations</title>");
            out.write("<link href=\"/railwaysystem/css/customer.css\" rel=\"stylesheet\">");

            out.write("</head>");
            out.write("<body>");

        
            out.write("<table>");
            out.write("<thead><tr>");
            out.write("<th>Train</th>");
            out.write("<th>Transit line</th>");
            out.write("<th>Fare</th>");
            out.write("<th>Departure time</th>");
            out.write("<th>Arrival time</th>");
            out.write("<th>Travel time</th>");
            out.write("<th>Origin</th>");
            out.write("<th>Destination</th>");
            out.write("<th>Date</th>");  
            out.write("<th>Trip Type</th>");  
            out.write("<th>Action</th>");  
            out.write("</tr></thead>");

            out.write("<tbody>");
            while (rs.next()) {
                out.write("<tr>");
                out.write("<td>"+ rs.getString("train") + "</td>");
                out.write("<td>"+ rs.getString("transitline") + "</td>");
                out.write("<td>"+ rs.getString("base_fare") + "</td>");
                out.write("<td>"+ rs.getString("departure_time") + "</td>");
                out.write("<td>"+ rs.getString("arrival_time") + "</td>");
                out.write("<td>"+ rs.getString("travel_time") + "</td>");
                out.write("<td>"+ rs.getString("origin") + "</td>");
                out.write("<td>"+ rs.getString("destination") + "</td>");
                
             
                out.write("<form method='post' action='customer'>");
                
           
                out.write("<input type='hidden' name='train' value='"+rs.getString("train")+"'>");
                out.write("<input type='hidden' name='transitline' value='"+rs.getString("transitline")+"'>");
                out.write("<input type='hidden' name='base_fare' value='"+rs.getString("base_fare")+"'>");
                out.write("<input type='hidden' name='departure_time' value='"+rs.getString("departure_time")+"'>");
                out.write("<input type='hidden' name='arrival_time' value='"+rs.getString("arrival_time")+"'>");
                out.write("<input type='hidden' name='travel_time' value='"+rs.getString("travel_time")+"'>");
                out.write("<input type='hidden' name='origin' value='"+rs.getString("originid")+"'>");
                out.write("<input type='hidden' name='destination' value='"+rs.getString("destinationid")+"'>");
                out.write("<input type='hidden' name='action' value='insert'>");
                
               
                out.write("<td>");
                out.write("<input type='date' name='date' onfocus=\"this.min=new Date().toISOString().split('T')[0]\">");
                out.write("</td>");
                
               
                out.write("<td >");
                out.write("<input type='radio' name='type' value='oneway' id='oneway_"+rs.getString("train")+"'>");
                out.write("<label for='oneway_"+rs.getString("train")+"'>One way</label><br>");
                out.write("<input type='radio' name='type' value='roundtrip' id='roundtrip_"+rs.getString("train")+"'>");
                out.write("<label for='roundtrip_"+rs.getString("train")+"'>Round trip</label>");
                out.write("</td>");
                
                
                out.write("<td>");
                out.write("<input type='submit' value='Reserve'>");
                out.write("</td>");
                
                out.write("</form>");
                out.write("</tr>");
            }
            out.write("</tbody>");
            out.write("</table>");
            out.write("</body>");
            out.write("</html>");
            
        
            conn.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
		
	}
}
