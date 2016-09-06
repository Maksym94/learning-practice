package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
 
public class LastUser {
	private String lastLogin;
	private LinkedHashMap<String, String> map;
	
	private File f;


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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map = new LinkedHashMap<>();
			map.put("", "");
		}
	}

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
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}


	public String getLastLogin() {
		setLastLogin(map.keySet().toArray()[map.size()-1].toString());
		return lastLogin;
	}
}
