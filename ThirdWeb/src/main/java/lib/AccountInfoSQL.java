package lib;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class AccountInfoSQL {
	/**
	 * A container that contains login of user, the date of the transaction in
	 * string format, amount of the transaction and current balance
	 */
	private SessionFactory sf = HibernateUtil.getSessionFactory();
	//private Connection conn = ConnectionToMYSQL.getInstance().getConnection();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	public AccountInfoSQL() {
	}

	@SuppressWarnings("unchecked")
	public List<Account> getAccountInfo(
			String login) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
	    List<Account> list = (List<Account>) s.createCriteria(Account.class)
	    		.add(Restrictions.eq("loginAccount", login))
	    		.addOrder(Order.desc("idAccount"))
	    		.list();
	    trns.commit();
		/*PreparedStatement ps = null;
		TripleContainer<String, BigDecimal, BigDecimal> acc = null;
		try {
			ps = conn.prepareStatement("SELECT date,amount,balance "
					+ "FROM bank_storage.accounts" + " WHERE login_account=? "
							+ "ORDER BY id_account DESC");
			ps.setString(1, login);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			acc = new TripleContainer<String, BigDecimal, BigDecimal>();
			while (rs.next()) {
				String date = rs.getString("date");
				BigDecimal amount = rs.getBigDecimal("amount");
				BigDecimal balance = rs.getBigDecimal("balance");
				acc.add(new TripleWrite<String, BigDecimal, BigDecimal>(date,
						amount, balance));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (acc!=null&&acc.size() != 0) {
			return acc;
		}*/
		/*
		 * System.out.println(" login for getting info " + login); if (login !=
		 * null && storage.containsKey(login)) { return storage.get(login); }
		 * System.out.println("This login is nothing return ");
		 */
		return list;
	}

	public boolean putMoney(String login, BigDecimal amount) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		BigDecimal balance  =(BigDecimal) s.createQuery("select balance from Account "
				+ "where loginAccount=:login ORDER BY idAccount DESC")
				.setMaxResults(1)
				.setParameter("login", login).uniqueResult();
		if(balance!=null&&amount.doubleValue() > 0){
			BigDecimal finalBalance = balance.add(amount);
			Account ac = fillAccount(login, amount, finalBalance);
			s.save(ac);
			trns.commit();
			return true;
		}
		else if (amount.doubleValue() > 0) {
			Account ac = fillAccount(login, amount, amount);
			s.save(ac);
			trns.commit();
			return true;
		}
		trns.rollback();
		/*PreparedStatement ps = null;
		PreparedStatement psAnother = null;
		try {
			ps = conn.prepareStatement("SELECT balance "
					+ "FROM bank_storage.accounts WHERE login_account=? "
					+ "ORDER BY DESC LIMIT 1");
			ps.setString(1, login);
			ps.setString(2, login);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();

			if (rs.next() && amount.doubleValue() > 0) {
				BigDecimal balanceDec = rs.getBigDecimal("balance");
				BigDecimal finalBalance = balanceDec.add(amount);

				psAnother = conn
						.prepareStatement("INSERT INTO bank_storage.accounts"
								+ "(login_account,date,amount,balance) "
								+ "VALUES (?,?,?,?)");
				psAnother.setString(1, login);
				psAnother.setString(2, sdf.format(new Date()));
				psAnother.setBigDecimal(3, amount);
				psAnother.setBigDecimal(4, finalBalance);
				psAnother.executeUpdate();
				return true;
			} else if (amount.doubleValue() > 0) {
				psAnother = conn
						.prepareStatement("INSERT INTO bank_storage.accounts"
								+ " (login_account,date,amount,balance)"
								+ " values (?,?,?,?) ");
				psAnother.setString(1, login);
				psAnother.setString(2, sdf.format(new Date()));
				psAnother.setBigDecimal(3, amount);
				psAnother.setBigDecimal(4, amount);
				psAnother.executeUpdate();
				return true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
*/
		return false;
	}

	private Account fillAccount(String login, BigDecimal amount, BigDecimal finalBalance) {
		Account ac = new Account();
		ac.setLoginAccount(login);
		ac.setAmount(amount);
		ac.setBalance(finalBalance);
		ac.setDate(sdf.format(new Date()));
		return ac;
	}

	public boolean removeMoney(String login, BigDecimal amount) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		BigDecimal balance  =(BigDecimal) s.createQuery("select balance from Account "
				+ "where loginAccount=:login order by idAccount DESC")
				.setMaxResults(1)
				.setParameter("login", login).uniqueResult();
		if(balance!=null){
			if (amount.doubleValue() > balance.doubleValue()) {
				trns.rollback();
				return false;
			}
			
			else if (balance.doubleValue() > amount.doubleValue()
					|| balance.doubleValue() == amount.doubleValue()) {
				Account ac = fillAccount(login,amount.negate() , balance.subtract(amount));
				s.save(ac);
				trns.commit();
				return true;
			}
		}
		/*PreparedStatement ps = null;
		PreparedStatement psAnother = null;
		try {
			ps = conn.prepareStatement("SELECT balance "
					+ "FROM bank_storage.accounts" + " WHERE login_account=?"
					+ "&&id_account=(SELECT MAX(id_account) "
					+ "FROM bank_storage.accounts WHERE login_account=?)");
			ps.setString(1, login);
			ps.setString(2, login);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				BigDecimal balance = rs.getBigDecimal("balance");
				if (amount.doubleValue() > balance.doubleValue()) {
					return false;
				} else if (balance.doubleValue() > amount.doubleValue()
						|| balance.doubleValue() == amount.doubleValue()) {
					
					psAnother = conn
							.prepareStatement("INSERT INTO bank_storage.accounts"
									+ "(login_account,date,amount,balance) "
									+ "VALUES (?,?,?,?)");
					psAnother.setString(1, login);
					psAnother.setString(2, sdf.format(new Date()));
					psAnother.setBigDecimal(3, amount.negate());
					psAnother.setBigDecimal(4, balance.subtract(amount));
					psAnother.executeUpdate();
					return true;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}*/
        trns.rollback();
		return false;
	}
}
