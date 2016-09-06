package servleto;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.AccountInfoSQL;
import lib.LogInClassSQL;

public class ApprovedServlet extends HttpServlet {
	private AccountInfoSQL accoutData;
	private LogInClassSQL logIn;

	@Override
	public void init() throws ServletException {
		accoutData = new AccountInfoSQL();
		logIn = new LogInClassSQL();
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String login = (String) req.getSession().getAttribute("currentLogin");
		String password = (String) req.getSession().getAttribute(
				"currentPassword");
		if (login != null && password != null
				&& logIn.checkLoginPassword(login, password)) {

			req.getSession().setAttribute("info",
					accoutData.getAccountInfo(login));
			req.getRequestDispatcher("/WEB-INF/Approved.jsp")
					.forward(req, resp);
			return;
		}

		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}
}
