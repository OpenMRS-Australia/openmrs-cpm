package org.openmrs.module.cpm;

import org.hibernate.annotations.GenericGenerator;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Double getHiAbsolute() {
		return hiAbsolute;
	}

	public void setHiAbsolute(Double hiAbsolute) {
		this.hiAbsolute = hiAbsolute;
	}

	public Double getHiCritical() {
		return hiCritical;
	}

	public void setHiCritical(Double hiCritical) {
		this.hiCritical = hiCritical;
	}

	public Double getHiNormal() {
		return hiNormal;
	}

	public void setHiNormal(Double hiNormal) {
		this.hiNormal = hiNormal;
	}

	public Double getLowAbsolute() {
		return lowAbsolute;
	}

	public void setLowAbsolute(Double lowAbsolute) {
		this.lowAbsolute = lowAbsolute;
	}

	public Double getLowCritical() {
		return lowCritical;
	}

	public void setLowCritical(Double lowCritical) {
		this.lowCritical = lowCritical;
	}

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
