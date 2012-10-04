package org.openmrs.module.cpm.db;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

public class ProposalDao {

	@Autowired
	private EntityManager entityManager;

	public List<Proposal> findAll() {
		return entityManager.createQuery("select p from Proposal", Proposal.class).getResultList();
	}

	public Proposal fetch(final Long id) {
		return entityManager.find(Proposal.class, id);
	}

	public void persist(final Proposal p) {
		// wat do?
	}

	public void remove(final Proposal p) {
		entityManager.remove(p);
	}
}
