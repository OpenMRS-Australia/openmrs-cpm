package org.openmrs.module.cpm.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.api.db.ProposedConceptDAO;
import org.openmrs.module.cpm.api.db.ProposedConceptPackageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Hibernate specific database methods for {@link org.openmrs.module.cpm.api.db.ProposedConceptDAO}
 */
@Repository
public class HibernateProposedConceptDAO implements ProposedConceptDAO {

	private static Log log = LogFactory.getLog(HibernateProposedConceptDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void deleteProposedConcept(ProposedConcept proposedConcept) {
		if (proposedConcept != null) {
			sessionFactory.getCurrentSession().delete(proposedConcept);
		} else {
			log.warn("Attempting to delete null ProposedConcept");
		}
	}

}
