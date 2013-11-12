package org.openmrs.module.conceptpropose.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.api.db.ProposedConceptPackageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate specific database methods for {@link ProposedConceptPackageDAO}
 */
@Repository
public class HibernateProposedConceptPackageDAO implements ProposedConceptPackageDAO {

	private static Log log = LogFactory.getLog(HibernateProposedConceptPackageDAO.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getAllConceptProposalPackages()
	 */
	@Override
	public List<ProposedConceptPackage> getAllConceptProposalPackages() {
		@SuppressWarnings("unchecked")
        List<ProposedConceptPackage> result = (List<ProposedConceptPackage>) sessionFactory.getCurrentSession().createQuery("from ProposedConceptPackage").list();
		if (log.isDebugEnabled()) {
			log.debug("getAllConceptProposalPackages returned: " + result.size() + " results");
		}
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getConceptProposalPackageById(Integer id)
	 */
	@Override
	public ProposedConceptPackage getConceptProposalPackageById(Integer id) {
		if (id != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ProposedConceptPackage package where package.id = :id");
			query.setInteger("id", id);
			ProposedConceptPackage result = (ProposedConceptPackage) query.uniqueResult();
			if (log.isDebugEnabled()) { log.debug("getConceptProposalPackageById returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get package with null id");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getConceptProposalPackageByUuid(Integer uuid)
	 */
	@Override
	public ProposedConceptPackage getConceptProposalPackageByUuid(String uuid) {
		if (uuid != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ProposedConceptPackage package where package.uuid = :uuid");
			query.setString("uuid", uuid);
			ProposedConceptPackage result = (ProposedConceptPackage) query.uniqueResult();
			if (log.isDebugEnabled()) { log.debug("getConceptProposalPackageByUuid returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get package with null uuid");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.saveConceptProposalPackage(ProposedConceptPackage conceptPackage)
	 */
	@Override
	public ProposedConceptPackage saveConceptProposalPackage(ProposedConceptPackage conceptPackage) {
		if (conceptPackage != null) {
			sessionFactory.getCurrentSession().saveOrUpdate(conceptPackage);
			return conceptPackage; 
		} else {
			log.warn("Attempting to save null package");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.deleteConceptProposalPackage(ProposedConceptPackage conceptPackage)
	 */
	@Override
	public void deleteConceptProposalPackage(ProposedConceptPackage conceptPackage) {
		if (conceptPackage != null) {
			sessionFactory.getCurrentSession().delete(conceptPackage);
		} else {
			log.warn("Attempting to delete null package");
		}
	}

}
