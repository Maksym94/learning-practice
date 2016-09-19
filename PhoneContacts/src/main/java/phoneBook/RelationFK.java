package phoneBook;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Maximka
 *This class is bind to table of contacts_groups. This table don't have a primary key, because
 * table can have more than one same id of contact and id of group. Using hibernate annotations
 * performing relation between column in contacts_groups and field in this class.
 */
@Embeddable
public class RelationFK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="id_contact")
	private int idContact;
	
	@Column(name="id_group")
	private int idGroup;
	
	public int getIdContact() {
		return idContact;
	}
	public void setIdContact(int idContact) {
		this.idContact = idContact;
	}
	public int getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}
	

}
