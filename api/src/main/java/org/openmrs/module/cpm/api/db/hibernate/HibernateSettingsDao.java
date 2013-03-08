package org.openmrs.module.cpm.api.db.hibernate;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.cpm.Settings;
import org.openmrs.module.cpm.api.db.SettingsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateSettingsDao implements SettingsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Settings get() {
		final Query query = sessionFactory.getCurrentSession().createQuery("from Settings").setMaxResults(1);
		return (Settings) query.uniqueResult();
	}

	@Override
	public void update(final Settings settings) {
		sessionFactory.getCurrentSession().saveOrUpdate(settings);
	}
}
