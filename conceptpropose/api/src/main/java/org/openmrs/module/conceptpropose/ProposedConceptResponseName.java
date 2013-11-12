package org.openmrs.module.conceptpropose;

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.api.ConceptNameType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "cpm_proposed_concept_response_name")
public class ProposedConceptResponseName extends BaseOpenmrsObject implements Serializable {

  @Id
  @GeneratedValue(generator = "nativeIfNotAssigned")
  @GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
  @Column(name = "cpm_response_name_id")
  private Integer proposedConceptResponseNameId;

  @ManyToOne
  @JoinColumn(name = "proposed_concept_response")
  private ProposedConceptResponse proposedConceptResponse;

  private String name;

  @Enumerated(EnumType.STRING)
  private ConceptNameType type;

  private Locale locale;

  public ProposedConceptResponse getProposedConceptResponse() {
    return proposedConceptResponse;
  }

  public void setProposedConceptResponse(ProposedConceptResponse proposedConceptResponse) {
    this.proposedConceptResponse = proposedConceptResponse;
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
    return proposedConceptResponseNameId;
  }

  @Override
  public void setId(Integer id) {
    proposedConceptResponseNameId = id;
  }
}