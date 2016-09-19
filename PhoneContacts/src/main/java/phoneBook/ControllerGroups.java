package phoneBook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * @author Maximka
 *This class is controller of the groups and any manipulation with them, including adding,
 *editing, clearing, deleting and response for any actions, performed by the user, relating
 *to the groups.
 */
public class ControllerGroups implements ActionListener {

	private ViewContacts view;
	private ModelContacts model;
	private ConsolePhoneBookInterface dao;
	private ControllerContacts controllerContacts;
	private List<String> nameGroups;
	private List<Group> groups;
	private String action;
	private Contact contact = null;

	public static final String ADD_GROUP = "ADD_GROUP";
	private static final String empty_group_name = "Name for group can't be empty";
	private static final String non_unique_group_name = "This name of group is already exist";
	private static final String too_much_groups = "Maximum numbers of group can't be more than 14";
	private static final String very_long_name_group = "Very long name for group";
	public static final String RENAME_GROUP = "RENAME_GROUP";
	public static final String CLEAR_LIST = "CLEAR_LIST";
	public static final String DELETE_GROUP = "DELETE_GROUP";

	/**
	 * Constructor, where get view of program, model of building table contacts,
	 * dao, which is get all data from the database about groups, and controller contacts
	 * for getting information about relative contacts to the group
	 * @param view of the program, where is displayed all items
	 * @param model of table, where contacts is located
	 * @param dao, database manager which connected to database and get data within a 
	 * specified criteria
	 * @param controllerContacts controller contacts to get information about groups
	 */
	public ControllerGroups(ViewContacts view, ModelContacts model,
			ConsolePhoneBookInterface dao, ControllerContacts controllerContacts) {

		this.view = view;
		this.model = model;
		this.dao = dao;
		this.controllerContacts = controllerContacts;
		view.addGroupButtonListener(this);
		view.addGroupsButtonMouseListener(new GroupMouseAdapter());
		view.addToGroupContactListener(new GroupContactRelation());
		getGroups();

	}

	
	
	@Override
	public void actionPerformed(ActionEvent groupActionEvent) {
		String res = groupActionEvent.getActionCommand();
		switch (res) {
		case ADD_GROUP:
			addNewGroup();

			break;
		case RENAME_GROUP:
			renameGroup(action);
			break;
		case CLEAR_LIST:
 if(view.confirmGroupClearMenu(action)){
	int currIdGroup= dao.getGroupFromName(action).getIdGroup();
	dao.clearAllContactsFromCurrentGroup(currIdGroup);
	model.loadData(dao.getContactsFromCurrentGroup(currIdGroup));
 }
			break;

		case DELETE_GROUP:
			deleteGroup(action);
			break;
		default:

			for (int i = 0; i < nameGroups.size(); i++) {
				if (res.equals(nameGroups.get(i))) {
					int idGroup = dao.getGroupFromName(nameGroups.get(i))
							.getIdGroup();
					model.loadData(dao.getContactsFromCurrentGroup(idGroup));
				}
			}
			break;
		}

	}

	/**
	 * Rename group if user option is OK, if cancel no action is performed
	 * @param actionButton, previous name of the group
	 */
	private void renameGroup(String actionButton) {
		int okButton = view.editGroupPanel(actionButton);
		if (okButton == 0) {
			String name = view.nameGroupInput.getText();
			if (name.length() < 25) {
				Group changedGroup =dao.getGroupFromName(actionButton);
				changedGroup.setNameGroup(name);
				dao.editGroup(changedGroup);
				getGroups();
			}
			 
			else {
				view.emptyFields(ControllerGroups.very_long_name_group);
			}
		}

	}

	/**
	 * Deleting group from name
	 * @param nameGroup, name of the group, which will be deleted from the database
	 */
	private void deleteGroup(String nameGroup) {
		int indexGroup = 0;
		if (view.confirmMenu("group")) {
			indexGroup = dao.getGroupFromName(nameGroup).getIdGroup();
			dao.deleteGroup(indexGroup);
			getGroups();
		}
	}

	/**
	 * Get all groups from the database and view them in a window
	 */
	private void getGroups() {
		groups = dao.getAllGroups();
		nameGroups = new ArrayList<>();
		for (int i = 0; i < groups.size(); i++) {
			nameGroups.add(groups.get(i).getNameGroup());
		}
		System.out.println(nameGroups.size());
		view.setGroups(nameGroups);

	}

	/**
	 * Checking for unique name of the new group
	 * @param groups, list of the existing groups
	 * @param name, current name of the new group
	 * @return true if name of the group is unique, false if not
	 */
	private boolean uniqueNameGroup(List<String> groups, String name) {

		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(name)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adding new group, but before add, check: right click button, unique name of the
	 * group, name of group are less than 14, length name of the group less than 25 symbols,
	 * length name of the group is not empty. If one of this criteria is false, new group 
	 * won't be created, otherwise new group will be added to database and displayed in view
	 * of the program
	 */
	private void addNewGroup() {

		int pushAction = view.addNewGroupPanel();
		String nameGroup = view.nameGroupInput.getText();
		System.out.println(nameGroups.size());
		if (nameGroups.size() >= 14) {
			view.emptyFields(too_much_groups);
		} else if (nameGroup.length() > 25) {
			view.emptyFields(ControllerGroups.very_long_name_group);
		} else if (pushAction == 0 && !nameGroup.trim().isEmpty()
				&& uniqueNameGroup(nameGroups, nameGroup)) {
			Group grp = new Group();
			grp.setNameGroup(nameGroup);
			dao.addNewGroup(grp);
			getGroups();
		} else if (pushAction == 0 && nameGroup.trim().isEmpty()) {
			view.emptyFields(ControllerGroups.empty_group_name);
		} else if (pushAction == 0 && !nameGroup.trim().isEmpty()
				&& !uniqueNameGroup(nameGroups, nameGroup)) {
			view.emptyFields(ControllerGroups.non_unique_group_name);
		}

	}


	/**
	 * @author Maximka
	 *This class is listen for mouse click on the group, for further processing
	 */
	private class GroupMouseAdapter extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JButton) {
				JButton btn = (JButton) e.getSource();
				action = btn.getText();
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu menu = view.createGroupPopUp();
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {

			if (e.getSource() instanceof JMenu) {
				JMenu menju = null;
				menju = (JMenu) e.getSource();
				menju.removeAll();
				contact = controllerContacts.originalIndex();
				List<Group> grList = dao.getExistGroupsFromContact(contact.getId());
				if (grList != null && grList.size() != 0) {
					ActionListener actli= new ListenContactDeleteFromGroup();
					for (int i = 0; i < grList.size(); i++) {
						JMenuItem itm=new JMenuItem(grList.get(i)
								.getNameGroup());
						itm.addActionListener(actli);
						menju.add(itm);
					}

				} else {
					JMenuItem jpm = new JMenuItem("None");
					jpm.setEnabled(false);
					menju.add(jpm);
				}

			}
		}
	}
	
	/**
	 * @author Maximka
	 *Listen contact when trying to add into group
	 */
	private class ListenContactAddToGroup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String res = e.getActionCommand();
			int idContact=contact.getId();
			int indexGroup=dao.getGroupFromName(res).getIdGroup();
			dao.addRelativeContactToGroup(idContact, indexGroup);
		
		}
		
	}
	
	/**
	 * @author Maximka
	 *Listen contact when trying to delete from group
	 */
    private class ListenContactDeleteFromGroup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String res = e.getActionCommand();
			int idContact=contact.getId();
			int indexGroup=dao.getGroupFromName(res).getIdGroup();
            dao.removeRelativeContactFromGroup(idContact, indexGroup);
            
            model.loadData(dao.getContactsFromCurrentGroup(indexGroup));
		}
		
	}

	/**
	 * @author Maximka
	 *This class is responsible for making relation between contact and group
	 */
	private class GroupContactRelation extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {

			if (e.getSource() instanceof JMenu) {
				JMenu menju = null;
				menju = (JMenu) e.getSource();
				menju.removeAll();
				Contact c = controllerContacts.originalIndex();
				int idCont = c.getId();
				
				List<Group> grList = dao.getNonExistGroupsFromContact(idCont);
				
				if (grList.size() != 0) {
					ActionListener actli=new  ListenContactAddToGroup();
					for (int i = 0; i < grList.size(); i++) {
						JMenuItem itemAdd=new JMenuItem(grList.get(i).getNameGroup());
						itemAdd.addActionListener(actli);
						menju.add(itemAdd);
					}
				} else {
					
					JMenuItem jpm = new JMenuItem("None");
					jpm.setEnabled(false);
					menju.add(jpm);
				}
			}
		}

	}

}
