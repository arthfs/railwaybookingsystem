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
<<<<<<< HEAD
        String root_password = "Freestyle99+-"; 
=======
        String root_password = ""; 
>>>>>>> 51313782a3728c5270b811165b13f7c36e5867c9
        HttpSession session = request.getSession();
        
		String action = request.getParameter("action");
		if (action.compareTo("executeupdate")==0) 
		{
			String ssn = request.getParameter("ssn"),first_name= request.getParameter("first_name"),last_name= request.getParameter("last_name"),username= request.getParameter("username"),password= request.getParameter("password");
			String ssn_reference = request.getParameter("originalssn");
		
			
			try 
			   {   
<<<<<<< HEAD
				    Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
=======
			   Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
>>>>>>> 51313782a3728c5270b811165b13f7c36e5867c9
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
<<<<<<< HEAD
				    Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
=======
			   Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
>>>>>>> 51313782a3728c5270b811165b13f7c36e5867c9
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
<<<<<<< HEAD
					    Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
=======
				   Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
>>>>>>> 51313782a3728c5270b811165b13f7c36e5867c9
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
		
		if (action.compareTo("get_reservation") == 0) 
		{
			   try {
		            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
		            Connection conn = DriverManager.getConnection(url, root_username, root_password);
		            Statement st = conn.createStatement();
		            ResultSet rs = st.executeQuery("select * from reservation");
		            int num_reservation = 0;
		            
		            out.write("<!DOCTYPE html>");
		            out.write("<html>");
		            out.write("<head>");
		            out.write("<title>Reservations</title>");
		            out.write("<link href=\"/railwaysystem/css/style.css\" rel=\"stylesheet\">");
		            out.write("</head>");
		            out.write("<body>");
		            out.write("<table> "
		            		
								+ "<th> Reservation number </th>"
								+ "<th> Train id </th>"
								+ "<th> Date </th>"
								+ "<th> Total fare </th>"
								+ "<th> Type </th>"
								+ "<th> Username </th>"
								+ "<th> Transitline </th>"
								+ "<th> Origin </th>"
								+ "<th> Destination </th>"
		            		+ "</tr>");
		            while (rs.next()) 
		            {  
		            	out.write("<tr> "
		            			+ "<td>"+ rs.getString("number") +  "</td> "
		            			+ "<td>"+ rs.getString("id_train") +  "</td> "
		            			+ "<td>"+ rs.getString("date") +  "</td> "
		            			+ "<td>"+ rs.getString("total_fare") +  "</td> "
		            			+ "<td>"+ rs.getString("type") +  "</td> "
		            			+ "<td>"+ rs.getString("username") +  "</td> "
		            			+ "<td>"+ rs.getString("transitline_name") +  "</td> "
		            			+ "<td>"+ rs.getString("origin") +  "</td> "
		            			+ "<td>"+ rs.getString("destination") +  "</td> "
		            																					
		            			+ "</tr> ");
		            	num_reservation+=1;
		            }
		            
		       
		            out.write("</table>");
		            out.write("</body>");
		            out.write("</html>");
		            if (num_reservation == 0) 
		            	{	
		            	out.write("<h1>No reservation has been made   </h1>");
		            		
		            	}
		            
		            conn.close();
		        } catch (Exception e) 
			   
			   {
		            out.println("<h3>Error: " + e.getMessage() + "</h3>");
		            e.printStackTrace(out);
		        }
		}
		
		if (action.compareTo("active")==0) 
		{
			
			
	        //String username = request.getParameter("username") , password = request.getParameter("password");
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
	            Connection conn = DriverManager.getConnection(url, root_username, root_password);
	            Statement st = conn.createStatement();
	            ResultSet rs =  st.executeQuery("select transitline_name from (select transitline_name, count(transitline_name) count from reservation group by transitline_name order by count limit 5) t1");
	            int num_transitline = 0;
	           
            	
	              
	            	 while (rs.next()) 
	            	 {	 num_transitline+=1;
	            		 out.write("<p>"+ rs.getString("transitline_name")+  "</p>");
	            	 }
	              
	            
	            if (num_transitline == 0) 
	            	{	out.write("<h1>No transitline has been used yet  </h1>");
	            		
	            	}
	            	
	        
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
