package servleto;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.CreateCapthaData;
import lib.LastUser;
import lib.LogInClassSQL;

/**
 * @author Maximka
 * This servlet return login page with the form to input login and password. Then login and 
 * password are checked in POST method and if everything right, save parameters login and 
 * password and redirect to approved page, if not, forward to the same login page. Uses 
 * POST and GET.
 *
 */
public class DataSent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LogInClassSQL logClass;

	@Override
	public void init() throws ServletException {
		logClass = new LogInClassSQL();
		new CreateCapthaData().createData();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LastUser us = (LastUser) request.getSession().getAttribute("someBean");
		if (us == null) {
			System.out.println("Before return");
			request.getRequestDispatcher("/").forward(request,
					response);
			return;
		}
		System.out.println("aaa = " + request.getParameter("login") + " "
				+ request.getParameter("password"));
		us.addLoginPass(request.getParameter("login"),
				/*request.getParameter("password")*/"");
		if (logClass.checkLoginPassword(request.getParameter("login"),
				request.getParameter("password"))) {

			request.getSession().setAttribute("isLogIn", true);
			request.getSession().setAttribute("currentLogin",
					request.getParameter("login"));
			request.getSession().setAttribute("currentPassword",
					request.getParameter("password"));
			
			
		} else {
			request.setAttribute("Wrong",
					"Wrong login or password");
			request.setAttribute("totalUsers", logClass.getTotalUsers());
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
	getServletContext()
				.getRequestDispatcher("/").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameter("action") != null
				&& req.getParameter("action").equals("logout")) {
			req.getSession().removeAttribute("currentLogin");
			req.getSession().removeAttribute("currentPassword");
			req.getSession().removeAttribute("isLogIn");
			req.getSession().removeAttribute("info");
			req.getSession().invalidate();
		}

		String login = (String) req.getSession().getAttribute("currentLogin");
		String password = (String) req.getSession().getAttribute(
				"currentPassword");
		System.out.println("There ar atr " + login);
		if (login != null && password != null
				&& req.getParameter("action") != null
				&& req.getParameter("action").equals("approved")) {
			req.getRequestDispatcher("/approved").forward(req, resp);
			return;
		}
		if (login != null && password != null
				&&req.getParameter("action") != null
				&& req.getParameter("action").equals("putmoney")) {
			req.getRequestDispatcher("/put").forward(req, resp);
			return;
		}
		if (login != null && password != null
				&&req.getParameter("action") != null
				&& req.getParameter("action").equals("getmoney")) {
			req.getRequestDispatcher("/get").forward(req, resp);
			return;
		}

		if (login != null && password != null
				&& logClass.checkLoginPassword(login, password)) {
			System.out.println("Are you going among the GET?");
			req.getSession().setAttribute("isLogIn", true);
			req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
			return;
		}
		req.setAttribute("totalUsers", logClass.getTotalUsers());
		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}
}
