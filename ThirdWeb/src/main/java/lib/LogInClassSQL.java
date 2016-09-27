package lib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * @author Maximka
 * LogInClassSQL - class which is responsible for validation of user input during log in or
 * register a new account and getting total count of accounts which already registered 
 * on the server. Has connection with database, so every data is revalidate immediately after
 * any transactions.
 *
 */
public class LogInClassSQL {
	private SessionFactory sf = HibernateUtil.getSessionFactory();

	/**
	 * Check for login and password, when user try to log in on the site. 
	 * @param login inputed login 
	 * @param password inputed password
	 * @return true if login is exist ignore case and password is equals to password from 
	 * database, false otherwise
	 */
	public boolean checkLoginPassword(String login, String password) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		UserAccount user = (UserAccount) s.createCriteria(UserAccount.class)
		.add(Restrictions.like("login", login.trim())).uniqueResult();
		trns.commit();
		if(user!=null){
			
			return user.getPassword().equals(password);
		}
		
		return false;
	}


	/**
	 * Check if login is free and no one is already registered with the same login
	 * @param login inputed login for registration
	 * @return true if login is free for registration no one row from database, false 
	 * otherwise
	 */
	private boolean isFreeLogin(String login) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		String loginExist = (String) s.createQuery("select login from UserAccount "
				+ "where login like :login")
				.setParameter("login", login).uniqueResult();
		trns.commit();
		if(loginExist==null){
			return true;
		}
		return false;

	}

	/**
	 * Get information from the user input field of captcha and number of picture which was
	 *  randomly generated by servlet sent to page and after all gotten here
	 * @param number number of captcha picture from the page
	 * @param capcha text of captcha, inputted by user
	 * @return true if inputed text is equal to captcha text, false otherwise
	 * 
	 */
	private boolean isCorrectCapcha(int number, String capcha) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		String trueCapcha = (String) s.createQuery("select textPhoto from Captcha "
				+ "where photoNumber=:number")
				.setParameter("number", number).uniqueResult();
		trns.commit();
		if(trueCapcha!=null){
			return trueCapcha.equals(capcha);
		}
		return false;
	}

	/**
	 * Add new account if all check will complete
	 * @param login inputed login supposed as new 
	 * @param password inputed password as new
	 * @param confirmPassword confirm previous password
	 * @param numberOfPicture name of picture from page which displays actual captcha and 
	 * which used to get captcha text
	 * @param capcha inputed text of captcha 
	 * @return true if all criteria are: login is free, password and confirm password are the 
	 * same, text on captcha according to inputed text that is compared with text from 
	 * database. This text gotten using name of the picture as primary key.
	 * 
	 */
	public boolean addNewUser(String login, String password,
			String confirmPassword, int numberOfPicture, String capcha) {
		if (isFreeLogin(login) && password.equals(confirmPassword) 
				&& isCorrectCapcha(numberOfPicture, capcha)) {
		Transaction trns =null;
		Session s = sf.openSession();
		trns= s.beginTransaction();
		UserAccount userAccount = new UserAccount();
		userAccount.setLogin(login);
		userAccount.setPassword(password);
		s.save(userAccount);
		trns.commit();
		trns= s.beginTransaction();
		Integer count =(Integer) s.createCriteria(CountUser.class)
				.setProjection(Projections.max("count")).uniqueResult();
		trns.commit();
		CountUser cu = new CountUser();
		if(count!=null){
			cu.setCount(++count);
			}
		else{ cu.setCount(1);}
		trns= s.beginTransaction();
		s.save(cu);
		trns.commit();
		s.close();
		return true;
		}
		return false;
	}

	/**
	 * Get total users which already registered
	 * @return an integer of total users from database, 0 if no user is registered
	 */
	public int getTotalUsers() {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		Integer total = (Integer) s.createCriteria(CountUser.class)
				.setProjection(Projections.max("count")).uniqueResult();
		trns.commit();
		if(total!=null){
			return total;
		}
		return 0;
	}


}