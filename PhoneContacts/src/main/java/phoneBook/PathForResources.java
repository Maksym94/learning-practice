package phoneBook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * @author Maximka
 * This class determine path of the current location source code. It needed in order to make 
 * sure that folder "resources" is exist and where to save resize pictures. if project is open 
 * outside of jar, folder "resources" will be exist by default and just needed to get location
 * of this folder. But if project open from the jar probably we don't have outside folder 
 * "resources and default picture for contact, which will be setted if user didn't choose a 
 * picture, that's why first detect if folder "resources" exist, that detect if picture 
 * "default.png" also exist. After all this manipulations folder "resources" will be created 
 * outside the jar with the "default.png" picture. The location of saving pictures will be 
 * outside the jar too.
 *
 */
public class PathForResources {
	private String path;

	
	/**
	 * Getter of the current location "resource" folder. For the first request this method call
	 * setPath. For the next requests return same location.
	 * @return absolute path to the picture
	 */
	public String getPath() {
		if (path == null) {
			setPath();
		}
		return path;
	}

	/**
	 * Set the path of the folder "resources". If project open not in jar file, just return
	 * location of this folder. If project open from jar, check for existing folder "resources",
	 * if not exist creating it, then check for existing picture "default.png", if not exist 
	 * get this file from the jar and write into outside folder. Then set this folder as a 
	 * storage of resize pictures, which user will choose for contacts.
	 */
	public void setPath() {
		try {

			String tempPath = getClass().getProtectionDomain().getCodeSource()
					.getLocation().toURI().getPath()
					+ "/resources/";
			if (tempPath.contains(".jar")) {
				String pathToCreate = tempPath.substring(0, tempPath.lastIndexOf(".jar"));
				String newPath = pathToCreate.substring(0, pathToCreate.lastIndexOf("/"));
				String finalPath = newPath + "/resources/";
				File folderResources = new File(finalPath);
				File defaultPicture = new File(finalPath + "default.png");
				if (!folderResources.exists()) {
					folderResources.mkdir();
				}
				if (!defaultPicture.exists()) {
					try {
						InputStream is = PathForResources.this.getClass()
								.getResourceAsStream("/resources/" + "default.png");
						FileOutputStream fos = new FileOutputStream(defaultPicture
								.getAbsolutePath());
						byte buf[] = new byte[4096];
						int r;
						while ((r = is.read(buf)) != -1) {
							fos.write(buf, 0, r);
						}
						fos.flush();
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				path = finalPath;
			}

			else {
				path = tempPath;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
