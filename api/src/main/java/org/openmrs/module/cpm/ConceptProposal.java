package org.openmrs.module.cpm;

import java.util.Date;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Concept;


public class ConceptProposal extends BaseOpenmrsObject {

	Concept concept;
	String description;
	Date creationDate;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	
    public Concept getConcept() {
    	return concept;
    }

	
    public void setConcept(Concept concept) {
    	this.concept = concept;
    }

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
    }

	
    public Date getCreationDate() {
    	return creationDate;
    }

	
    public void setCreationDate(Date creationDate) {
    	this.creationDate = creationDate;
    }
	
}
