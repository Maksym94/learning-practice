package phoneBook;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Maximka
 *ConsolePhoneBook is database manager, which get information about all data from the database
 *that is needed by the program using hibernate connection and entities
 */
public class ConsolePhoneBook implements ConsolePhoneBookInterface {

	private SessionFactory sf= HibernateUtil.getSessionFactory();

	/**
	 *  Adding new contact
	 */
	@Override
	public void addNewContact(Contact cont) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
		s.save(cont);
		trns.commit();
		
	}

	/** 
	 * Getter of all contacts without duplication
	 * @return list of contacts that are exist
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> showAllContacts() {
		List<Contact> listContacts;
		Session s = sf.getCurrentSession();
		Transaction trns =null;
		trns= s.beginTransaction();
		listContacts =(List<Contact>) s.createCriteria(Contact.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		trns.commit();
		
		return listContacts;
	}
//http://stackoverflow.com/questions/5776098/hibernate-session-delete-an-object-if-exists
	
	/** 
	 * Remove contact
	 * @param cont index of contact which will be deleted
	 */
	@Override
	public void removeContact(int cont) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
	     Contact c = new Contact();
	     c.setId(cont);
	     s.delete(c);
	     trns.commit();

	}
	
	/** 
	 * Editing contact
	 * @param cont contact which will be edited by the user
	 */
	@Override
	public void editContact(Contact cont) {
		
Session s = sf.getCurrentSession();
Transaction trns = s.beginTransaction();
s.update(cont);
trns.commit();
		
	}
	
	/** 
	 * Add new group
	 * @param group which will be added
	 */
	@Override
	public void addNewGroup(Group group) {
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
		s.save(group);
		trns.commit();
	}
	
	/** 
	 * Edit existing group
	 * @param group which will be edited
	 */
	@Override
	public void editGroup(Group group) {
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
		s.update(group);
		trns.commit();
	}

	
	/** 
	 * Getting contacts from current group by index
	 * @param group index of group, in which get the list of contacts
	 */
	@Override
	public List<Contact> getContactsFromCurrentGroup(int group) {
		List<Contact> listContactsFromGroup;
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
	
	Group gr=(Group) s.createQuery("from Group where  idGroup=:idGroup")
			.setParameter("idGroup", group).uniqueResult();
		trns.commit();
		listContactsFromGroup = gr.getContacts();
		return listContactsFromGroup;
	}

	/** 
	 * Getting list of all existing groups
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getAllGroups() {
		List<Group> groupList =new ArrayList<>();
		Session s = sf.getCurrentSession();
		Transaction trns =null;
		trns= s.beginTransaction();
		groupList = s.createCriteria(Group.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		trns.commit();
		return groupList;
	}

	/** 
	 * Delete group by index of the group
	 * @param id index of the group which will be deleted
	 */
	@Override
	public void deleteGroup(int id) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
	     Group g = new Group();
	     g.setIdGroup(id);
	     s.delete(g);
	     trns.commit();
		
	}

	/** 
	 * Getting non exist groups from the current contact. This data needed to have ability 
	 * add groups which is not relate to the contact
	 * @param idContact id contact from database
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getNonExistGroupsFromContact(int idContact) {
		
		List<Group> groupList = new ArrayList<Group>();
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
		
	groupList=(List<Group>)	s.createQuery("select distinct g "
			+ "from Group g  "
				+ "where  g.idGroup not IN (select g.idGroup "
				+ "from Group g join g.contacts c"
				+ " where c.id=:id)")
				.setParameter("id", idContact).list();
		trns.commit();
		return groupList;
	}
	
	/** 
	 * Creating relative between contact and group
	 * @param idContact index of contact
	 * @param idGroup index of group
	 */
	@Override
	public void addRelativeContactToGroup(int idContact, int idGroup) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
		Relation relation = new Relation();
		RelationFK fk = new RelationFK();
		fk.setIdContact(idContact);
		fk.setIdGroup(idGroup);
		relation.setId(fk);
		s.save(relation);
		trns.commit();
	}

	/** 
	 * Remove relative between contact and group
	 * @param idContact index of contact
	 * @param idGroup index of group
	 */
	@Override
	public void removeRelativeContactFromGroup(int idContact, int idGroup) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
	     Relation relation = new Relation();
	     RelationFK fk = new RelationFK();
	     fk.setIdContact(idContact);
	     fk.setIdGroup(idGroup);
	     relation.setId(fk);
	     s.delete(relation);
	     trns.commit();
	}

	/** 
	 * Getting exist groups from the current contact. This data needed to have ability to
	 * delete groups which is relate to the contact
	 * @param idContact id contact from database
	 */
	@Override
	public List<Group> getExistGroupsFromContact(int idContact) {
		
		List<Group> groupList = new ArrayList<Group>();
		Session s =sf.getCurrentSession();
		Transaction trsn= s.beginTransaction();
		Contact c=(Contact) s.createQuery("from Contact where id=:id")
				.setParameter("id", idContact)
				.uniqueResult();
		trsn.commit();
		groupList = c.getGroups();
		return groupList;
	}

	/** 
	 * Clear all contacts from current group using index group
	 * @param idGroup index of group
	 */
	@Override
	public void clearAllContactsFromCurrentGroup(int idGroup) {
		Session s =sf.getCurrentSession();
		Transaction trsn= s.beginTransaction();
		Group g = (Group) s.createQuery("from Group where idGroup=:idGroup")
				.setParameter("idGroup", idGroup).uniqueResult();
		
		for (Contact c : g.getContacts()) {
			Relation relation  = new Relation();
			RelationFK fk = new RelationFK();
			fk.setIdGroup(idGroup);
			fk.setIdContact(c.getId());
			relation.setId(fk);
			s.delete(relation);
		}
		
		trsn.commit();
	}
	
	/** 
	 * Search any information about contact in order to find it using key string word
	 * @param inputData key word for search
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> searchContact(String inputData) {
		List<Contact> contact = new ArrayList<Contact>();
		Session s =sf.getCurrentSession();
		Transaction trsn= s.beginTransaction();
		contact=(List<Contact> ) s.createQuery("from Contact where name=:name "
				+ "or surname=:surname or cellNumber=:cellNumber "
				+ "or date=:date or company=:company or homeNumber=:homeNumber "
				+ "or workNumber=:workNumber or email=:email or address=:address "
				+ "or website=:website or skype=:skype "
				+ "or photoPath=:photoPath")
				.setParameter("name", inputData)
				.setParameter("surname", inputData)
				.setParameter("cellNumber", inputData)
				.setParameter("date", inputData)
				.setParameter("company", inputData)
				.setParameter("homeNumber", inputData)
				.setParameter("workNumber", inputData)
				.setParameter("email", inputData)
				.setParameter("address", inputData)
				.setParameter("website", inputData)
				.setParameter("skype", inputData)
				.setParameter("photoPath", inputData)
				.list();
		trsn.commit();
		
		return contact;
	}

	/** 
	 * Get path from the contact using it's index
	 * @param idContact index of contact
	 */
	@Override
	public String getContactPhotoPath(int idContact) {
		String path = null;
		Session s =sf.getCurrentSession();
		Transaction trsn= s.beginTransaction();
		path=(String)s.createQuery("Select photoPath "
				+ "from Contact where id =:id").setParameter("id", idContact)
				.uniqueResult();
		trsn.commit();
		
		return path;
	}
	
	/** 
	 * Get entity group from it's name
	 * @param nameGroup name of group
	 */
	@Override
	public Group getGroupFromName(String nameGroup) {
		Group group;
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
	
	group=(Group) s.createQuery("from Group where  nameGroup=:nameGroup")
			.setParameter("nameGroup", nameGroup).uniqueResult();
		trns.commit();
		
		return group;
	}
}
