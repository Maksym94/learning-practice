package phoneBook;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group {
	
	@Column(name="name_group")
	private String nameGroup;
	
	@Id
	@Column(name="id_group")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idGroup;
	
	@ManyToMany(/*cascade = CascadeType.ALL,*/ fetch=FetchType.EAGER)
	@JoinTable(name = "contacts_groups", joinColumns = 
	@JoinColumn(name="id_group",referencedColumnName="id_group"),
	inverseJoinColumns=@JoinColumn(name = "id_contact",  referencedColumnName = "id"))
	private List<Contact> contacts;
	
	public String getNameGroup() {
		return nameGroup;
	}
	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}
	public int getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + idGroup;
		result = prime * result
				+ ((nameGroup == null) ? 0 : nameGroup.hashCode());
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
		Group other = (Group) obj;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (idGroup != other.idGroup)
			return false;
		if (nameGroup == null) {
			if (other.nameGroup != null)
				return false;
		} else if (!nameGroup.equals(other.nameGroup))
			return false;
		return true;
	}
	
   
}
