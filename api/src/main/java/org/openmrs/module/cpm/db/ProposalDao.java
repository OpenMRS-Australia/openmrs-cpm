package org.openmrs.module.cpm.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
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

	public Integer persist(final Proposal p) {
		// Can't get this to work
//		final Session currentSession = sessionFactory.getCurrentSession();
//		final Proposal mergedEntity = (Proposal) currentSession.merge(p);
//		currentSession.evict(mergedEntity);
//		currentSession.saveOrUpdate(mergedEntity);
//		currentSession.flush();

		final Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(p);
		session.getTransaction().commit();
		session.close();

		return p.getId();
	}

	public void remove(final Proposal p) {
//		sessionFactory.getCurrentSession().delete(p);

		final Session sess = sessionFactory.openSession();
		sess.beginTransaction();
		sess.delete(p);
		sess.getTransaction().commit();
		sess.close();

	}
}
