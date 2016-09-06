package lib;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	

}
