package org.openmrs.module.cpm.web.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Proposal {
	
	private int id;
	private String title;
	
	public Proposal(String title){
		this.title = title;
	}
	
	@Column
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
			 
}