package dbModels;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Maximka
 * 
 * Data base manager of admin operations. It has such operations: delete user, edit user login and 
 * password, edit balance of operation and delete operation.
 *
 */
public class AdminDataBaseSpring implements AdminDataBase{
	
	private HibernateTemplate template;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public AdminDataBaseSpring(HibernateTemplate template) {
		this.template = template;
	}
	
	@Override
	/**
	 * Get all existed users
	 * @return list of Users if more that o is exist, otherwise return empty list.
	 */
	public List<UserAccount> getAllUsers() {
		
		@SuppressWarnings("unchecked")
		List<UserAccount> users = (List<UserAccount>) template.findByCriteria(
				DetachedCriteria.forClass(UserAccount.class)
				.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY));
		if(users==null){
			return new ArrayList<UserAccount>();
		}
		return users;
	}
	
	@Transactional
	@Override
	/**
	 * Edit user password and login.
	 * @param user which will be edited
	 */
	public void editUser(UserAccount user) {
		if(!user.getPassword().startsWith("$2a$10$")&&user.getPassword().length()<21){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		template.update(user);
	}
	
	@Transactional
	@Override
	/**
	 * Delete user from.
	 * @param user which will be deleted
	 */
	public void deleteUser(UserAccount user) {
		template.delete(user);
	}

	@Override
	/**
	 * Get user from id.
	 * @param index of user
	 * @return User if index is according to user, that stored in database, otherwise return 
	 * empty user.
	 */
	public UserAccount getUserById(int index) {
		 @SuppressWarnings("unchecked")
			List<UserAccount> users= (List<UserAccount>) template.findByCriteria(DetachedCriteria
					.forClass(UserAccount.class)
					.add(Restrictions.eq("id", index)));
			 if(!users.isEmpty()){
				 return users.get(0);
			 }
			 return new UserAccount();
	}

	@Override
	/**
	 * Delete user account operation.
	 * @param index that according to operation
	 */
	public void deleteOperation(int index) {
		template.getSessionFactory().openSession()
		.createQuery("delete from Account where idAccount=:idAccount")
		.setParameter("idAccount", index).executeUpdate();
	}

	@Override
	/**
	 * Find operation by it's id.
	 * @param index of operation
	 * @return operation if id is correct, if not return empty operation.
	 */
	public Account findOperatonById(int index) {
		@SuppressWarnings("unchecked")
		List<Account> users= (List<Account>) template.findByCriteria(DetachedCriteria
				.forClass(Account.class)
				.add(Restrictions.eq("id", index)));
		 if(!users.isEmpty()){
			 return users.get(0);
		 }
		 return new Account();
	}

	@Transactional
	@Override
	/**
	 * Edit balance of current operation.
	 * @param operation which will be edited
	 */
	public void editOperaton(Account account) {
		template.update(account);
	}

}
