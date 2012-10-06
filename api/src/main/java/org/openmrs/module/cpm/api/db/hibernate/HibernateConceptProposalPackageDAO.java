package org.openmrs.module.cpm.api.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.openmrs.module.cpm.ConceptProposalPackage;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Hibernate specific database methods for {@link ConceptProposalPackageDAO}
 */
@Repository
public class HibernateConceptProposalPackageDAO implements ConceptProposalPackageDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.db.MetadataSharingDAO#getLatestExportedPackage(String)
	 */
	@Override
	public List<ConceptProposalPackage> getAllConceptProposalPackages() {
		@SuppressWarnings("unchecked")
        List<ConceptProposalPackage> result = (List<ConceptProposalPackage>) sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackage").list();
		if (Log.isInfoEnabled()) {
			Log.info("getAllConceptProposalPackages returned: " + result.size() + " results");
		}
		return result;
	}
	
}
