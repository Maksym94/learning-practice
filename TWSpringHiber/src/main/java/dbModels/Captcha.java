package dbModels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Maximka
 * This class is binded with table "captcha" from database where contains data about captcha,
 * including name of the captcha photo and text on the photo, which will be compared during
 * registration of a new user. If table does not exist, it will be created automatically
 * through hibernate annotations and configuration,  
 *
 */
@Entity
@Table(name="captcha")
public class Captcha {
	
	@Id
	@Column(name="id_captcha")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idCaptcha;
	
	@Column(name="photo_number")
	private int photoNumber;
	
	@Column(name="text_photo")
	private String textPhoto;
	
	public int getIdCaptcha() {
		return idCaptcha;
	}
	public void setIdCaptcha(int idCaptcha) {
		this.idCaptcha = idCaptcha;
	}
	public int getPhotoNumber() {
		return photoNumber;
	}
	public void setPhotoNumber(int photoNumber) {
		this.photoNumber = photoNumber;
	}
	public String getTextPhoto() {
		return textPhoto;
	}
	public void setTextPhoto(String textPhoto) {
		this.textPhoto = textPhoto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCaptcha;
		result = prime * result + photoNumber;
		result = prime * result + ((textPhoto == null) ? 0 : textPhoto.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Captcha other = (Captcha) obj;
		if (idCaptcha != other.idCaptcha)
			return false;
		if (photoNumber != other.photoNumber)
			return false;
		if (textPhoto == null) {
			if (other.textPhoto != null)
				return false;
		} else if (!textPhoto.equals(other.textPhoto))
			return false;
		return true;
	}
	

}
