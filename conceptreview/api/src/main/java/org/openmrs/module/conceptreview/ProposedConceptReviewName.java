package org.openmrs.module.conceptreview;

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.ConceptNameType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "conceptreview_proposed_concept_review_name")
public class ProposedConceptReviewName extends BaseOpenmrsObject implements Serializable {

  @Id
  @GeneratedValue(generator = "nativeIfNotAssigned")
  @GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
  @Column(name = "conceptreview_review_name_id")
  private Integer proposedConceptReviewNameId;

  @ManyToOne
  @JoinColumn(name = "proposed_concept_review")
  private ProposedConceptReview proposedConceptReview;

  private String name;

  @Enumerated(EnumType.STRING)
  private ConceptNameType type;

  private Locale locale;

  public ProposedConceptReview getProposedConceptReview() {
    return proposedConceptReview;
  }

  public void setProposedConceptReview(ProposedConceptReview proposedConceptReview) {
    this.proposedConceptReview = proposedConceptReview;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ConceptNameType getType() {
    return type;
  }

  public void setType(ConceptNameType type) {
    this.type = type;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  @Override
  public Integer getId() {
    return proposedConceptReviewNameId;
  }

  @Override
  public void setId(Integer id) {
    proposedConceptReviewNameId = id;
  }
}