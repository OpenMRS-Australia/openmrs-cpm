package org.openmrs.module.conceptpropose;


import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.openmrs.Concept;

import javax.persistence.*;
import java.util.List;

//public class ProposedConceptComment extends ShareableProposal<ProposedConceptPackage> {


@Entity
@Table(name = "conceptpropose_proposed_concept_comment")
public class ProposedConceptComment extends BaseOpenmrsObject {

	private Integer proposedConceptCommentId;

	private ProposedConcept proposedConcept;

	private String name;
	private String email;
	private String comment;
	private Date dateCreated;

	public ProposedConceptComment()
	{
		dateCreated = new Date();
	}
	public ProposedConceptComment(String name, String email, String comment)
	{
		dateCreated = new Date();
		this.name = name;
		this.email = email;
		this.comment = comment;
	}
	public ProposedConceptComment(String name, String email, String comment, Date dateCreated)
	{
		this.name = name;
		this.email = email;
		this.comment = comment;
		this.dateCreated = dateCreated;
	}

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "conceptpropose_proposed_concept_comment_id")
	@Override
	public Integer getId() {
		return proposedConceptCommentId;
	}

	@Override
	public void setId(Integer id) {
		this.proposedConceptCommentId = id;
	}

	@ManyToOne
	@JoinColumn(name = "conceptpropose_proposed_concept_id")
	public ProposedConcept getProposedConcept() {
		return proposedConcept;
	}

	public void setProposedConcept(ProposedConcept proposedConcept) {
		this.proposedConcept = proposedConcept;
	}


	@Column(name = "date_created", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "ProposedConceptComment{" +
				"ProposedConceptCommentId=" + proposedConceptCommentId +
				", name='" + name + '\'' +
				", email=" + email +
				", comment='" + comment + '\'' +
				'}';
	}
}
