package org.openmrs.module.cpm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;

/**
 * This is the base class underlying the exchange of comments concerning Concept Proposals and
 * Concept Proposal Packages by both Concept proposers, and Concept Proposal reviewers. The
 * attributes modelled in the abstract class are the ones that will be exchanged between the two
 * using transfer REST services
 */
@Entity
@Table(name = "cpm_comment")
public class Comment extends BaseOpenmrsObject {
	
	private static final Log log = LogFactory.getLog(Comment.class);
	
	private Integer commentId;
	private String text;
	private CommenterRole commenterRole;

    @Id
    @Column(name = "cpm_comment_id")
    @Transient
    public Integer getId() {
		return this.commentId;
	}
	
	public void setId(Integer id) {
		this.commentId = id;
	}

    @Column(name = "uuid", unique = true, nullable = false, length = 38)
    @Override
    public String getUuid() {
        return super.getUuid();
    }

    public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

    public CommenterRole getCommenterRole() {
		return commenterRole;
	}
	
	public void setCommenterRole(CommenterRole commenterRole) {
		this.commenterRole = commenterRole;
	}

    @Override
    public String toString() {
        return "Comment{" +
                super.toString() +
                ", commentId=" + commentId +
                ", text='" + text + '\'' +
                ", commenterRole=" + commenterRole +
                '}';
    }
}
