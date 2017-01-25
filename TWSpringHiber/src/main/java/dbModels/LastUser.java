package dbModels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
 
/**
 * @author Maximka
 * This class is used to return last inputed login on the site. As a storage uses HashMap and
 * serialize it on the disk.
 *
 */
public class LastUser {
	private String lastLogin;
	private LinkedHashMap<String, String> map;
	
	private File f;


	/**
	 * For the first invoke will create file, which will be used for serializing HashMap and 
	 * initialize empty HashMap. HashMap stores data about last login. For the next invokes
	 * deserialize HashMap from the file.  
	 */
	@SuppressWarnings("unchecked")
	public LastUser() {
		System.out.println("bull.shit");
		f = new File(getClass().getResource("").getPath()+"bull.shit");
		if (f.exists() && f.length() != 0) {
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(f));

				map = (LinkedHashMap<String, String>) ois.readObject();
				ois.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			map = new LinkedHashMap<>();
			map.put("", "");
		}
	}

	/**
	 * Adding new login to be the last login of input. If login is already exists rewrite it 
	 * with new index
	 * @param login last inputed login
	 * @param password which matches current login
	 */
	public void addLoginPass(String login, String password) {
		if(map.containsKey(login)){
				map.remove(login);}
			
		
		map.put(login, password);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(f));
			oos.writeObject(map);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Setter of last login
	 * @param lastLogin last login
	 */
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}


	/**
	 * @return last login from HashMap
	 */
	public String getLastLogin() {
		setLastLogin(map.keySet().toArray()[map.size()-1].toString());
		return lastLogin;
	}
}
