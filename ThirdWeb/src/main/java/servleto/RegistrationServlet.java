package servleto;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.LogInClassSQL;

/**
 * @author Maximka
 * This servlet is used to registration new accounts. First return registration page. Then 
 * after all filled pages will redirect to successfullyregistered page and then to login page
 *  if everything right, if not forward to wrongregistration page and then to the same 
 *  registration page. Uses GET and POST.
 *
 */
public class RegistrationServlet extends HttpServlet {
	private LogInClassSQL dataBaseOfUsers;
	private Random randomgenerator;

	@Override
	public void init() throws ServletException {
		dataBaseOfUsers = new LogInClassSQL();
		randomgenerator = new Random();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (dataBaseOfUsers.addNewUser(req.getParameter("login"),
				req.getParameter("password"),
				req.getParameter("confirmpassword"),(Integer) req.getSession().getAttribute("picturenumber"),
				req.getParameter("permissioncode"))) {
			
			req.getRequestDispatcher("/WEB-INF/successfullyregistered.jsp")
			.forward(req, resp);
		}
		else{req.getRequestDispatcher("/WEB-INF/wrongregistration.jsp")
			.forward(req, resp);}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().removeAttribute("picturenumber");
		req.getSession().setAttribute("picturenumber", randomgenerator.nextInt(6) + 1);
		req.getRequestDispatcher("/registration.jsp").forward(req, resp);
	}

}
