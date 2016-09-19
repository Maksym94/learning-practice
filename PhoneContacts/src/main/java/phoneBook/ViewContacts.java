package phoneBook;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Maximka
 *This class is responsible for view our contact application. All application consists from
 *upper, left and central border. In the upper boarder are located three buttons:
 *button for add contact(addContact), add group(addGroup) and search(search).
 *In the left boarder is located groups panel(groupsPanel). In the central border are located:
 *table with scroll pane, where is actually will be all contacts and their short information.
 *Program has this features: create contact with detail data about address, name, work, home
 *number etc., put photo for each contact, create group, the maximum number is 14 for 
 *convenience, associate contacts with groups, what is the most interesting that many contacts 
 *could be in many groups and vice versa. 
 */
public class ViewContacts extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel contactPanel;
	private JPanel panelFieldsContacts;
	private JPanel groupsPanel;
	private JButton addContact;
	private JButton search;
	private JButton allGroupsButton;
	private JPanel buttonsPanel;
	private JTable table;
	private JButton addGroup;
	private JButton choosePhotoButton;
	private JButton saveContactShortButton;
	private JButton saveGroup;
	private JMenuItem allFieldsItem;
	private JMenuItem editContactItem;
	private JMenuItem deleteContactItem;
	private JMenuItem renameGroupItem;
	private JMenuItem clearListGroupItem;
	private JMenuItem deleteGroupItem;
	private JMenu addToGroupItem;
	private JMenu deleteFromGropItem;
	private JLabel groupLabel;
	private ActionListener groupListener;
	private MouseListener mouseGroupListener;
	protected JTextField nameInput;
	protected JTextField surnameInput;
	protected JTextField cellNumberInput;
	protected JTextField workNumberInput;
	protected JTextField emailInput;
	protected JTextField homeNumberInput;
	protected JTextField adressInput;
	protected JTextField birthInput;
	protected JTextField websiteInput;
	protected JTextField skypeInput;
	protected JTextField companyInput;
	protected JTextField nameGroupInput;
	protected JTextField searchInput;
	protected JScrollPane jScrollPane;
	protected ModelContacts model;
	private static final JLabel NAME_CONTACT_LABEL = new JLabel("Name");
	private static final JLabel SURNAME_CONTACT_LABEL = new JLabel("Surname");
	private static final JLabel CELL_NUMBER_LABEL = new JLabel("Cell number");
	private static final JLabel CHOOSE_PHOTO_LABEL = new JLabel("Choose Photo");
	private static final JLabel WORK_NUMBER_LABEL = new JLabel("Work number");
	private static final JLabel EMAIL_LABEL = new JLabel("Email");
	private static final JLabel HOME_NUMBER_LABEL = new JLabel("Home number");
	private static final JLabel ADDRESS_LABEL = new JLabel("Address");
	private static final JLabel BIRTH_LABEL = new JLabel("Date of birth");
	private static final JLabel WEBSITE_LABEL = new JLabel("Website");
	private static final JLabel SKYPE_LABEL = new JLabel("Skype");
	private static final JLabel COMPANY_LABEL = new JLabel("Company");
	private static final JLabel NAME_GROUP_LABEL = new JLabel("Name");
	private static final JLabel SEARCH_INFO_LABEL = 
			new JLabel("Enter any contact information");
	private String path;
	protected int dataBaseID;
	protected List<Contact> contacts;
	protected List<Group> groupsA;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsolePhoneBookInterface dao = new ConsolePhoneBook();
					ModelContacts model = new ModelContacts(dao.showAllContacts());
					ViewContacts window = new ViewContacts(model);
					ControllerContacts contrCont = new ControllerContacts(
							window, model, dao);
					 new ControllerGroups(window, model, dao,contrCont);

					window.frame.setVisible(true);

					// 

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ViewContacts(ModelContacts model) {
		this.model=model;
		initialize();
		 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();//need Log here
		}
		
	}
	
	/**
	 * Initializing all panels except JTextFields for input data about contact or group
	 * and adding pictures for upper buttons: addContact, addGroup and search.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 980, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Phone book v.1.0");
	setLookAndFeel();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				HibernateUtil.closeSessionFactory();
				
			}
		});
       

		ImageIcon newContactImage = null;
		ImageIcon newGroupImage = null;
		ImageIcon searchImage = null;
		Image im=null;
		try {
			im = ImageIO.read(getClass().getResource("/resources/icon.png"));
			
			newContactImage = new ImageIcon(ImageIO.read(getClass()
					.getResource("/resources/add_new_contact.png")));
			newGroupImage = new ImageIcon(ImageIO.read(getClass().getResource(
					"/resources/add_new_group.png")));
			searchImage = new ImageIcon(ImageIO.read(getClass().getResource(
					"/resources/search.png")));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		frame.setIconImage(im);
		
		addContact = new JButton(newContactImage);
		addContact.setText("Add conntact");
		addContact.setActionCommand(ControllerContacts.ADD_CONTACT);
		addGroup = new JButton(newGroupImage);
		addGroup.setText("Add group");
		addGroup.setActionCommand(ControllerGroups.ADD_GROUP);
		search = new JButton(searchImage);
		search.setText("Search");
		search.setActionCommand(ControllerContacts.SEARCH);
		choosePhotoButton = new JButton("Choose from directories");
		choosePhotoButton.setActionCommand(ControllerContacts.CHOOSE_PHOTO);
		saveContactShortButton = new JButton("Save");
		saveContactShortButton.setActionCommand(ControllerContacts.SAVE_CONTACT);
		saveGroup = new JButton("Save");
		saveGroup.setActionCommand(ControllerContacts.SAVE_GROUP);
		allFieldsItem= new JMenuItem("Show all Fields");
		editContactItem= new JMenuItem("Edit");
		addToGroupItem= new JMenu("Add to group");
		deleteFromGropItem= new JMenu("Delete from group");
		deleteContactItem= new JMenuItem("Detele");
		renameGroupItem= new JMenuItem("Rename");
		clearListGroupItem= new JMenuItem("Clear group");
		deleteGroupItem= new JMenuItem("Delete");
		allFieldsItem.setActionCommand(ControllerContacts.SHOW_ALL_FIELDS);
		editContactItem.setActionCommand(ControllerContacts.EDIT_CONTACT);
		deleteContactItem.setActionCommand(ControllerContacts.DELETE_CONTACT);
		renameGroupItem.setActionCommand(ControllerGroups.RENAME_GROUP);
		clearListGroupItem.setActionCommand(ControllerGroups.CLEAR_LIST);
		deleteGroupItem.setActionCommand(ControllerGroups.DELETE_GROUP);
		allFieldsItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		editContactItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		deleteContactItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		addToGroupItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		deleteFromGropItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		renameGroupItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		clearListGroupItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		deleteGroupItem.setFont(new Font("sans-serif", Font.PLAIN, 14));
		GridLayout grdForButt = new GridLayout(1, 3);
		grdForButt.setHgap(2);
		grdForButt.setVgap(3);
		buttonsPanel = new JPanel(grdForButt);
		buttonsPanel.add(addContact);
		buttonsPanel.add(addGroup);
		buttonsPanel.add(search);
		
		table = new JTable(model);
		table.setRowHeight(25);
		jScrollPane=new JScrollPane(table);
		groupsPanel= new JPanel(new GridLayout(16,0));
		groupsPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	    allGroupsButton= new JButton("All groups");
	    allGroupsButton.setActionCommand(ControllerContacts.ALL_GROUPS);
	    groupLabel= new JLabel("List of existing groups");
	    groupsPanel.add(groupLabel);
	    groupsPanel.add(allGroupsButton);
	  
		frame.getContentPane().add(jScrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(buttonsPanel, BorderLayout.NORTH);
	    frame.add(groupsPanel,BorderLayout.WEST);	
	}

	/**
	 * Create contact panel when adding new contact in a program and storage
	 * @return an integer indicating the option selected by the user
	 */
	public int addNewContactPanel() {
		contactPanel = new JPanel(new BorderLayout());
		
		nameInput = new JTextField();
		surnameInput = new JTextField();
		cellNumberInput = new JTextField();
		workNumberInput = new JTextField();
		emailInput = new JTextField();
		homeNumberInput = new JTextField();
		adressInput = new JTextField();
		birthInput = new JTextField();
		websiteInput = new JTextField();
		skypeInput = new JTextField();
		companyInput = new JTextField();
		panelFieldsContacts = new JPanel(new GridLayout(12, 2, 0, 4));
		panelFieldsContacts.add(NAME_CONTACT_LABEL);
		panelFieldsContacts.add(nameInput);
		panelFieldsContacts.add(SURNAME_CONTACT_LABEL);
		panelFieldsContacts.add(surnameInput);
		panelFieldsContacts.add(CELL_NUMBER_LABEL);
		panelFieldsContacts.add(cellNumberInput);
		panelFieldsContacts.add(CHOOSE_PHOTO_LABEL);
		panelFieldsContacts.add(choosePhotoButton);
		panelFieldsContacts.add(WORK_NUMBER_LABEL);
		panelFieldsContacts.add(workNumberInput);
		panelFieldsContacts.add(EMAIL_LABEL);
		panelFieldsContacts.add(emailInput);
		panelFieldsContacts.add(HOME_NUMBER_LABEL);
		panelFieldsContacts.add(homeNumberInput);
		panelFieldsContacts.add(ADDRESS_LABEL);
		panelFieldsContacts.add(adressInput);
		panelFieldsContacts.add(BIRTH_LABEL);
		panelFieldsContacts.add(birthInput);
		panelFieldsContacts.add(WEBSITE_LABEL);
		panelFieldsContacts.add(websiteInput);
		panelFieldsContacts.add(SKYPE_LABEL);
		panelFieldsContacts.add(skypeInput);
		panelFieldsContacts.add(COMPANY_LABEL);
		panelFieldsContacts.add(companyInput);
		contactPanel.add(panelFieldsContacts, BorderLayout.CENTER);

return JOptionPane.showConfirmDialog(null, contactPanel,
				"Add new contact", JOptionPane.OK_CANCEL_OPTION);
	}


	
	/**
	 * @return table for controller to register mouse listener
	 */
	public JTable getTable() {
		return table;
	}

	
	/**
	 * Set the path of the photo if user chose, if not return null.
	 * @return origin path for the photo which user chose for current contact 
	 */
	public String setPath() {
		JFileChooser ch = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & GIF & PNG Images", "jpg", "gif", "png");
		    ch.setFileFilter(filter);
		ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ch.setDialogTitle("Choose Photo");
		ch.setAcceptAllFileFilterUsed(false);
		if (ch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			
		return 	path= ch.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
	
	
	/**
	 * @return the previous path of the photo which was set before
	 */
	public String getPath() {
	
		return path;
	}
	
	/**
	 * @param new path for photo, which will be updated
	 */
	public void setPathForEdit(String path) {
		this.path = path;
	}


	
	/**
	 * Opening panel for creating new group
	 * @return an integer indicating the option selected by the user
	 */
	public int addNewGroupPanel() {
		JPanel groupPan = new JPanel(new BorderLayout());
		
		nameGroupInput = new JTextField();
       
		JPanel panelFieldsGroup = new JPanel(new GridLayout(2, 1));
		panelFieldsGroup.add(NAME_GROUP_LABEL);
		panelFieldsGroup.add(nameGroupInput);
		groupPan.add(panelFieldsGroup, BorderLayout.CENTER);
	return	JOptionPane.showConfirmDialog(null, groupPan, "Add new group",JOptionPane.OK_CANCEL_OPTION);
		
	}
	
	/**
	 * @param previousName putting the previous name of group if user selected okey button
	 * without any change
	 * @return an integer of user option
	 */
	public int editGroupPanel(String previousName) {
		JPanel groupPan = new JPanel(new BorderLayout());
		JLabel nameGroup = new JLabel("Name");
		nameGroupInput = new JTextField(previousName);
       
		JPanel panelFieldsGroup = new JPanel(new GridLayout(2, 1));
		panelFieldsGroup.add(nameGroup);
		panelFieldsGroup.add(nameGroupInput);
		groupPan.add(panelFieldsGroup, BorderLayout.CENTER);
	return	JOptionPane.showConfirmDialog(null, groupPan, "Edit group",JOptionPane.OK_CANCEL_OPTION);
		
	}
	
	/**
	 * Fulfilling left panel with existing groups and binding mouse listener for them
	 * after all actions revalidate panel in case of: second and subsequent request of the
	 * groups, deleting or adding one or more groups
	 * @param groups list of groups which have already exist 
	 */
	public void setGroups(List<String> groups){
  groupsPanel.removeAll();
  groupsPanel.add(groupLabel);
  groupsPanel.add(allGroupsButton);
			for (int i = 0; i < groups.size(); i++) {
				
			JButton b1= new JButton(groups.get(i));
			b1.setActionCommand(groups.get(i));
			b1.addActionListener(groupListener);
			b1.addMouseListener(mouseGroupListener);
			groupsPanel.add(b1);
		}
			groupsPanel.repaint();
			frame.revalidate();
	}
	
	
	/**
	 * Adding listener from controller to view
	 * @param groupsMouseListener listener, which detect position on a current group button
	 */
	public void addGroupsButtonMouseListener(MouseListener groupsMouseListener){
		addToGroupItem.addMouseListener(groupsMouseListener);
		deleteFromGropItem.addMouseListener(groupsMouseListener);
		this.mouseGroupListener=groupsMouseListener;
	}

	
	/**
	 * opening search panel in which user put any information about contact
	 * @return an integer, according to selection of the user option
	 */
	public int searchPanel() {
		JPanel searchPan = new JPanel(new BorderLayout());
		
		searchInput = new JTextField();

		JPanel panelFieldsSearch = new JPanel(new GridLayout(1, 2, 2, 2));
		JPanel panelButtonsSearch = new JPanel(new GridLayout(1, 1, 2, 2));
		panelFieldsSearch.add(SEARCH_INFO_LABEL);
		panelFieldsSearch.add(searchInput);
		searchPan.add(panelFieldsSearch, BorderLayout.CENTER);
		searchPan.add(panelButtonsSearch, BorderLayout.SOUTH);
		return JOptionPane.showConfirmDialog(null, searchPan, "Search",JOptionPane.OK_CANCEL_OPTION);
	}
	
	
	/**
	 * Method of opening dialog window if user input is empty
	 * @param argument warning text, which will be displayed in a dialog window
	 */
	public void emptyFields(String argument) {
		JPanel noData= new JPanel(new GridLayout(1, 1));
		JLabel noInput= new JLabel(argument);
		noData.add(noInput);
		 JOptionPane.showConfirmDialog(null, noData,
				"Empty fields", JOptionPane.CLOSED_OPTION);
		
	}
	
	/**Opening window if no search data was inputted
	 * @param argument of warning text which will be displayed in a window
	 */
	public void noFoundContactsDialog(String argument) {
		JPanel noData= new JPanel(new GridLayout(1, 1));
		JLabel noInput= new JLabel(argument);
		noData.add(noInput);
		 JOptionPane.showConfirmDialog(null, noData,
				"Void search information", JOptionPane.CLOSED_OPTION);
		
	}

	/**Adding all buttons listener except groups buttons, responsible for delete 
	 * and edit group
	 * @param actionListener listener from the controller, to bind buttons with listener
	 */
	public void addButtonListener(ActionListener actionListener) {
		
		addContact.addActionListener(actionListener);
		search.addActionListener(actionListener);
		choosePhotoButton.addActionListener(actionListener);
		saveGroup.addActionListener(actionListener);
		allFieldsItem.addActionListener(actionListener);
		editContactItem.addActionListener(actionListener);
		deleteContactItem.addActionListener(actionListener);
		allGroupsButton.addActionListener(actionListener);
	}
	
	/**
	 * Adding mouse listener for ability to put contact in group
	 * @param mouseListener listener, which detect the selection of the group in current
	 * contact
	 */
	public void addToGroupContactListener(MouseListener mouseListener){
		addToGroupItem.addMouseListener(mouseListener);
	}
	
	/**
	 * Adding listener for button, responsible for delete, edit or clear group
	 * @param actionListener listener for buttons in group
	 */
	public void addGroupButtonListener(ActionListener actionListener){
		groupListener=actionListener;
		addGroup.addActionListener(actionListener);
		 
		 deleteFromGropItem.addActionListener(actionListener);
		renameGroupItem.addActionListener(actionListener);
		clearListGroupItem.addActionListener(actionListener);
		deleteGroupItem.addActionListener(actionListener);
	}
	

	/**
	 * Creating popup menu for contact, when user input mouse3(right mouse button
	 * @return popup menu to the contacts controller
	 */
	public JPopupMenu createContactPopUp() {
		JPopupMenu contactPopUpMenu = new JPopupMenu();
		contactPopUpMenu.add(allFieldsItem);
		contactPopUpMenu.add(editContactItem);
		contactPopUpMenu.add(addToGroupItem);
		contactPopUpMenu.add(deleteFromGropItem);
		contactPopUpMenu.add(deleteContactItem);
		return contactPopUpMenu;
	}
	/**
	 * Creating popup menu for group, when user input mouse3(right mouse button
	 * @return popup menu to the groups controller
	 */
	public JPopupMenu createGroupPopUp() {
		JPopupMenu groupPopUpMenu = new JPopupMenu();
		groupPopUpMenu.add(renameGroupItem);
		groupPopUpMenu.add(clearListGroupItem);
		groupPopUpMenu.add(deleteGroupItem);
		return groupPopUpMenu;
	}

	
	/**
	 * Opening edit panel for contact, getting previous data, fulfilling text fields 
	 * with it and changing if it's need and subsequent saving contact with changing fields
	 * @param change contact with previous data which will be changed 
	 * @return an integer indicating the option selected by the user
	 */
	public int editContactPanel(Contact change) {
		contactPanel = new JPanel(new BorderLayout());
		JLabel homeNumber = new JLabel("Home number");
		JLabel adress = new JLabel("Address");
		JLabel birth = new JLabel("Date of birth");
		JLabel website = new JLabel("Website");
		JLabel skype = new JLabel("Skype");
		JLabel company = new JLabel("Company");
		nameInput = new JTextField();
		surnameInput = new JTextField();
		cellNumberInput = new JTextField();
		workNumberInput = new JTextField();
		emailInput = new JTextField();
		homeNumberInput = new JTextField();
		adressInput = new JTextField();
		birthInput = new JTextField();
		websiteInput = new JTextField();
		skypeInput = new JTextField();
		companyInput = new JTextField();
		nameInput.setText(change.getName());
		surnameInput.setText(change.getSurname());
		cellNumberInput.setText(change.getCellNumber());
		workNumberInput.setText(change.getWorkNumber());
		emailInput.setText(change.getEmail());
		homeNumberInput.setText(change.getHomeNumber());
		adressInput.setText(change.getAddress());
		birthInput.setText(change.getDate());
		websiteInput.setText(change.getWebsite());
		skypeInput.setText(change.getSkype());
		companyInput.setText(change.getCompany());
		path=change.getPhotoPath();
		dataBaseID=change.getId();
		groupsA= change.getGroups();
		panelFieldsContacts = new JPanel(new GridLayout(12, 2, 0, 4));
		panelFieldsContacts.add(NAME_CONTACT_LABEL);
		panelFieldsContacts.add(nameInput);
		panelFieldsContacts.add(SURNAME_CONTACT_LABEL);
		panelFieldsContacts.add(surnameInput);
		panelFieldsContacts.add(CELL_NUMBER_LABEL);
		panelFieldsContacts.add(cellNumberInput);
		panelFieldsContacts.add(CHOOSE_PHOTO_LABEL);
		panelFieldsContacts.add(choosePhotoButton);
		panelFieldsContacts.add(WORK_NUMBER_LABEL);
		panelFieldsContacts.add(workNumberInput);
		panelFieldsContacts.add(EMAIL_LABEL);
		panelFieldsContacts.add(emailInput);
		panelFieldsContacts.add(homeNumber);
		panelFieldsContacts.add(homeNumberInput);
		panelFieldsContacts.add(adress);
		panelFieldsContacts.add(adressInput);
		panelFieldsContacts.add(birth);
		panelFieldsContacts.add(birthInput);
		panelFieldsContacts.add(website);
		panelFieldsContacts.add(websiteInput);
		panelFieldsContacts.add(skype);
		panelFieldsContacts.add(skypeInput);
		panelFieldsContacts.add(company);
		panelFieldsContacts.add(companyInput);
		contactPanel.add(panelFieldsContacts, BorderLayout.CENTER);

return JOptionPane.showConfirmDialog(null, contactPanel,
				"Edit contact", JOptionPane.OK_CANCEL_OPTION);
	}
	
	/**
	 * Gets all fields from contact, which is filled, except list of groups.
	 * List of groups will mix with other fields, thats why it separated
	 * @param contact which was selected
	 * @return map with key-value pair of headline and value from contact fields
	 */
	private Map<String,String> FilledFields(Contact contact){
		HashMap <String,String> map= new HashMap<>();
		
		if(!contact.getSurname().trim().isEmpty()){
			map.put("Surname", contact.getSurname());
		}
		map.put("Cell number", contact.getCellNumber());
		if(!contact.getDate().trim().isEmpty()){
			map.put("Date of birth", contact.getDate());
		}
		if(!contact.getCompany().trim().isEmpty()){
			map.put("Company", contact.getCompany());
		}
		if(!contact.getHomeNumber().trim().isEmpty()){
			map.put("Home number", contact.getHomeNumber());
		}
		if(!contact.getWorkNumber().trim().isEmpty()){
			map.put("Work number", contact.getWorkNumber());
		}
		if(!contact.getEmail().trim().isEmpty()){
			map.put("Email", contact.getEmail());
		}
		if(!contact.getAddress().trim().isEmpty()){
			map.put("Address",contact.getAddress());
		}
		if(!contact.getWebsite().trim().isEmpty()){
			map.put("Website", contact.getWebsite());
		}
		if(!contact.getSkype().trim().isEmpty()){
			map.put("Skype", contact.getSkype());
		}
		
		
		return map;
	}
	
	/**
	 * Gets all groups from the current contact to divide into a separate panel
	 * @param contact, which was selected by the user
	 * @return List of groups, binding with a current contact or null if no group is exist
	 */
	private List<Group> FilledGroups(Contact contact){
		if(contact.getGroups()!=null&&contact.getGroups().size()!=0){
		List<Group> grop=contact.getGroups();
		
		return grop;
		}
		return null;
	}
	
		/**
		 * Getting all data from the contact and fulfilling three panels with information.
		 * First upper panel filled with photo and name contact. Second panel filled with 
		 * all another information about contact, except groups. And finally third panel
		 * filled with groups from the current contact, if they are exist, if no, this panel
		 * will be skipped
		 * @param contact, which was selected by the user to display data
		 */
		public void ShowAllFieldsPanel(Contact contact){
		Map<String, String> visibleFields=FilledFields(contact);
		int numberOfRows= visibleFields.size();
		
		JPanel showFieldsPanel= new JPanel(new BorderLayout());
		JPanel photoAndNamePanel= new JPanel(new GridLayout(1,2,10,0));
		photoAndNamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		JPanel fields= new JPanel(new GridLayout(numberOfRows, 2,10,5));
		
		for (String key  : visibleFields.keySet()) {
			fields.add(new JLabel(key));
			fields.add(new JLabel(visibleFields.get(key)));
		}
		Icon icon= new ImageIcon(contact.getPhotoPath());
		JLabel contactIcon= new JLabel(icon);
		JLabel name= new JLabel(contact.getName());
		photoAndNamePanel.add(contactIcon);
		photoAndNamePanel.add(name);
		name.setFont(new Font("sans-serif", Font.PLAIN, 20));
		showFieldsPanel.add(photoAndNamePanel,BorderLayout.NORTH);
		showFieldsPanel.add(fields,BorderLayout.CENTER);
		
		List<Group> namesGroups=FilledGroups(contact);
		if(namesGroups!=null){
		int numberOfRowsGroups=(namesGroups.size()/2)+1;
		JPanel fieldsGroups= new JPanel(new GridLayout(numberOfRowsGroups, 2,10,5));
		fields.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		JLabel showGroupsLabel= new JLabel("Groups: ");
		showGroupsLabel.setFont(new Font("sans-serif", Font.BOLD, 15));
		fieldsGroups.add(showGroupsLabel);
		for (int i = 0; i < namesGroups.size(); i++) {
			fieldsGroups.add(new JLabel(namesGroups.get(i).getNameGroup()));
		}
		showFieldsPanel.add(fieldsGroups,BorderLayout.SOUTH);}
		JOptionPane.showConfirmDialog(null, showFieldsPanel,
				"Full contact information", JOptionPane.DEFAULT_OPTION);
		
	}

		/**
		 * Opening confirm menu for clear current group from the contacts. Clear mean remove
		 * all relation contacts from group
		 * @param objectThatDelete, name of the group that need to be without any relation
		 * with contacts
		 * @return true if user confirm, false otherwise
		 */
		public boolean confirmGroupClearMenu(String objectThatDelete) {
			JPanel noData = new JPanel(new GridLayout(1, 1));
			JLabel noInput = new JLabel("Are you sure you want to clear all contacts from group "
					+ objectThatDelete + " ?");
			noData.add(noInput);
			if (JOptionPane.showConfirmDialog(null, noData, "Confirm delete",
					JOptionPane.OK_CANCEL_OPTION) == 0) {
				return true;
			}
			return false;
		}

	/**
	 * Opening confirm menu for deleting contacts or group
	 * @param objectThatDelete, title of what will be deleted
	 * @return true if user confirm, false otherwise
	 */
	public boolean confirmMenu(String objectThatDelete) {
		JPanel noData = new JPanel(new GridLayout(1, 1));
		JLabel noInput = new JLabel("Are you sure you want to delete this "
				+ objectThatDelete + " ?");
		noData.add(noInput);
		if (JOptionPane.showConfirmDialog(null, noData, "Confirm delete",
				JOptionPane.OK_CANCEL_OPTION) == 0) {
			return true;
		}
		return false;
	}

}
