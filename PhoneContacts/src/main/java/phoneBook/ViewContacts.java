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

public class ViewContacts extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel contactPanel;
	private JPanel panelFieldsContacts;
	private JPanel groupsPanel;
	private JButton add_contact;
	private JButton search;
	private JButton allGroupsButton;
	private JPanel buttonsPanel;
	private JTable table;
	private JButton add_group;
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
	private MouseListener ml;
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
	JLabel nameContact = new JLabel("Name");
	JLabel surnameContact = new JLabel("Surname");
	JLabel cellNumber = new JLabel("Cell number");
	JLabel choosePhoto = new JLabel("Choose Photo");
	JLabel workNumber = new JLabel("Work number");
	JLabel email = new JLabel("Email");
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
		/*ImageIcon listContactImage = null;
		ImageIcon listGroupImage = null;*/
		Image im=null;
		try {
			im = ImageIO.read(getClass().getResource("/resources/icon.png"));
			
			newContactImage = new ImageIcon(ImageIO.read(getClass()
					.getResource("/resources/add_new_contact.png")));
			newGroupImage = new ImageIcon(ImageIO.read(getClass().getResource(
					"/resources/add_new_group.png")));
			searchImage = new ImageIcon(ImageIO.read(getClass().getResource(
					"/resources/search.png")));
			/*listContactImage = new ImageIcon(ImageIO.read(getClass()
					.getResource("/resources/list_of_contacts.png")));
			listGroupImage = new ImageIcon(ImageIO.read(getClass().getResource(
					"/resources/list_of_groups.png")));*/
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		frame.setIconImage(im);
		
		add_contact = new JButton(newContactImage);
		add_contact.setText("Add conntact");
		add_contact.setActionCommand(ControllerContacts.ADD_CONTACT);
		add_group = new JButton(newGroupImage);
		add_group.setText("Add group");
		add_group.setActionCommand(ControllerGroups.ADD_GROUP);
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
		buttonsPanel.add(add_contact);
		buttonsPanel.add(add_group);
		buttonsPanel.add(search);
		/*buttonsPanel.add(listContacts);
		buttonsPanel.add(listGroups);*/
		
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

	public int addNewContactPanel() {
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
		panelFieldsContacts = new JPanel(new GridLayout(12, 2, 0, 4));
		panelFieldsContacts.add(nameContact);
		panelFieldsContacts.add(nameInput);
		panelFieldsContacts.add(surnameContact);
		panelFieldsContacts.add(surnameInput);
		panelFieldsContacts.add(cellNumber);
		panelFieldsContacts.add(cellNumberInput);
		panelFieldsContacts.add(choosePhoto);
		panelFieldsContacts.add(choosePhotoButton);
		panelFieldsContacts.add(workNumber);
		panelFieldsContacts.add(workNumberInput);
		panelFieldsContacts.add(email);
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
				"Add new contact", JOptionPane.OK_CANCEL_OPTION);
	}


	public JTable getTable() {
		return table;
	}

	public String setPath() {
		//path = null;
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
	public String getPath() {
		System.out.println("Path ---- "+path);
	
		return path;
	}
	public void setPathForEdit(String path) {
		this.path = path;
	}


	public int addNewGroupPanel() {
		JPanel groupPan = new JPanel(new BorderLayout());
		JLabel nameGroup = new JLabel("Name");
		nameGroupInput = new JTextField();
       
		JPanel panelFieldsGroup = new JPanel(new GridLayout(2, 1));
		panelFieldsGroup.add(nameGroup);
		panelFieldsGroup.add(nameGroupInput);
		groupPan.add(panelFieldsGroup, BorderLayout.CENTER);
	return	JOptionPane.showConfirmDialog(null, groupPan, "Add new group",JOptionPane.OK_CANCEL_OPTION);
		
	}
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
	
	public void setGroups(List<String> groups){
  groupsPanel.removeAll();
  groupsPanel.add(groupLabel);
  groupsPanel.add(allGroupsButton);
			for (int i = 0; i < groups.size(); i++) {
				
			JButton b1= new JButton(groups.get(i));
			b1.setActionCommand(groups.get(i));
			b1.addActionListener(groupListener);
			b1.addMouseListener(ml);
			groupsPanel.add(b1);
		}
			groupsPanel.repaint();
			frame.revalidate();
	}
	public void setGroup(String nameOfGroup){
		JButton newGrp= new JButton(nameOfGroup);
		newGrp.setActionCommand(nameOfGroup);
		newGrp.addActionListener(groupListener);
		newGrp.addMouseListener(ml);
		groupsPanel.add(newGrp);
		frame.revalidate();
		
	}
	public void addGroupsButtonMouseListener(MouseListener ml){
		addToGroupItem.addMouseListener(ml);
		deleteFromGropItem.addMouseListener(ml);
		this.ml=ml;
	}

	public int searchPanel() {
		JPanel searchPan = new JPanel(new BorderLayout());
		JLabel searchInfo = new JLabel("Enter any contact information");
		searchInput = new JTextField();

		JPanel panelFieldsSearch = new JPanel(new GridLayout(1, 2, 2, 2));
		JPanel panelButtonsSearch = new JPanel(new GridLayout(1, 1, 2, 2));
		panelFieldsSearch.add(searchInfo);
		panelFieldsSearch.add(searchInput);
		searchPan.add(panelFieldsSearch, BorderLayout.CENTER);
		searchPan.add(panelButtonsSearch, BorderLayout.SOUTH);
		return JOptionPane.showConfirmDialog(null, searchPan, "Search",JOptionPane.OK_CANCEL_OPTION);
	}
	
	public void emptyFields(String argument) {
		JPanel noData= new JPanel(new GridLayout(1, 1));
		JLabel noInput= new JLabel(argument);
		noData.add(noInput);
		 JOptionPane.showConfirmDialog(null, noData,
				"Empty fields", JOptionPane.CLOSED_OPTION);
		
	}
	public void noFoundContactsDialog(String argument) {
		JPanel noData= new JPanel(new GridLayout(1, 1));
		JLabel noInput= new JLabel(argument);
		noData.add(noInput);
		 JOptionPane.showConfirmDialog(null, noData,
				"Void search information", JOptionPane.CLOSED_OPTION);
		
	}

	public void addButtonListener(ActionListener al) {
		
		add_contact.addActionListener(al);
		search.addActionListener(al);
		/*listContacts.addActionListener(al);
		listGroups.addActionListener(al);*/
		choosePhotoButton.addActionListener(al);
		saveGroup.addActionListener(al);
		allFieldsItem.addActionListener(al);
		editContactItem.addActionListener(al);
		deleteContactItem.addActionListener(al);
		allGroupsButton.addActionListener(al);
	}
	public void addToGroupContactListener(MouseListener mlist){
		addToGroupItem.addMouseListener(mlist);
	}
	public void addGroupButtonListener(ActionListener actList){
		groupListener=actList;
		add_group.addActionListener(actList);
		 
		 deleteFromGropItem.addActionListener(actList);
		renameGroupItem.addActionListener(actList);
		clearListGroupItem.addActionListener(actList);
		deleteGroupItem.addActionListener(actList);
	}
	
	public JMenu drawGroupListPopUp(List<String> listGroups){
		 JMenu listMenuGroup= new JMenu();
		 for (int i = 0; i < listGroups.size(); i++) {
			JMenuItem item = new JMenuItem(listGroups.get(i));
			listMenuGroup.add(item);
		}
		 return listMenuGroup;
	}

	public JPopupMenu createContactPopUp() {
		JPopupMenu contactPopUpMenu = new JPopupMenu();
		contactPopUpMenu.add(allFieldsItem);
		contactPopUpMenu.add(editContactItem);
		contactPopUpMenu.add(addToGroupItem);
		contactPopUpMenu.add(deleteFromGropItem);
		contactPopUpMenu.add(deleteContactItem);
		return contactPopUpMenu;
	}
	
	public JPopupMenu createGroupPopUp() {
		JPopupMenu groupPopUpMenu = new JPopupMenu();
		groupPopUpMenu.add(renameGroupItem);
		groupPopUpMenu.add(clearListGroupItem);
		groupPopUpMenu.add(deleteGroupItem);
		return groupPopUpMenu;
	}

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
		panelFieldsContacts.add(nameContact);
		panelFieldsContacts.add(nameInput);
		panelFieldsContacts.add(surnameContact);
		panelFieldsContacts.add(surnameInput);
		panelFieldsContacts.add(cellNumber);
		panelFieldsContacts.add(cellNumberInput);
		panelFieldsContacts.add(choosePhoto);
		panelFieldsContacts.add(choosePhotoButton);
		panelFieldsContacts.add(workNumber);
		panelFieldsContacts.add(workNumberInput);
		panelFieldsContacts.add(email);
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
	private HashMap<String,String> FilledFields(Contact cont){
		HashMap <String,String> map= new HashMap<>();
		
		if(!cont.getSurname().trim().isEmpty()){
			map.put("Surname", cont.getSurname());
		}
		map.put("Cell number", cont.getCellNumber());
		if(!cont.getDate().trim().isEmpty()){
			map.put("Date of birth", cont.getDate());
		}
		if(!cont.getCompany().trim().isEmpty()){
			map.put("Company", cont.getCompany());
		}
		if(!cont.getHomeNumber().trim().isEmpty()){
			map.put("Home number", cont.getHomeNumber());
		}
		if(!cont.getWorkNumber().trim().isEmpty()){
			map.put("Work number", cont.getWorkNumber());
		}
		if(!cont.getEmail().trim().isEmpty()){
			map.put("Email", cont.getEmail());
		}
		if(!cont.getAddress().trim().isEmpty()){
			map.put("Address",cont.getAddress());
		}
		if(!cont.getWebsite().trim().isEmpty()){
			map.put("Website", cont.getWebsite());
		}
		if(!cont.getSkype().trim().isEmpty()){
			map.put("Skype", cont.getSkype());
		}
		
		
		return map;
	}
	
	private List<Group> FilledGroups(Contact contact){
		if(contact.getGroups()!=null&&contact.getGroups().size()!=0){
		List<Group> grop=contact.getGroups();
		
		return grop;
		}
		return null;
	}
		public void ShowAllFieldsPanel(Contact contact){
		HashMap<String, String> visibleFields=FilledFields(contact);
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
		//fieldsGroups.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0), BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY)));
		//fieldsGroups.setBorder(BorderFactory.createTitledBorder("Groups"));
		//fieldsGroups.setBorder(BorderFactory.);
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
