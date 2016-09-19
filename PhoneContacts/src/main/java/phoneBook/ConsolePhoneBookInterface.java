package phoneBook;

import java.util.List;

public interface ConsolePhoneBookInterface {

	public void addNewContact(Contact cont);

	//public Contact showContact(int id);

	public List<Contact> showAllContacts();

	public void removeContact(int cont);
	
	public void clearAllContactsFromCurrentGroup(int idGroup);
     
	public void editContact(Contact cont);

	public Group getGroupFromName(String nameGroup);

	public void addNewGroup(Group group);

	public void editGroup(Group group);

	public List<Group> getAllGroups();

	public void deleteGroup(int id);

	public List<Contact> getContactsFromCurrentGroup(int group);
	
	public List<Group> getNonExistGroupsFromContact(int idContact);
	
	public List<Group> getExistGroupsFromContact(int idContact);
	
	public void addRelativeContactToGroup(int idContact,int idGroup);
	
	public void removeRelativeContactFromGroup(int idContact,int idGroup);
	
	public List<Contact> searchContact(String inputData);
	
	public String getContactPhotoPath(int idContact);
}
