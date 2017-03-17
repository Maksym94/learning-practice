package dbModels;

import java.util.List;

public interface AdminDataBase {
	
	public List<UserAccount> getAllUsers();
	public void editUser(UserAccount user);
	public void deleteUser(UserAccount user);
	public UserAccount getUserById(int index);
	public void deleteOperation(int index);
	public Account findOperatonById(int index);
	public void editOperaton(Account account);

}
