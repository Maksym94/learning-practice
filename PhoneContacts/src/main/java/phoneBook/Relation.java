package phoneBook;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Maximka
 * This class bind table contacts_groups with fields of RelationFK class. Current class is used
 * to avoid adding id into table. Create table automatically highlighting the fields of the 
 * class if not exist by hibernate configuration and this entity.
 *
 */
@Entity
@Table(name="contacts_groups")
public class Relation {
	
	@Id
	private RelationFK id;

	public RelationFK getId() {
		return id;
	}

	public void setId(RelationFK id) {
		this.id = id;
	}
	
	

}
