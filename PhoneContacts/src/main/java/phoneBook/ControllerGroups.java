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

public class ControllerGroups implements ActionListener {

	private ViewContacts view;
	private ModelContacts model;
	private ConsolePhoneBookInterface dao;
	private ControllerContacts contrCont;
	private List<String> nameGroups;
	private List<Group> grop;
	private String action;
	Contact c = null;
	/*
	 * private int[] contactsId; private int contactId;
	 */

	public static final String ADD_GROUP = "ADD_GROUP";
	private static final String empty_group_name = "Name for group can't be empty";
	private static final String non_unique_group_name = "This name of group is already exist";
	private static final String too_much_groups = "Maximum numbers of group can't be more than 14";
	private static final String very_long_name_group = "Very long name for group";
	public static final String RENAME_GROUP = "RENAME_GROUP";
	public static final String CLEAR_LIST = "CLEAR_LIST";
	public static final String DELETE_GROUP = "DELETE_GROUP";

	public ControllerGroups(ViewContacts view, ModelContacts model,
			ConsolePhoneBookInterface dao, ControllerContacts contrCont) {

		this.view = view;
		this.model = model;
		this.dao = dao;
		this.contrCont = contrCont;
		view.addGroupButtonListener(this);
		view.addGroupsButtonMouseListener(new GroupMouseAdapter());
		view.addToGroupContactListener(new GroupContactRelation());
		getGroups();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String res = e.getActionCommand();
		System.out.println(res);
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
			System.out.println("Button, that will be deleted " + action);
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

	private void renameGroup(String actionButton) {
		int okButton = view.editGroupPanel(action);
		if (okButton == 0) {
			String name = view.nameGroupInput.getText();
			if (name.length() < 25) {
				Group changedGroup =dao.getGroupFromName(action);
				changedGroup.setNameGroup(name);
				dao.editGroup(changedGroup);
				getGroups();
			}
			 
			else {
				view.emptyFields(ControllerGroups.very_long_name_group);
			}
		}

	}

	private void deleteGroup(String buttonAction) {
		int indexGroup = 0;
		if (view.confirmMenu("group")) {
			indexGroup = dao.getGroupFromName(buttonAction).getIdGroup();
			dao.deleteGroup(indexGroup);
			getGroups();
		}
	}

	private void getGroups() {
		grop = dao.getAllGroups();
		nameGroups = new ArrayList<>();
		for (int i = 0; i < grop.size(); i++) {
			nameGroups.add(grop.get(i).getNameGroup());
		}
		System.out.println(nameGroups.size());
		view.setGroups(nameGroups);

	}

	private boolean uniqueNameGroup(List<String> groups, String name) {

		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(name)) {
				return false;
			}
		}
		return true;
	}

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

	/*
	 * void addToGroup() { contactsId = contrCont.originalIndexes(); }
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
				c = contrCont.originalIndex();
				List<Group> grList = dao.getExistGroupsFromContact(c.getId());
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
	
	private class ListenContactAddToGroup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String res = e.getActionCommand();
			int idContact=c.getId();
			int indexGroup=dao.getGroupFromName(res).getIdGroup();
			dao.addRelativeContactToGroup(idContact, indexGroup);
		
			//model.loadData(dao.showAllContacts());
		}
		
	}
    private class ListenContactDeleteFromGroup implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String res = e.getActionCommand();
			int idContact=c.getId();
			int indexGroup=dao.getGroupFromName(res).getIdGroup();
            dao.removeRelativeContactFromGroup(idContact, indexGroup);
            
            model.loadData(dao.getContactsFromCurrentGroup(indexGroup));
		}
		
	}

	private class GroupContactRelation extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {

			if (e.getSource() instanceof JMenu) {
				JMenu menju = null;
				menju = (JMenu) e.getSource();
				menju.removeAll();
				Contact c = contrCont.originalIndex();
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
