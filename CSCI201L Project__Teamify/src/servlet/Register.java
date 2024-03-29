package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServ
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
		String next = "/Index.jsp";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		if(!password.equals(password2)) {
			out.print("The passwords do not match.");  
			request.getRequestDispatcher("Register.jsp").include(request, response); 
		}
		String email = request.getParameter("email");
		String type = request.getParameter("exampleRadios");
		if(type.equals("option1")) {
			type = "Organizer";
		}
		else {
			type = "Contributor";
		}
		//String description = request.getParameter("description");
		String description = "default";
		System.out.println(username);
		System.out.println(password);
		System.out.println(password2);
		System.out.println(email);
		System.out.println(type);
		Connector con = (Connector)request.getSession().getAttribute("Connector");
		if(con == null) {
			String Credential_String = "jdbc:mysql://google/HW3?cloudSqlInstance=white-inscriber-255423:us-central1:sql-db-1&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=scarlett&password=password";
			con = new Connector(Credential_String);
			request.getSession().setAttribute("Connector", con);
		}
		System.out.println(con);
		String resp = con.createUser(username, password, email, type, description);
		PrintWriter pw = response.getWriter();
		if(!resp.contentEquals("Success")) {
			pw.print(resp);
			next = "/Register.jsp";
		}
		getServletContext().getRequestDispatcher(next).forward(request, response);
	}
}
