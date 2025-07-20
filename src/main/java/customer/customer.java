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
		if (request.getParameter("action").compareTo("history") == 0)
		{
			try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
	            String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
	            String root_username = "root"; 
	            String root_password = "Freestyle99+-"; 
	            Connection conn = DriverManager.getConnection(url, root_username, root_password);
	            Statement st = conn.createStatement();
	            HttpSession session = request.getSession();
	            String username = session.getAttribute("user").toString();
	            ResultSet rs = st.executeQuery("select r.username, r.number,r.id_train,r.date, r.total_fare, r.type,r.transitline_name , s1.name origin, s2.name destination,t1.departure_time,t2.arrival_time from reservation r  \r\n"
	            		+ "join station s1 on s1.id = r.origin join station s2 on s2.id = r.destination \r\n"
	            		+ "join transitline t1 on t1.name = r.transitline_name\r\n"
	            		+ "join transitline t2 on t2.name = r.transitline_name  where username = '"+ username +"'");
	            
	            
	            out.write("<!DOCTYPE html>");
	            out.write("<html>");
	            out.write("<head>");
	            out.write("<title>Reservations</title>");
	            out.write("<link href=\"/railwaysystem/css/style.css\" rel=\"stylesheet\">");
	            out.write("</head>");
	            out.write("<body>");
	            out.write("<table> "
	            		+ "<tr>  <th> Username </th> <th> Date </th>  <th> Number </th> <th> id_train </th>  <th> Transit line </th> <th> Origin </th> <th> Destination </th> <th> Departure time </th> <th> Arrival time </th>   <th> Type  </th> <th> Total fare </th>    "
	            		+ "</tr>");
	            
	            while (rs.next())
	            {
	            	out.write(" <tr> "
	            			+ "<td>" + rs.getString("username") + "</td> "
	            			+ "<td>" + rs.getString("date") + "</td> "
	            			+ "<td>" + rs.getString("number") + "</td> "
	            			+ "<td>" + rs.getString("id_train") + "</td> "
	            			+ "<td>" + rs.getString("transitline_name") + "</td> "
	            			+ "<td>" + rs.getString("origin") + "</td> "
	            			+ "<td>" + rs.getString("destination") + "</td> "
	            			+ "<td>" + rs.getString("departure_time") + "</td> "
	            			+ "<td>" + rs.getString("arrival_time") + "</td> "
	            			+ "<td>" + rs.getString("type") + "</td> "
	            			+ "<td>" + rs.getString("total_fare") + "</td> "
	            
	            			+ "</tr>");
	            }
	            
	            out.write("</table>");
	            out.write("</table>");
	            out.write("</body>");
	            out.write("</html>");
	            
	            rs.close();
	            
			  } 
			catch (Exception e) 
			    {
		            out.println("<h3>Error: " + e.getMessage() + "</h3>");
		            e.printStackTrace(out);
		        }
		}
		if (request.getParameter("action").compareTo("makereservation") == 0) make_reservation(request,response);
		if (request.getParameter("action").compareTo("insert") == 0) 
		{
			 try {
		            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
		            String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
		            String root_username = "root"; 
		            String root_password = "Freestyle99+-"; 
		            Connection conn = DriverManager.getConnection(url, root_username, root_password);
		            Statement st = conn.createStatement();
		            HttpSession session = request.getSession();
		            String username = session.getAttribute("user").toString();
		            
		            String idtrain = request.getParameter("train"), transitline_name = request.getParameter("transitline"),type = request.getParameter("type") ,date= request.getParameter("date"),origin = request.getParameter("origin"),destination=request.getParameter("destination"); 
		            Double total_fare = Double.parseDouble( request.getParameter("base_fare"));
		            ArrayList <String> transitlines = new ArrayList<String> ();
		        
		            
		            ResultSet rs = st.executeQuery("select * from has_stop h where h.transitline_name = '" + request.getParameter("transitline") +"'");
		            while (rs.next()) transitlines.add(rs.getString("id_station")); 
		            rs.close();
		            Collections.sort(transitlines);
		            
		            int i = transitlines.indexOf(origin), j = transitlines.indexOf(destination);
		            rs = st.executeQuery("select t.fare/ count(h.transitline_name)  stop_fare from transitline t join has_stop h on h.transitline_name = t.name group by transitline_name having transitline_name = \"bostonline\"\r\n"
		            		+ "  ");
		            
		            if (rs.next() ) 
		            	{
		            	total_fare += Math.abs(j-i) * rs.getDouble("stop_fare");
		            	if (type.compareTo("roundtrip")== 0) total_fare*=2;
		            	}
		            rs.close();
		            
		       
		             int rt = st.executeUpdate ("insert into reservation(id_train,date,total_fare,type,username,transitline_name,origin,destination) "
		            + "values('"+    idtrain+"','"+ date + "',round(" + total_fare + ",3),'" + type +"','" + username +"','" +transitline_name +"','" + origin +"','" + destination   +"')");
		             conn.close();
		             make_reservation(request,response);
		            
		        } catch (Exception e) {
		            out.println("<h3>Error: " + e.getMessage() + "</h3>");
		            e.printStackTrace(out);
		        }
			
			
		}
	}

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
