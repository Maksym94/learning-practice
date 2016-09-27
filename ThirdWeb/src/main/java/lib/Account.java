package lib;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Maximka
 * Account - class, which is binded with table "accounts" from database, where contains data 
 * about login of account, committed transaction date, amount of money transaction and 
 * current balance after committed transaction. If table does not exist, it will be created
 * automatically through hiberanate annotations and configuration.
 * 
 */
@Entity
@Table(name="accounts")
public class Account {
	
	@Id
	@Column(name="id_account")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idAccount;
	
	@Column(name="login_account")
	private String loginAccount;
	
	private String date;
	private BigDecimal amount;
	private BigDecimal balance;
	
	public int getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + idAccount;
		result = prime * result + ((loginAccount == null) ? 0 : loginAccount.hashCode());
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
		Account other = (Account) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idAccount != other.idAccount)
			return false;
		if (loginAccount == null) {
			if (other.loginAccount != null)
				return false;
		} else if (!loginAccount.equals(other.loginAccount))
			return false;
		return true;
	}
	
}
