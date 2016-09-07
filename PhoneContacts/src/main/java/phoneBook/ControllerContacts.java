package phoneBook;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class ControllerContacts implements ActionListener{
	private ViewContacts view;
	private ModelContacts model;
	private ConsolePhoneBookInterface dao;
	private PathForResources pathResources;
    private int currentRow;
    private int[] SelectedRows;
    private String inputtedPathPhoto;
    int	finalIndexes[];
    int finalIndex;
	
	public static final String ADD_CONTACT = "ADD_CONTACT";
	public static final String ADD_GROUP = "ADD_GROUP";
	public static final String SEARCH = "SEARCH";
	public static final String LIST_OF_CONTACTS = "LIST_OF_CONTACTS";
	public static final String LIST_OF_GROUPS = "LIST_OF_GROUPS";
	public static final String CHOOSE_PHOTO = "CHOOSE_PHOTO";
	public static final String SAVE_CONTACT = "SAVE_CONTACT_SHORT";
	public static final String SAVE_GROUP = "SAVE_GROUP";
	public static final String SEARCH_BUTTON = "SEARCH_BUTTON";
	public static final String SHOW_ALL_FIELDS = "SHOW_ALL_FIELDS";
	public static final String EDIT_CONTACT = "EDIT_CONTACT";
	public static final String DELETE_CONTACT = "DELETE_CONTACT";
	public static final String ALL_GROUPS = "ALL_GROUPS";
	private static final String empty_contact_fields="Fields name and cell number can't be empty";
	private static final String empty_search_input="Input for searching can't be empty";
	private static final String no_search_information = "No contact information are found";

	public ControllerContacts(ViewContacts view, ModelContacts model,ConsolePhoneBookInterface dao) {
		this.view = view;
		this.model = model;
		view.addButtonListener(this);
		
		this.dao = dao;
		pathResources = new PathForResources();
           view.getTable().addMouseListener(new MyMouseAdapter());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String result = e.getActionCommand();
		System.out.println(result);
		switch (result) {
		case ADD_CONTACT:
			inputtedPathPhoto=null;
			int res = view.addNewContactPanel();
			
			if (res == 0&&checkFields(view.nameInput.getText(),
					view.cellNumberInput.getText())){
				
				
					view.setPathForEdit(pathResources.getPath()+"/default.png");
				 
				if(inputtedPathPhoto!=null){
view.setPathForEdit(inputtedPathPhoto);}
				setNewContact(getDatafromView(view));
				
				}
			else if(res==0){view.emptyFields(empty_contact_fields);}
			break;
	/*	case ADD_GROUP:
			view.addNewGroupPanel();
			//createProup(view.nameGroupInput.getText());
			break;*/
		case SEARCH:
			int resultSearchButton=view.searchPanel();
			String inputtedText=view.searchInput.getText().trim();
			if( resultSearchButton==0&&!inputtedText.isEmpty()){
				List<Contact> foundContacts= dao.searchContact(inputtedText);
				if(foundContacts.size()!=0){
					model.loadData(foundContacts);
				}
				else{
					view.noFoundContactsDialog(no_search_information);
				}
			}
			else if(resultSearchButton==0){
				view.emptyFields(empty_search_input);
			}
			
			break;
		case LIST_OF_CONTACTS:

			break;
		case LIST_OF_GROUPS:

			break;
		case CHOOSE_PHOTO:
			inputtedPathPhoto= view.setPath();
			break;
		case SHOW_ALL_FIELDS:
			view.ShowAllFieldsPanel(model.getContact(currentRow));

			break;
		case ALL_GROUPS:
			model.loadData(dao.showAllContacts());
			break;
		case EDIT_CONTACT:
editContact(currentRow);
			break;
		case DELETE_CONTACT:
deleteContact(SelectedRows);


			break;

		default:
			break;
		}

	}
	/*private void createProup(String text) {
		
		
	}
*/
	private void editContact(int gotCurrentRow) {
	Contact beforeChange=	model.getContact(gotCurrentRow);
	int pushedButton= view.editContactPanel(beforeChange);
	if(pushedButton==0){
		Contact afterChange=getDatafromView(view);
		String supposePicturePathForDelete=beforeChange.getPhotoPath();
		String newPicturePath=afterChange.getPhotoPath();
		
			if(!supposePicturePathForDelete.equals(pathResources.getPath()+"/default.png")
					&&!supposePicturePathForDelete.equals(newPicturePath)){
				new File(supposePicturePathForDelete).delete();
			}
		
	dao.editContact(afterChange);
	model.addNewContact(dao.showAllContacts());}
	}

	private void deleteContact( int[]SelectedRows) {
		String objectName;
		if(SelectedRows.length>1){objectName=SelectedRows.length+ " contacts";}
		else{ objectName="contact";}
		if (view.confirmMenu(objectName)){
		originalIndexes();
		for (int i = 0; i < finalIndexes.length; i++) {
		String	pictureForDelete	=dao.getContactPhotoPath(finalIndexes[i]);
			System.out.println(dao.getContactPhotoPath(finalIndexes[i]));
			dao.removeContact(finalIndexes[i]);
			
			
			
			
				if(!pictureForDelete.equals(pathResources.getPath()+"/default.png")){
					System.out.println("picture that will be deleted "+pictureForDelete);
					new File(pictureForDelete).delete();
					
				}
			 
			
		}
	model.loadData(dao.showAllContacts());	}
	}

	int[] originalIndexes() {
	 return	finalIndexes= model.DBIndexes(SelectedRows);
	}
	
	Contact originalIndex(){
		return model.getContact(currentRow);
	}

	private boolean checkFields(String name, String cellNumber){
		
		if(name.trim().isEmpty()||cellNumber.trim().isEmpty()){
			return false;
		}
		return true;
	}
	private String getPathForPicture(String path) throws URISyntaxException {
		try {
			if (path != null&&!view.getPath().startsWith(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+"/resources/default.png")) {
				ResizePicture rp = new ResizePicture();
				
				 return rp.getNewImageLocation(path,pathResources.getPath(), model.getContact(model.getRowCount() - 1).getId() + 1);

			}
			else if(path != null){
				return path;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathResources.getPath()+"/default.png";
	}

	private Contact getDatafromView(ViewContacts view2) {
		this.view = view2;
		Contact cont = new Contact();
		cont.setName(view.nameInput.getText().trim());
		cont.setSurname(view.surnameInput.getText().trim());
		cont.setCellNumber(view.cellNumberInput.getText().trim());
		try {
			cont.setPhotoPath(getPathForPicture(view.getPath()));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cont.setWorkNumber(view.workNumberInput.getText().trim());
		cont.setEmail(view.emailInput.getText().trim());
		cont.setHomeNumber(view.homeNumberInput.getText().trim());
		cont.setAddress(view.adressInput.getText().trim());
		cont.setDate(view.birthInput.getText().trim());
		cont.setWebsite(view.websiteInput.getText().trim());
		cont.setSkype(view.skypeInput.getText().trim());
		cont.setCompany(view.companyInput.getText().trim());
		cont.setId(view.dataBaseID);
		cont.setGroups(view.groupsA);
		return cont;

	}

	private void setNewContact(Contact cont) {
		dao.addNewContact(cont);
		model.addNewContact(dao.showAllContacts());
	}
	


	private class MyMouseAdapter extends MouseAdapter {
		
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Point point=null;
			int[] rows=null;
			int row;
			JTable localTable = (JTable) e.getSource();
				point = new Point();
				point.setLocation(e.getPoint());
				row = localTable.rowAtPoint(point);
				
				if (!localTable.isRowSelected(row)) {
					localTable.changeSelection(row, row, false, false);
				} 
				
				rows = localTable.getSelectedRows();
				currentRow=row;
				SelectedRows=rows;
				
				if (e.getButton() == MouseEvent.BUTTON3) {
				JPopupMenu popup = view.createContactPopUp();
				popup.show(e.getComponent(), e.getX(), e.getY());}

				
		}
		
		
	}}

			
		
		
		       
		
		
		

