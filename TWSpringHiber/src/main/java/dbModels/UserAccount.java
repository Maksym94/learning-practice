package dbModels;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author Maximka
 * 
 * This class is binded table "users" from database where contains data about login and 
 * password of the bank account,which will be checked during log in of the user. If table 
 * does not exist, create it through hibernate annotations and configuration.
 *
 */
@Entity
@Table(name="users")
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String login;
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER/*,  cascade=CascadeType.PERSIST */ )
	@JoinTable(name="user_role", joinColumns=@JoinColumn(name="id_user", 
	referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="id_role",
	referencedColumnName="id"))
	private Set<Role> roles;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL )
	@JoinColumn(name="id_login"/*, 
	referencedColumnName="id_login"*/)
	@OrderBy("id_account DESC")
	private List<Account> accountOperations;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public List<Account> getAccountOperations() {
		return accountOperations;
	}
	public void setAccountOperations(List<Account> accountOperations) {
		this.accountOperations = accountOperations;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		UserAccount other = (UserAccount) obj;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	

 
}
