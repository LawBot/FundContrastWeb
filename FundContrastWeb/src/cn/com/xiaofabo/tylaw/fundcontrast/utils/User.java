package cn.com.xiaofabo.tylaw.fundcontrast.utils;

import org.springframework.web.multipart.MultipartFile;

public class User {
	private String username;
	private MultipartFile image;

	public User() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
