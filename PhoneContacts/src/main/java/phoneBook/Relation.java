package phoneBook;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
