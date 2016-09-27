package servleto;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lib.AccountInfoSQL;
import lib.LogInClassSQL;

/**
 * @author Maximka
 * This servlet return page for get money from account. If user try to go on this page 
 * directly, will be forward to login page. After input number of money representation, check
 * with database and if everything right redirect to successfullyget page, if not to wrongget 
 * page. After all depending on input and balance user will see a difference or not. Uses 
 * POST and GET.
 *
 */
public class GetMoneyServlet extends HttpServlet {
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
		String paramAmount = req.getParameter("currentget");
		if (login != null && password != null
				&& logIn.checkLoginPassword(login, password)
				&& paramAmount.length() != 0) {
			BigDecimal amountPut = new BigDecimal(paramAmount);
			if (accoutData.removeMoney(login, amountPut)) {
				req.getRequestDispatcher("/WEB-INF/successfullyget.jsp")
						.forward(req, resp);
				return;
			}

		}

		req.getRequestDispatcher("/WEB-INF/wrongget.jsp").forward(req, resp);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String login = (String) req.getSession().getAttribute("currentLogin");
		String password = (String) req.getSession().getAttribute(
				"currentPassword");
		if (login != null && password != null
				&& logIn.checkLoginPassword(login, password)) {
			req.getRequestDispatcher("/WEB-INF/get.jsp").forward(req, resp);
			return;
		}
		req.getRequestDispatcher("/login.jsp").forward(req, resp);
	}

}
