package org.openmrs.module.conceptpropose.web.dto;

public class Settings {

	private String url;

	private String username;

	private String password;
	
	private boolean urlInvalid;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	
	public boolean getUrlInvalid() {
		return urlInvalid;
	}
	
	public void setUrlInvalid(boolean urlInvalid) {
		this.urlInvalid = urlInvalid;
	}
}
