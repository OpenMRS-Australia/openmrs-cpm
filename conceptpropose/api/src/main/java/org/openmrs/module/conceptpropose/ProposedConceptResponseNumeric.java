package org.openmrs.module.conceptpropose;

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;

@Entity
@Table(name = "cpm_proposed_concept_response_numeric")
public class ProposedConceptResponseNumeric extends BaseOpenmrsObject {

	private Integer id;

	private Double hiAbsolute;

	private Double hiCritical;

	private Double hiNormal;

	private Double lowAbsolute;

	private Double lowCritical;

	private Double lowNormal;

	private String units;

	private Boolean precise = false;

	@Id
	@GeneratedValue(generator = "nativeIfNotAssigned")
	@GenericGenerator(name = "nativeIfNotAssigned", strategy = "org.openmrs.api.db.hibernate.NativeIfNotAssignedIdentityGenerator")
	@Column(name = "cpm_proposed_concept_response_numeric_id")
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "hi_absolute")
	public Double getHiAbsolute() {
		return hiAbsolute;
	}

	public void setHiAbsolute(Double hiAbsolute) {
		this.hiAbsolute = hiAbsolute;
	}

	@Column(name = "hi_critical")
	public Double getHiCritical() {
		return hiCritical;
	}

	public void setHiCritical(Double hiCritical) {
		this.hiCritical = hiCritical;
	}

	@Column(name = "hi_normal")
	public Double getHiNormal() {
		return hiNormal;
	}

	public void setHiNormal(Double hiNormal) {
		this.hiNormal = hiNormal;
	}

	@Column(name = "low_absolute")
	public Double getLowAbsolute() {
		return lowAbsolute;
	}

	public void setLowAbsolute(Double lowAbsolute) {
		this.lowAbsolute = lowAbsolute;
	}

	@Column(name = "low_critical")
	public Double getLowCritical() {
		return lowCritical;
	}

	public void setLowCritical(Double lowCritical) {
		this.lowCritical = lowCritical;
	}

	@Column(name = "low_normal")
	public Double getLowNormal() {
		return lowNormal;
	}

	public void setLowNormal(Double lowNormal) {
		this.lowNormal = lowNormal;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Boolean getPrecise() {
		return precise;
	}

	public void setPrecise(Boolean precise) {
		this.precise = precise;
	}
}
