package phoneBook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class PathForResources {
	private String path;

	public String getPath() {
		if (path == null) {
			setPath();
		}
		return path;
	}

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
