package servleto;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.AccountInfoSQL;
import lib.LogInClassSQL;

public class PutMoneyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountInfoSQL accoutData;
	private LogInClassSQL logIn;

	@Override
	public void init() throws ServletException {
		accoutData = new AccountInfoSQL();
		logIn = new LogInClassSQL();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String login = (String) req.getSession().getAttribute("currentLogin");
		String password = (String) req.getSession().getAttribute(
				"currentPassword");
		String paramAmount = req.getParameter("currentput");
		if (login != null && password != null
				&& logIn.checkLoginPassword(login, password)
				&& paramAmount.length() != 0) {
			BigDecimal amountPut = new BigDecimal(paramAmount);
			if (accoutData.putMoney(login, amountPut)) {
				req.getRequestDispatcher("/WEB-INF/successfullyput.jsp")
						.forward(req, resp);
			}
		} else {
			req.getRequestDispatcher("/WEB-INF/wrongput.jsp")
					.forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String login = (String) req.getSession().getAttribute("currentLogin");
		String password = (String) req.getSession().getAttribute(
				"currentPassword");
		if (login != null && password != null
				&& logIn.checkLoginPassword(login, password)) {
			req.getRequestDispatcher("/WEB-INF/put.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}
}
