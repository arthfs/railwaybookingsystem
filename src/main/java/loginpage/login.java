package loginpage;


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

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
        
		 //request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

        String url = "jdbc:mysql://localhost:3306/railwaybookingsystem"; 
        String root_username = "root"; 
        String root_password = "Freestyle99+-"; 
        HttpSession session = request.getSession();
        String username = request.getParameter("username") , password = request.getParameter("password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
            Connection conn = DriverManager.getConnection(url, root_username, root_password);
            Statement st = conn.createStatement();
            ResultSet rs;
            rs = st.executeQuery("select * from customer where username='" + username + "' and password='" + password
            + "'");
            if (rs.next()) {
            session.setAttribute("user", username); // the username will be stored in the session
            out.println("welcome " + username);
            out.println("<a href='logout.jsp'>Log out</a>");
            response.sendRedirect("index.jsp");
            } else {
            out.println("Invalid password <a href='login.jsp'>try again</a>");
            }
            conn.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
	}

}
