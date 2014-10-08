package org.openmrs.module.conceptreview;

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conceptreview_proposed_concept_review_comment")
public class ProposedConceptReviewComment extends BaseOpenmrsObject {

	private Integer proposedConceptReviewCommentId;

	private ProposedConceptReview proposedConceptReview;


	private String name;
	private String email;
	private String comment;
	private Date dateCreated;

	public ProposedConceptReviewComment()
	{
		dateCreated = new Date();
	}
	public ProposedConceptReviewComment(String name, String email, String comment)
	{
		dateCreated = new Date();
		this.name = name;
		this.email = email;
		this.comment = comment;
	}

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "conceptreview_proposed_concept_review_comment_id")
	@Override
	public Integer getId() {
		return proposedConceptReviewCommentId;
	}

	@Override
	public void setId(Integer id) {
		this.proposedConceptReviewCommentId = id;
	}

	@ManyToOne
	@JoinColumn(name = "proposed_concept_review")
	public ProposedConceptReview getProposedConceptReview() {
		return proposedConceptReview;
	}

	public void setProposedConceptReview(ProposedConceptReview proposedConceptReview) {
		this.proposedConceptReview = proposedConceptReview;
	}


	@Column(name = "date_created", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
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
		return "ProposedConceptReviewComment{" +
				"ProposedConceptReviewCommentId=" + proposedConceptReviewCommentId +
				", name='" + name + '\'' +
				", email=" + email +
				", dateCreated=" + dateCreated +
				", comment='" + comment + '\'' +
				'}';
	}
}