package phoneBook;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ConsolePhoneBook implements ConsolePhoneBookInterface {

	//private Connection conn = ConnectionToMYSQL.getInstance().getConnection();
	private SessionFactory sf= HibernateUtil.getSessionFactory();

	@Override
	public void addNewContact(Contact cont) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
		s.save(cont);
		trns.commit();
		//s.close();
		/*PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("INSERT INTO phone_contacts.fullcontacts (name, surname, "
							+ "cellNumber, Date, company, homeNumber, workNumber, email, address, "
							+ "website, skype, photopath) "
							+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setString(1, cont.getName());
			ps.setString(2, cont.getSurname());
			ps.setString(3, cont.getCellNumber());
			ps.setString(4, cont.getDate());
			ps.setString(5, cont.getCompany());
			ps.setString(6, cont.getHomeNumber());
			ps.setString(7, cont.getWorkNumber());
			ps.setString(8, cont.getEmail());
			ps.setString(9, cont.getAddress());
			ps.setString(10, cont.getWebsite());
			ps.setString(11, cont.getSkype());
			ps.setString(12, cont.getPhotoPath());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
	}
/*
	@Override
	public Contact showContact(int id) {
		PreparedStatement ps = null;
		Contact c = null;
		try {
			ps = conn
					.prepareStatement("SELECT * FROM phone_contacts.fullcontacts WHERE id =?");
			ps.setInt(1, id);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			c = new Contact();

			while (rs.next()) {
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String cellNumber = rs.getString("cellNumber");
				String Date = rs.getString("Date");
				String company = rs.getString("company");
				String homeNumber = rs.getString("homeNumber");
				String workNumber = rs.getString("workNumber");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String website = rs.getString("website");
				String skype = rs.getString("skype");
				String photopath = rs.getString("photopath");
				c.setId(id);
				c.setName(name);
				c.setSurname(surname);
				c.setCellNumber(cellNumber);
				c.setDate(Date);
				c.setCompany(company);
				c.setHomeNumber(homeNumber);
				c.setWorkNumber(workNumber);
				c.setEmail(email);
				c.setAddress(address);
				c.setWebsite(website);
				c.setSkype(skype);
				c.setPhotoPath(photopath);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return c;
	}
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
		//s.close();
		/*PreparedStatement ps = null;
		PreparedStatement psG = null;
		try {
			ps = conn
					.prepareStatement("SELECT * FROM phone_contacts.fullcontacts ");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Contact c = new Contact();
				int id = rs.getInt("id");
				psG = conn
						.prepareStatement("SELECT phone_contacts.groups.id_group,nameGroup "
								+ "FROM phone_contacts.groups "
								+ "JOIN phone_contacts.contacts_groups "
								+ "ON contacts_groups.id_group=groups.id_group "
								+ "JOIN phone_contacts.fullcontacts "
								+ "ON contacts_groups.id_contact=fullcontacts.id "
								+ "WHERE phone_contacts.fullcontacts.id=? ");
				psG.setInt(1, id);
				ResultSet rsGr = psG.executeQuery();
				List<Group> listGroupsInContact = new ArrayList<>();
				while (rsGr.next()) {
					Group grp = new Group();
					int idGroup = rsGr.getInt("id_group");
					String nameGroup = rsGr.getString("nameGroup");
					grp.setIdGroup(idGroup);
					grp.setNameGroup(nameGroup);
					listGroupsInContact.add(grp);

				}
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String cellNumber = rs.getString("cellNumber");
				String Date = rs.getString("Date");
				String company = rs.getString("company");
				String homeNumber = rs.getString("homeNumber");
				String workNumber = rs.getString("workNumber");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String website = rs.getString("website");
				String skype = rs.getString("skype");
				String photopath = rs.getString("photopath");

				c.setId(id);
				c.setName(name);
				c.setSurname(surname);
				c.setCellNumber(cellNumber);
				c.setDate(Date);
				c.setCompany(company);
				c.setHomeNumber(homeNumber);
				c.setWorkNumber(workNumber);
				c.setEmail(email);
				c.setAddress(address);
				c.setWebsite(website);
				c.setSkype(skype);
				c.setPhotoPath(photopath);
				c.setGroups(listGroupsInContact);
				contact.add(c);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		return listContacts;
	}
//http://stackoverflow.com/questions/5776098/hibernate-session-delete-an-object-if-exists
	@Override
	public void removeContact(int cont) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
	     Contact c = new Contact();
	     c.setId(cont);
	     s.delete(c);
	     trns.commit();
		// s.close();
		//s.d
	/*	PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("DELETE  FROM phone_contacts.contacts_groups WHERE id_contact=?");
			ps.setInt(1, cont);
			ps.executeUpdate();
			ps = conn
					.prepareStatement("DELETE FROM phone_contacts.fullcontacts WHERE id = ?");
			ps.setInt(1, cont);
			ps.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}*/

	}
	@Override
	public void editContact(Contact cont) {

		Session s = sf.getCurrentSession();
Transaction trns = s.beginTransaction();
s.update(cont);
trns.commit();
		/*PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("UPDATE phone_contacts.fullcontacts set name=?, "
							+ "surname=?, cellNumber=?, Date=?, company=?, homeNumber=?, "
							+ "workNumber=?, email=?, address=?, website=?, skype=?, photoPath=? "
							+ "WHERE ID=?");

			ps.setString(1, cont.getName());
			ps.setString(2, cont.getSurname());
			ps.setString(3, cont.getCellNumber());
			ps.setString(4, cont.getDate());
			ps.setString(5, cont.getCompany());
			ps.setString(6, cont.getHomeNumber());
			ps.setString(7, cont.getWorkNumber());
			ps.setString(8, cont.getEmail());
			ps.setString(9, cont.getAddress());
			ps.setString(10, cont.getWebsite());
			ps.setString(11, cont.getSkype());
			ps.setString(12, cont.getPhotoPath());
			ps.setInt(13, cont.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
	}

	@Override
	public void addNewGroup(Group group) {
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
		s.save(group);
		trns.commit();
/*		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("INSERT INTO phone_contacts.groups (nameGroup) VALUE (  ?)");

			ps.setString(1, group.getNameGroup());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
*/
	}

	@Override
	public void editGroup(Group group) {
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
		s.update(group);
		trns.commit();
	/*	PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("UPDATE phone_contacts.groups set nameGroup=? "
							+ "WHERE id_group=?");

			ps.setString(1, group.getNameGroup());
			ps.setInt(2, group.getIdGroup());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/

	}

	

	@Override
	public List<Contact> getContactsFromCurrentGroup(int group) {
		List<Contact> listContactsFromGroup;
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
	
	Group gr=(Group) s.createQuery("from Group where  idGroup=:idGroup")
			.setParameter("idGroup", group).uniqueResult();
		trns.commit();
		listContactsFromGroup = gr.getContacts();
		//s.close();
		/*
		listContactsFromGroup =
		PreparedStatement ps = null;
		PreparedStatement psG = null;
		try {
			ps = conn
					.prepareStatement("SELECT id,name,surname,cellNumber,Date,company,homeNumber,"
							+ "workNumber,email,Address,website,skype,photoPath "
							+ "FROM phone_contacts.fullcontacts "
							+ "JOIN phone_contacts.contacts_groups "
							+ "ON(phone_contacts.contacts_groups.id_contact = phone_contacts.fullcontacts.id) "
							+ "WHERE phone_contacts.contacts_groups.id_group = ?");
			ps.setInt(1, group);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Contact c = new Contact();
				int id = rs.getInt("id");
				psG = conn
						.prepareStatement("SELECT phone_contacts.groups.id_group,nameGroup "
								+ "FROM phone_contacts.groups "
								+ "JOIN phone_contacts.contacts_groups "
								+ "ON contacts_groups.id_group=groups.id_group "
								+ "JOIN phone_contacts.fullcontacts "
								+ "ON contacts_groups.id_contact=fullcontacts.id "
								+ "WHERE phone_contacts.fullcontacts.id=? ");
				psG.setInt(1, id);
				ResultSet rsGr = psG.executeQuery();
				List<Group> listGroupsInContact = new ArrayList<>();
				while (rsGr.next()) {
					Group grp = new Group();
					int idGroup = rsGr.getInt("id_group");
					String nameGroup = rsGr.getString("nameGroup");
					grp.setIdGroup(idGroup);
					grp.setNameGroup(nameGroup);
					listGroupsInContact.add(grp);

				}
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String cellNumber = rs.getString("cellNumber");
				String Date = rs.getString("Date");
				String company = rs.getString("company");
				String homeNumber = rs.getString("homeNumber");
				String workNumber = rs.getString("workNumber");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String website = rs.getString("website");
				String skype = rs.getString("skype");
				String photopath = rs.getString("photopath");

				c.setId(id);
				c.setName(name);
				c.setSurname(surname);
				c.setCellNumber(cellNumber);
				c.setDate(Date);
				c.setCompany(company);
				c.setHomeNumber(homeNumber);
				c.setWorkNumber(workNumber);
				c.setEmail(email);
				c.setAddress(address);
				c.setWebsite(website);
				c.setSkype(skype);
				c.setPhotoPath(photopath);
				c.setGroups(listGroupsInContact);
				listContactsFromGroup.add(c);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		return listContactsFromGroup;
	}

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
		//s.close();
		/*PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM phone_contacts.groups ");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Group gr = new Group();
				int id = rs.getInt("id_group");
				String name = rs.getString("nameGroup");

				gr.setIdGroup(id);
				gr.setNameGroup(name);

				groupList.add(gr);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		return groupList;
	}


	@Override
	public void deleteGroup(int id) {
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
	     Group g = new Group();
	     g.setIdGroup(id);
	     s.delete(g);
	     trns.commit();
		// s.close();
		/*PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM phone_contacts.groups WHERE id_group = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps = conn
			.prepareStatement("DELETE FROM phone_contacts.contacts_groups WHERE id_group=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

	/*I have one variable like long[] ids = [10, 11] and 
	 * I am trying to fire query like this :
		Query query2 = session.createQuery("update 
		Employee e SET e.isLatest = false where e.id not in (:ids)");
		query2.setParameter("ids", ids);
		query2.executeUpdate()*/
	@SuppressWarnings("unchecked")
	//http://stackoverflow.com/questions/
	//4828049/in-clause-in-hql-or-java-persistence-query-language
	@Override
	public List<Group> getNonExistGroupsFromContact(int idContact) {
		
		List<Group> groupList = new ArrayList<Group>();
		Session s = sf.getCurrentSession();
		Transaction trns = null;
		trns=s.beginTransaction();
	/*	List<Integer> indexes= new ArrayList<>();
		indexes= (List<Integer> )s.createQuery("select g.idGroup "
				+ "from Group g join g.contacts c"
				+ " where c.id=:id")
				.setParameter("id", idContact).list();*/
		
		
	groupList=(List<Group>)	s.createQuery("select distinct g "
			+ "from Group g  "
				+ "where  g.idGroup not IN (select g.idGroup "
				+ "from Group g join g.contacts c"
				+ " where c.id=:id)")
				.setParameter("id", idContact).list();
		trns.commit();
		//s.close();
		/*PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM phone_contacts.groups "
					+ "WHERE NOT phone_contacts.groups.id_group = ANY("
					+ "SELECT id_group FROM phone_contacts.contacts_groups "
					+ "WHERE phone_contacts.contacts_groups.id_contact=?)");
			ps.setInt(1, idContact);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Group gr = new Group();
				int id = rs.getInt("id_group");
				String name = rs.getString("nameGroup");

				gr.setIdGroup(id);
				gr.setNameGroup(name);

				groupList.add(gr);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		return groupList;
	}

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
		//s.close();
		/*PreparedStatement ps = null;
		try {

		ps = conn.prepareStatement("INSERT INTO phone_contacts.contacts_groups "
				+ "(id_group,id_contact) VALUES (?,?)");

			 ps.setInt(1, idGroup);
			 ps.setInt(2, idContact);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		*/
	}

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
		 //s.close();
		/*PreparedStatement ps = null;
		try {
			ps = conn
			.prepareStatement("DELETE  FROM phone_contacts.contacts_groups "
					+ "WHERE id_group=? "
					+ "AND id_contact=?");
			ps.setInt(1, idGroup);
			ps.setInt(2, idContact);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/	
	}

	@Override
	public List<Group> getExistGroupsFromContact(int idContact) {
		
		List<Group> groupList = new ArrayList<Group>();
		Session s =sf.getCurrentSession();
		Transaction trsn= s.beginTransaction();
		Contact c=(Contact) s.createQuery("from Contact where id=:id")
				.setParameter("id", idContact)
				.uniqueResult();
		trsn.commit();
		//s.close();
		groupList = c.getGroups();
		/*try {
		  PreparedStatement ps = null;
			ps = conn.prepareStatement("SELECT * FROM phone_contacts.groups "
					+ "WHERE  phone_contacts.groups.id_group = ANY("
					+ "SELECT id_group FROM phone_contacts.contacts_groups "
					+ "WHERE phone_contacts.contacts_groups.id_contact=?)");
			ps.setInt(1, idContact);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Group gr = new Group();
				int id = rs.getInt("id_group");
				String name = rs.getString("nameGroup");

				gr.setIdGroup(id);
				gr.setNameGroup(name);

				groupList.add(gr);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		return groupList;
	}

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
		
		/*s.createQuery("delete from Relation where id.idGroup=:id")
		.setParameter("id",idGroup);*/
		trsn.commit();
		//s.close();
		/*PreparedStatement ps = null;
		try {
			ps = conn
			.prepareStatement("DELETE  FROM phone_contacts.contacts_groups "
					+ "WHERE id_group=? ");
			ps.setInt(1, idGroup);

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		
	}

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
		//s.close();
		/*PreparedStatement ps = null;
		PreparedStatement psG = null;
		try {
			ps = conn
					.prepareStatement("SELECT * FROM phone_contacts.fullcontacts "
							+ "WHERE name=? OR surname=? OR cellNumber=? OR "
							+ "Date=? OR company=? OR homeNumber=? OR "
							+ "workNumber=? OR email=? OR Address=? OR website=? OR "
							+ "skype=? OR photoPath=? ");
			ps.setString(1, inputData);
			ps.setString(2, inputData);
			ps.setString(3, inputData);
			ps.setString(4, inputData);
			ps.setString(5, inputData);
			ps.setString(6, inputData);
			ps.setString(7, inputData);
			ps.setString(8, inputData);
			ps.setString(9, inputData);
			ps.setString(10, inputData);
			ps.setString(11, inputData);
			ps.setString(12, inputData);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Contact c = new Contact();
				int id = rs.getInt("id");
				psG = conn
						.prepareStatement("SELECT phone_contacts.groups.id_group,nameGroup "
								+ "FROM phone_contacts.groups "
								+ "JOIN phone_contacts.contacts_groups "
								+ "ON contacts_groups.id_group=groups.id_group "
								+ "JOIN phone_contacts.fullcontacts "
								+ "ON contacts_groups.id_contact=fullcontacts.id "
								+ "WHERE phone_contacts.fullcontacts.id=? ");
				psG.setInt(1, id);
				ResultSet rsGr = psG.executeQuery();
				List<Group> listGroupsInContact = new ArrayList<>();
				while (rsGr.next()) {
					Group grp = new Group();
					int idGroup = rsGr.getInt("id_group");
					String nameGroup = rsGr.getString("nameGroup");
					grp.setIdGroup(idGroup);
					grp.setNameGroup(nameGroup);
					listGroupsInContact.add(grp);

				}
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String cellNumber = rs.getString("cellNumber");
				String Date = rs.getString("Date");
				String company = rs.getString("company");
				String homeNumber = rs.getString("homeNumber");
				String workNumber = rs.getString("workNumber");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String website = rs.getString("website");
				String skype = rs.getString("skype");
				String photopath = rs.getString("photopath");

				c.setId(id);
				c.setName(name);
				c.setSurname(surname);
				c.setCellNumber(cellNumber);
				c.setDate(Date);
				c.setCompany(company);
				c.setHomeNumber(homeNumber);
				c.setWorkNumber(workNumber);
				c.setEmail(email);
				c.setAddress(address);
				c.setWebsite(website);
				c.setSkype(skype);
				c.setPhotoPath(photopath);
				c.setGroups(listGroupsInContact);
				contact.add(c);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		return contact;
	}

	@Override
	public String getContactPhotoPath(int idContact) {
		String path = null;
		Session s =sf.getCurrentSession();
		Transaction trsn= s.beginTransaction();
		path=(String)s.createQuery("Select photoPath "
				+ "from Contact where id =:id").setParameter("id", idContact)
				.uniqueResult();
		trsn.commit();
		//s.close();
		/*PreparedStatement ps = null;
		try {
		
			ps = conn.prepareStatement("SELECT photoPath "
					+ "FROM phone_contacts.fullcontacts WHERE id=?");
			ps.setInt(1, idContact);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				path = rs.getString("photoPath");
			}
			System.out.println("dd = " + path);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		*/
		return path;
	}
	@Override
	public Group getGroupFromName(String nameGroup) {
		Group group;
		Session s = sf.getCurrentSession();
		Transaction trns = s.beginTransaction();
	
	group=(Group) s.createQuery("from Group where  nameGroup=:nameGroup")
			.setParameter("nameGroup", nameGroup).uniqueResult();
		trns.commit();
		//s.close();
	
		/*PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("SELECT id_group FROM phone_contacts.groups WHERE nameGroup=?");
			ps.setString(1, nameGroup);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				id = rs.getInt("id_group");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}*/
		return group;
	}
}
