package dbModels;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maximka
 * AccountInfoSpring - class which is responsible to get data about account balance, all 
 * transactions of user, data of it's act, amount of money transaction and ability to put and 
 * get money from the account. 
 *
 */
@Repository
public class AccountInfoSpring implements AccountInfo{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	private HibernateTemplate template;
    
	public AccountInfoSpring(HibernateTemplate template) {
		this.template = template;
	}
	/**
	 * Get all transactions from the current account in form date of transaction, amount of 
	 * money transaction and balance
	 * @param login which data needed to get
	 * @return list of transaction or null if no transaction is exists in database
	 */
	@Override
	public List<Account> getAccountInfo(int idAccount) {
		
	    @SuppressWarnings("unchecked")
		List<Account> list =  (List<Account>) template.findByCriteria(DetachedCriteria
	    		.forClass(Account.class).add(Restrictions.eq("loginAccount", idAccount))
	    		.addOrder(Order.desc("idAccount")));
	
		return list;
	}
	
	/**
	 * Put money on current account and check for the right input of number.
	 * @param login to which will be put money
	 * @param amount of money which will be added to current balance. If balance is zero, 
	 * amount of money will be equal to balance
	 * @return true if amount is more than 0, false if not.
	 */
	@Transactional
	@Override
	public boolean putMoney(int idAccount, BigDecimal amount) {
		
		@SuppressWarnings("unchecked")
		List<BigDecimal> oneResultOfMoney= (List<BigDecimal>) 
		template.find("select balance from Account "
				+ "where loginAccount=? ORDER BY idAccount DESC", idAccount);
		//System.out.println("Suppose what is idAccount "+idAccount);
		if(!oneResultOfMoney.isEmpty()&&amount.doubleValue() > 0){
			BigDecimal balance  =oneResultOfMoney.get(0);
			BigDecimal finalBalance = balance.add(amount);
			Account ac = fillAccount(idAccount, amount, finalBalance);
			template.save(ac);
			return true;
		}
		else if (amount.doubleValue() > 0) {
			Account ac = fillAccount(idAccount, amount, amount);
			template.save(ac);
			return true;
		}
		return false;
	}
	
	/**
	 * Filling Account entity to save
	 * @param login of account
	 * @param amount which is put or get
	 * @param finalBalance new balance depend on put money or get money
	 * @return Account entity
	 */
	
	private Account fillAccount(int idAccount, BigDecimal amount, BigDecimal finalBalance) {
		Account ac = new Account();
		ac.setLoginAccount(idAccount);
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
	@Transactional
	@Override
	public boolean removeMoney(int idAccount, BigDecimal amount) {
	
		@SuppressWarnings("unchecked")
		List<BigDecimal> oneResultOfMoney= (List<BigDecimal>)  template
		.find("select balance from Account "
				+ "where loginAccount=? order by idAccount DESC", idAccount);
		
		if(!oneResultOfMoney.isEmpty()){
			BigDecimal balance  =oneResultOfMoney.get(0);
			if (amount.doubleValue() > balance.doubleValue()) {
				return false;
			}
			
			else if (balance.doubleValue() > amount.doubleValue()
					|| balance.doubleValue() == amount.doubleValue()) {
				Account ac = fillAccount(idAccount,amount.negate() , balance.subtract(amount));
				template.save(ac);
				return true;
			}
		}
		return false;
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
		AccountInfoSpring other = (AccountInfoSpring) obj;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		return true;
	}
	

}
