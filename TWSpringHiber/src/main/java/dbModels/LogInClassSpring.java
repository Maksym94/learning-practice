package dbModels;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maximka
 * LogInClassSpring - class which is responsible for validation of user input during log in or
 * register a new account and getting total count of accounts which already registered 
 * on the server. Has connection with database, so every data is revalidate immediately after
 * any transactions.
 *
 */
public class LogInClassSpring implements LogInClass{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	private HibernateTemplate template;
	public LogInClassSpring(HibernateTemplate template) {
		this.template = template;
	}

	/**
	 * Check for login and password, when user try to log in on the site. 
	 * @param login inputed login 
	 * @param password inputed password
	 * @return true if login is exist ignore case and password is equals to password from 
	 * database, false otherwise
	 */
	@Override
	public boolean checkLoginPassword(String login, String password) {
		
	@SuppressWarnings("unchecked")
	List <UserAccount> currentUser = (List<UserAccount>) template.findByCriteria(DetachedCriteria
			.forClass(UserAccount.class)
			.add(Restrictions.like("login", login.trim())));
		if(!currentUser.isEmpty()){
			UserAccount user=currentUser.get(0);
			return passwordEncoder.matches(password, user.getPassword());
		}
		return false;
	}
	
	/**
	 * Check if login is free and no one is already registered with the same login
	 * @param login inputed login for registration
	 * @return true if login is free for registration no one row from database, false 
	 * otherwise
	 */
	@Override
	public boolean isFreeLogin(String login) {
		@SuppressWarnings("unchecked")
		List<String> loginExist= (List<String>) template.findByNamedParam("select login from UserAccount "
				+ "where login like :login", "login", login);
		if(loginExist.isEmpty()){
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
	@Override
	public boolean isCorrectCapcha(int number, String capcha) {
		String trueCapcha = (String) template.findByNamedParam("select textPhoto from Captcha "
				+ "where photoNumber=:number", "number", number).get(0);
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
	@Transactional
	@Override
	public boolean addNewUser(String login, String password, String confirmPassword, 
			int numberOfPicture,String capcha) {
		if (isFreeLogin(login) && password.equals(confirmPassword) 
				&& isCorrectCapcha(numberOfPicture, capcha)) {
		UserAccount userAccount = new UserAccount();
		userAccount.setLogin(login);
		Set<Role> roles = new HashSet<>();
		Role r = (Role) template.findByCriteria(DetachedCriteria
				.forClass(Role.class).add(Restrictions.eq("roleName", "ROLE_USER"))).get(0);
		//r.setRoleName("ROLE_USER");
		roles.add(r);
		userAccount.setRoles(roles);
		userAccount.setPassword(passwordEncoder.encode(password));
		template.save(userAccount);
		
		/*Integer count = (Integer) template.findByCriteria(DetachedCriteria
				.forClass(CountUser.class).setProjection(Projections.max("count")),0,1)
				.get(0);
		CountUser cu = new CountUser();
		if(count!=null){
			cu.setCount(++count);
			}
		else{ cu.setCount(1);}
		template.save(cu);*/
		return true;
		}
		return false;
	}

	/**
	 * Get total users which already registered
	 * @return an integer of total users from database, 0 if no user is registered
	 */
	@Transactional
	@Override
	public long getTotalUsers() {
		Long total =(Long) template.findByCriteria(DetachedCriteria
				.forClass(UserAccount.class).setProjection(Projections.rowCount()),0,1)
				.get(0) ;
		if(total!=null){
			return total;
		}
		return 0;
	}

	@Override
	public UserAccount findUserByUsername(String username) {
		 @SuppressWarnings("unchecked")
		List<UserAccount> users= (List<UserAccount>) template.findByCriteria(DetachedCriteria
				.forClass(UserAccount.class)
				.add(Restrictions.like("login", username.trim())));
		 if(!users.isEmpty()){
			 return users.get(0);
		 }
		 return new UserAccount();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((template == null) ? 0 : template.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogInClassSpring other = (LogInClassSpring) obj;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		return true;
	}


	
}