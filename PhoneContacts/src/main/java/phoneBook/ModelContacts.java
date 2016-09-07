package phoneBook;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;



public class ModelContacts extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] columns = { "Id", "First name",
			"Second name", "Phone number", "Address" };
	private static final Class<?>[] columnClasses = { Integer.class, String.class,
			String.class, String.class, String.class };
	private List<Contact> listContacts = null;

	public ModelContacts(List<Contact> list) {
		
		listContacts = new ArrayList<>();
		loadData(list);
		System.out.println(listContacts.size());
		
	}

	
	public void loadData(List<Contact> listContacts) {
		
		this.listContacts.clear();
		this.listContacts.addAll(listContacts);
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {

		return listContacts.size();
	}


	public void addNewContact(List<Contact> conts) {
	
		loadData(conts); 
		fireTableRowsInserted(getRowCount()-1,getRowCount()-1);
		
		
	}
	
	public int[] DBIndexes(int[] selectedRows){
		int finalIndex[]=new int[selectedRows.length]; 
		for (int i = 0; i < selectedRows.length; i++) {
		finalIndex[i]=	listContacts.get(selectedRows[i]).getId();
		}
		return finalIndex;
	}
	
	public int DBIndex(int selectedRow){
		int finalIndex=listContacts.get(selectedRow).getId();
		return finalIndex;
	}
	
	public String getColumnName(int column) {
		 return columns[column];
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];

	}

	@Override
	public int getColumnCount() {

		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Contact contact = listContacts.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return rowIndex+1;
		case 1:
			return contact.getName();
		case 2:
			return contact.getSurname();
		case 3:
			return contact.getCellNumber();
		case 4:
			return contact.getAddress();

		}
		return "";
	}


	public Contact getContact(int getCurrentRow) {
		if(getRowCount()>0){
		return listContacts.get(getCurrentRow);
		}
		return new Contact();
	}

	

	}
