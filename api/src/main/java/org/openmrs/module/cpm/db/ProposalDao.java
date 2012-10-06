package org.openmrs.module.cpm.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.module.cpm.model.Proposal;

public class ProposalDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Proposal> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Proposal").list();
	}

	public Proposal fetch(final Integer id) {
		return (Proposal) sessionFactory.getCurrentSession().get(Proposal.class, id);
	}

	public void persist(final Proposal p) {
		sessionFactory.getCurrentSession().saveOrUpdate(p);
	}

	public void remove(final Proposal p) {
		sessionFactory.getCurrentSession().delete(p);
	}
}
