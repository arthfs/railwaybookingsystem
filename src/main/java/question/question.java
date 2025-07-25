package question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class question
 */
@WebServlet("/question")
public class question extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public question() {
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
		Properties props = new Properties();
		try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
		    props.load(input);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		
        HttpSession session = request.getSession();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
            Connection conn = DriverManager.getConnection(
            	    props.getProperty("db.url"),
            	    props.getProperty("db.user"),
            	    props.getProperty("db.password")
            	);
            Statement st = conn.createStatement();
            ResultSet rs;
            String username = session.getAttribute("user").toString();
            System.out.println(username);
            String question = request.getParameter("question");

            
            String sql = "INSERT INTO questions (question, customer) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, question);
            pst.setString(2, username);
            int rowsInserted = pst.executeUpdate();
            
            
            response.sendRedirect("index.jsp");
            conn.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
	}

}
