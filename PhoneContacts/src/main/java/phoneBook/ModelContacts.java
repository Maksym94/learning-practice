package phoneBook;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Maximka Building model of table, which will be filled by contacts and
 *         some of their fields.
 */
public class ModelContacts extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creating columns for table. Id is not a real id from the database. This
	 * id depend on quantity of the contacts.
	 */
	private static final String[] columns = { "Id", "First name", "Second name",
			"Phone number", "Address" };
	private static final Class<?>[] columnClasses = { Integer.class, String.class,
			String.class, String.class,String.class };

	private List<Contact> listContacts = null;

	/**
	 * Constructor of the class, which filling list of existing contacts to
	 * local list
	 * 
	 * @param list
	 *            of existing contacts
	 */
	public ModelContacts(List<Contact> list) {

		listContacts = new ArrayList<>();
		loadData(list);
	}

	/**
	 * Refresh list of existing contacts to avoid breeding contacts that is
	 * already displayed in a table
	 * 
	 * @param listContacts,
	 *            that exist in database
	 */
	public void loadData(List<Contact> listContacts) {

		this.listContacts.clear();
		this.listContacts.addAll(listContacts);
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {

		return listContacts.size();
	}

	/**
	 * Inserting new contact in table
	 * 
	 * @param contacts,
	 *            list of existing contacts
	 */
	public void addNewContact(List<Contact> contacts) {

		loadData(contacts);
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	/**
	 * @param selectedRows,
	 *            rows from the table which were selected
	 * @return array of indexes from contacts, that according indexes in
	 *         database
	 */
	public int[] DBIndexes(int[] selectedRows) {
		int finalIndex[] = new int[selectedRows.length];
		for (int i = 0; i < selectedRows.length; i++) {
			finalIndex[i] = listContacts.get(selectedRows[i]).getId();
		}
		return finalIndex;
	}

	/**
	 * @param selectedRow,
	 *            row from the table which was selected
	 * @return an integer from contact, that according index in database
	 */
	public int DBIndex(int selectedRow) {
		int finalIndex = listContacts.get(selectedRow).getId();
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
			return rowIndex + 1;
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

	/**
	 * Getter of the contact in a table
	 * @param getCurrentRow, current row from the table
	 * @return contact from selected row
	 */
	public Contact getContact(int getCurrentRow) {
		if (getRowCount() > 0) {
			return listContacts.get(getCurrentRow);
		}
		return new Contact();
	}
}
