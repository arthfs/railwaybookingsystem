package manager;

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



import java.sql.PreparedStatement;

/**
 * Servlet implementation class manager
 */
@WebServlet("/manager")
public class manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public manager() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
        String root_username = "root"; 
        String root_password = "Freestyle99+-"; 
        HttpSession session = request.getSession();
        
		String action = request.getParameter("action");
		if (action.compareTo("executeupdate")==0) 
		{
			String ssn = request.getParameter("ssn"),first_name= request.getParameter("first_name"),last_name= request.getParameter("last_name"),username= request.getParameter("username"),password= request.getParameter("password");
			String ssn_reference = request.getParameter("originalssn");
		
			
			try 
			   {   
			   Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
		            Connection conn = DriverManager.getConnection(url, root_username, root_password);
		            Statement st = conn.createStatement();
		            int rs = st.executeUpdate   ("update  customer_service set first_name ='"+ first_name+"', last_name ='"+ last_name+ "', username ='"+ username+ "'," +" password ='"+ password+ "'," +"ssn ='"+ ssn+ "' where ssn='"
				    +ssn_reference+"'"    );
		            
		           // System.out.println("update  customer_service set first_name='"+ first_name+"', last_name='"+ last_name+ "', username='"+ username+ "'," +" password ='"+ password+ "'," +"ssn ='"+ ssn+ "' where ssn='"+ssn_reference+"'"    );
		            response.sendRedirect("manager?action=update");
			   } catch (Exception e) 
			   
			   {
		            out.write("<h1> There is someone who already has this ssn </h1>");
		            response.sendRedirect("manager?action=update");
		        } 
			
		}
	
		if (action.compareTo ("updatecustomer") == 0) 
		{
			String ssn = request.getParameter("ssn"),first_name= request.getParameter("first_name"),last_name= request.getParameter("last_name"),username= request.getParameter("username"),password= request.getParameter("password");
			//System.out.println(first_name);
			out.write("<form method='put' action='manager'> "
					+ "<div> <label for='ssn'> SSN <label/> <input type='hidden' name='originalssn' value='"+ssn+"'> <input name='ssn' required value=" +ssn+"> </div>"
					+ "<div> <label for='first_name'>First name <label/> <input name='first_name' required value=" +first_name+"> </div>"
					+ "<div> <label for='last_name'>Last name <label/> <input name='last_name' required value=" +last_name+"> </div>"
					+ "<div> <label for='username'>Username <label/> <input name='username' required value=" +username+"> </div>"
					+ "<div> <label for='password'>Password <label/> <input name='password' required value=" +password+"> </div>"
					+ "<input type= 'hidden' name='action' value='executeupdate'> <input type='submit' value=Update> </form> "
					+ "<button onclick='window.history.back()'> Cancel </button>");
		}
		
		if (action.compareTo("delete") == 0) {
			//System.out.println("delete");
			 try 
			   {   
			   Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
		            Connection conn = DriverManager.getConnection(url, root_username, root_password);
		            Statement st = conn.createStatement();
		            int rs = st.executeUpdate ("delete from customer_service where ssn='"+request.getParameter("ssn")+  "'");
		            conn.close();
		            response.sendRedirect("manager?action=update");
		            
		        } catch (Exception e) 
			   
			   {
		            out.println("<h3>Error: " + e.getMessage() + "</h3>");
		            e.printStackTrace(out);
		        } 
			
		}
		if (action.compareTo("update") == 0) 
		{
			
				   try 
				   {   
				   Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
			            Connection conn = DriverManager.getConnection(url, root_username, root_password);
			            Statement st = conn.createStatement();
			            ResultSet rs = st.executeQuery("select * from customer_service");
			            int  num_customer_service = 0;
			            
			            out.write("<!DOCTYPE html>");
			            out.write("<html>");
			            out.write("<head>");
			            out.write("<title>Reservations</title>");
			            out.write("<link href=\"/railwaysystem/css/style.css\" rel=\"stylesheet\">");
			            out.write("</head>");
			            out.write("<body>");
			            out.write("<table>  <tr> "
			            		+ "<th> SSN </th> "
			            		+ "<th> First name </th> "
			            		+ "<th> Last name </th> "
			            		+ "<th> Username </th> "
			            		+ "<th> Password </th> "
			            		+ "<th colspan=2> Actions </th> "
			            		+ "</tr>");
					    while (rs.next())  
					    {	
						   num_customer_service+=1;
						   out.write("<form method='put' action='manager'> <tr> "
							+ "<input name='ssn' type='hidden' value='"+rs.getString("ssn") + "'> <td >" + rs.getString("ssn") + "</td>"
							+ "<input name='first_name' type='hidden' value='"+rs.getString("first_name") + "'> <td >" + rs.getString("first_name") + "</td>"
							+ "<input name='last_name' type='hidden' value='"+rs.getString("last_name") + "'> <td >" + rs.getString("last_name") + "</td>"
							+ "<input name='username' type='hidden' value='"+rs.getString("username") + "'> <td >" + rs.getString("username") + "</td>"
							+ "<input name='password' type='hidden' value='"+rs.getString("password") + "'> <td >" + rs.getString("password") + "</td>"
							+ "<td> <button type='submit' name='action' value='updatecustomer'> Update </button> </td> "
							+ "<td> <button type='submit' name='action' value='delete'> Delete  </button></td>"
							
	            			+ "</tr> </form> ");
					    }
					    out.write("</table> ");
					    out.write("</table>");
			            out.write("</body>");
			            out.write("</html>");
	            
	            if (num_customer_service == 0) 
            	{	
	            	out.write("<h1>No customer made a reservation   </h1>");
            		
            	}
	            conn.close();
	            
	            
	        } catch (Exception e) 
		   
		   {
	            out.println("<h3>Error: " + e.getMessage() + "</h3>");
	            e.printStackTrace(out);
	        } 
	  }
		
		if (action.compareTo("highest_revenue") == 0) 
		{
			
				   try 
				   {
			            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
			            Connection conn = DriverManager.getConnection(url, root_username, root_password);
			            Statement st = conn.createStatement();
			            ResultSet rs = st.executeQuery("select c.first_name,c.last_name,t1.* from (select r.username,round(sum(r.total_fare),3) total from reservation r  group by r.username) t1 join customer c using(username) where t1.total = (select max(t1.total))");
			            int num_customer = 0;
			            out.write("<!DOCTYPE html>");
			            out.write("<html>");
			            out.write("<head>");
			            out.write("<title>Reservations</title>");
			            out.write("<link href=\"/railwaysystem/css/style.css\" rel=\"stylesheet\">");
			            out.write("</head>");
			            out.write("<body>");
			            out.write("<table> <tr> "
			            		+ "<th> First Name </th> "
			            		+ "<th> Last Name </th> "
			            		+ "<th> Username </th> "
			            		+ "<th> Total </th> "
			            		+ "</tr>");
			            while (rs.next()) 
			            {	num_customer+=1;
			            	out.write("<tr> "
			            			+ "<td>" + rs.getString("first_name") + "</td>"
			            			+ "<td>" + rs.getString("last_name") + "</td>"
			            			+ "<td>" + rs.getString("username") + "</td>"
			            			+ "<td>" + "$" +rs.getString("total") + "</td>"
			            			+ "</tr> </table>");
			            }
			            out.write("</table>");
			            out.write("</body>");
			            out.write("</html>");
			            
			            if (num_customer == 0) 
		            	{	
			            	out.write("<h1>No customer made a reservation   </h1>");
		            		
		            	}
			            conn.close();
			            
			            
			        } catch (Exception e) 
				   
				   {
			            out.println("<h3>Error: " + e.getMessage() + "</h3>");
			            e.printStackTrace(out);
			        }
		}
		
		if (action.compareTo("get_reservation") == 0) {
				String by = request.getParameter("by");
				String keyword = request.getParameter("keyword");

				try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, root_username, root_password);
						Statement st = conn.createStatement();
						ResultSet rs;

						if (by.equals("customer")) {
								rs = st.executeQuery("SELECT * FROM reservation WHERE username = '" + keyword + "'");
						} else {
								rs = st.executeQuery("SELECT * FROM reservation WHERE transitline_name = '" + keyword + "'");
						}

						out.write("<html><head><title>Reservation List</title></head><body>");
						out.write("<h2>Reservations by " + by + ": " + keyword + "</h2>");
						out.write("<table border='1'>"
										+ "<tr><th>Number</th><th>Train ID</th><th>Date</th><th>Total Fare</th><th>Type</th><th>Username</th><th>Transit Line</th><th>Origin</th><th>Destination</th></tr>");

						boolean hasData = false;
						while (rs.next()) {
								hasData = true;
								out.write("<tr>"
												+ "<td>" + rs.getString("number") + "</td>"
												+ "<td>" + rs.getString("id_train") + "</td>"
												+ "<td>" + rs.getString("date") + "</td>"
												+ "<td>" + rs.getString("total_fare") + "</td>"
												+ "<td>" + rs.getString("type") + "</td>"
												+ "<td>" + rs.getString("username") + "</td>"
												+ "<td>" + rs.getString("transitline_name") + "</td>"
												+ "<td>" + rs.getString("origin") + "</td>"
												+ "<td>" + rs.getString("destination") + "</td>"
												+ "</tr>");
						}
						out.write("</table>");
						if (!hasData) {
								out.write("<p>No reservations found for this " + by + ".</p>");
						}
						out.write("<button onclick='history.back()'>Back</button>");
						out.write("</body></html>");
						conn.close();
				} catch (Exception e) {
						out.println("<h3>Error: " + e.getMessage() + "</h3>");
						e.printStackTrace(out);
				}
		}
		
		if (action.compareTo("active") == 0) {
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection conn = DriverManager.getConnection(url, root_username, root_password);
		        
		   
		        String month = request.getParameter("month");
		        String year = request.getParameter("year");
		        
		        // Build SQL query
		        StringBuilder query = new StringBuilder(
		            "SELECT transitline_name, COUNT(*) as reservation_count " +
		            "FROM reservation ");
		        
		       
		        if ((month != null && !month.isEmpty()) || (year != null && !year.isEmpty())) {
		            query.append("WHERE ");
		            if (month != null && !month.isEmpty()) {
		                query.append("MONTH(date) = ?");
		                if (year != null && !year.isEmpty()) {
		                    query.append(" AND ");
		                }
		            }
		            if (year != null && !year.isEmpty()) {
		                query.append("YEAR(date) = ?");
		            }
		        }
		        
		        query.append(" GROUP BY transitline_name ORDER BY reservation_count DESC LIMIT 5");
		
		        PreparedStatement pst = conn.prepareStatement(query.toString());
		        
		        int paramIndex = 1;
		        if (month != null && !month.isEmpty()) {
		            pst.setString(paramIndex++, month);
		        }
		        if (year != null && !year.isEmpty()) {
		            pst.setString(paramIndex++, year);
		        }
		        
		        ResultSet rs = pst.executeQuery();
		        
		        // Generate report
		        out.write("<html><head><title>Most Active Transit Lines</title></head><body>");
		        out.write("<h2>Top 5 Most Active Transit Lines</h2>");
		        
		        // Show filter info if applied
		        if ((month != null && !month.isEmpty()) || (year != null && !year.isEmpty())) {
		            out.write("<h3>Filter: ");
		            if (year != null && !year.isEmpty()) out.write("Year: " + year + " ");
		            if (month != null && !month.isEmpty()) out.write("Month: " + month);
		            out.write("</h3>");
		        }
		        
		        out.write("<table border='1' style='width:50%'>");
		        out.write("<tr><th>Rank</th><th>Transit Line</th><th>Reservations</th></tr>");
		        
		        int rank = 1;
		        boolean hasResults = false;
		        
		        while (rs.next()) {
		            hasResults = true;
		            out.write("<tr>");
		            out.write("<td>" + rank + "</td>");
		            out.write("<td>" + rs.getString("transitline_name") + "</td>");
		            out.write("<td>" + rs.getInt("reservation_count") + "</td>");
		            out.write("</tr>");
		            rank++;
		        }
		        
		        if (!hasResults) {
		            out.write("<tr><td colspan='3'>No reservations found</td></tr>");
		        }
		        
		        out.write("</table>");
		        out.write("<button onclick='history.back()'>Back</button>");
		        out.write("</body></html>");
		        
		        conn.close();
		    } catch (Exception e) {
		        out.println("<h3>Error: " + e.getMessage() + "</h3>");
		        e.printStackTrace(out);
		    }
		
		}
		
		// Monthly Sales Report
		if (action.compareTo("sales") == 0) {
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection conn = DriverManager.getConnection(url, root_username, root_password);
		        
		    
		        String year = request.getParameter("year");
		        String month = request.getParameter("month");
		        
		        StringBuilder query = new StringBuilder(
		            "SELECT DATE_FORMAT(date, '%Y-%m') AS month, ROUND(SUM(total_fare), 2) AS total_sales " +
		            "FROM reservation ");
		        
		     
		        if ((year != null && !year.isEmpty()) || (month != null && !month.isEmpty())) {
		            query.append("WHERE ");
		            
		            if (year != null && !year.isEmpty()) {
		                query.append("YEAR(date) = ? ");
		                if (month != null && !month.isEmpty()) {
		                    query.append("AND MONTH(date) = ? ");
		                }
		            } else {
		                query.append("MONTH(date) = ? ");
		            }
		        }
		        
		        query.append("GROUP BY month ORDER BY month DESC");
		        
		        PreparedStatement pst = conn.prepareStatement(query.toString());
		        
		        // Set parameters
		        int paramIndex = 1;
		        if (year != null && !year.isEmpty()) {
		            pst.setInt(paramIndex++, Integer.parseInt(year));
		        }
		        if (month != null && !month.isEmpty()) {
		            pst.setInt(paramIndex++, Integer.parseInt(month));
		        }
		        
		        out.write("<html><head><title>Monthly Sales Report</title></head><body>");
		        out.write("<h2>Monthly Sales Report</h2>");
		        
		       
		        if ((year != null && !year.isEmpty()) || (month != null && !month.isEmpty())) {
		           
		            if (year != null && !year.isEmpty()) out.write("Year: " + year + " ");
		            if (month != null && !month.isEmpty()) out.write("Month: " + month);
		            out.write("</h3>");
		        }
		        
		        out.write("<table border='1'><tr><th>Month</th><th>Total Sales ($)</th></tr>");
		        
		        ResultSet rs = pst.executeQuery();
		        boolean hasRows = false;
		        double grandTotal = 0;
		        
		        while (rs.next()) {
		            hasRows = true;
		            String monthData = rs.getString("month");
		            double totalSales = rs.getDouble("total_sales");
		            grandTotal += totalSales;
		            
		            out.write("<tr><td>" + monthData + "</td><td>" + String.format("%.2f", totalSales) + "</td></tr>");
		        }
		        
		        if (!hasRows) {
		            out.write("<tr><td colspan='2'>No reservations found</td></tr>");
		        } 
		        
		        out.write("</table>");
		        out.write("<button onclick='history.back()'>Back</button>");
		        out.write("</body></html>");
		        
		        conn.close();
		    } catch (Exception e) {
		        out.println("<h3>Error: " + e.getMessage() + "</h3>");
		        e.printStackTrace(out);
		    }
		}
		// Total Revenue by Transit Line or Customer
		if (action.compareTo("get_revenue") == 0) {
				String by = request.getParameter("by");
				String keyword = request.getParameter("keyword");

				try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, root_username, root_password);
						Statement st = conn.createStatement();
						ResultSet rs;

						if (by.equals("customer")) {
								rs = st.executeQuery("SELECT username, ROUND(SUM(total_fare), 2) AS total FROM reservation WHERE username = '" + keyword + "' GROUP BY username");
						} else {
								rs = st.executeQuery("SELECT transitline_name, ROUND(SUM(total_fare), 2) AS total FROM reservation WHERE transitline_name = '" + keyword + "' GROUP BY transitline_name");
						}

						out.write("<html><head><title>Total Revenue</title></head><body>");
						out.write("<h2>Total Revenue by " + by + ": " + keyword + "</h2>");

						if (rs.next()) {
								out.write("<p>Total Revenue: $" + rs.getString("total") + "</p>");
						} else {
								out.write("<p>No revenue data found for this " + by + ".</p>");
						}

						out.write("<button onclick='history.back()'>Back</button>");
						out.write("</body></html>");
						conn.close();

				} catch (Exception e) {
						out.println("<h3>Error: " + e.getMessage() + "</h3>");
						e.printStackTrace(out);
				}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
