package org.openmrs.module.conceptpropose;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

/**
 * This is the base class underlying the exchange of comments concerning Concept Proposals and
 * Concept Proposal Packages by both Concept proposers, and Concept Proposal reviewers. The
 * attributes modelled in the abstract class are the ones that will be exchanged between the two
 * using transfer REST services
 */
public class ShareableComment extends BaseOpenmrsObject {
	
	private static final Log log = LogFactory.getLog(ShareableComment.class);
	
	private Integer conceptProposalCommentId;
	private String comment;
	private ProposalRole commenterRole;
	
	public Integer getId() {
		return this.conceptProposalCommentId;
	}
	
	public void setId(Integer id) {
		this.conceptProposalCommentId = id;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public ProposalRole getCommenterRole() {
		return commenterRole;
	}
	
	public void setCommenterRole(ProposalRole commenterRole) {
		this.commenterRole = commenterRole;
	}
	
}
