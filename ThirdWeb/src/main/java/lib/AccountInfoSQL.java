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


/**
 * @author Maximka
 * AccountInfoSQL - class which is responsible to get data about account balance, all 
 * transactions of user, data of it's act, amount of money transaction and ability to put and 
 * get money from the account. 
 *
 */
public class AccountInfoSQL {
	
	private SessionFactory sf = HibernateUtil.getSessionFactory();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	public AccountInfoSQL() {
	}

	/**
	 * Get all transactions from the current account in form date of transaction, amount of 
	 * money transaction and balance
	 * @param login which data needed to get
	 * @return list of transaction or null if no transaction is exists in database
	 */
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
		
		return list;
	}

	/**
	 * Put money on current account and check for the right input of number.
	 * @param login to which will be put money
	 * @param amount of money which will be added to current balance. If balance is zero, 
	 * amount of money will be equal to balance
	 * @return true if amount is more than 0, false if not.
	 */
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
		return false;
	}

	/**
	 * Filling Account entity to save
	 * @param login of account
	 * @param amount which is put or get
	 * @param finalBalance new balance depend on put money or get money
	 * @return Account entity
	 */
	private Account fillAccount(String login, BigDecimal amount, BigDecimal finalBalance) {
		Account ac = new Account();
		ac.setLoginAccount(login);
		ac.setAmount(amount);
		ac.setBalance(finalBalance);
		ac.setDate(sdf.format(new Date()));
		return ac;
	}

	/**
	 * Removing money from account with check of previous balance and that amount to get will
	 * be less than balance.
	 * @param login of account
	 * @param amount of money which will be gotten from account
	 * @return true if balance is exist and not 0 and amount less than balance, false
	 * otherwise
	 */
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
        trns.rollback();
		return false;
	}
}
