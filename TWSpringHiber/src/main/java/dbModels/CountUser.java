package dbModels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Maximka
 * CountUser - class which is binded table "total_users" from database, where contains data 
 * about total number count of users whose already registered on the server. If table doesn't 
 * exists create it through hibernate annotations and configuration.
 * 
 */
@Entity
@Table(name="total_users")
public class CountUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + id;
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
		CountUser other = (CountUser) obj;
		if (count != other.count)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
