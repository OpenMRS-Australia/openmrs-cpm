package org.openmrs.module.conceptpropose.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.conceptpropose.ProposedConceptResponsePackage;
import org.openmrs.module.conceptpropose.api.db.ProposedConceptPackageResponseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate specific database methods for {@link ProposedConceptPackageResponseDAO}
 */
@Repository
public class HibernateProposedConceptPackageResponseDAO implements ProposedConceptPackageResponseDAO {
	
	private static Log log = LogFactory.getLog(org.openmrs.module.conceptpropose.api.db.hibernate.HibernateProposedConceptPackageDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getAllConceptProposalPackageResponses()
	 */
	@Override
	public List<ProposedConceptResponsePackage> getAllConceptProposalResponsePackages() {
		@SuppressWarnings("unchecked")
        List<ProposedConceptResponsePackage> result = (List<ProposedConceptResponsePackage>) sessionFactory.getCurrentSession().createQuery("from ProposedConceptResponsePackage").list();
		if (log.isDebugEnabled()) { log.info("getAllConceptProposalPackageResponses returned: " + result.size() + " results");
		}
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getConceptProposalPackageResponseById(Integer id)
	 */
	@Override
	public ProposedConceptResponsePackage getConceptProposalResponsePackageById(Integer id) {
		if (id != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ProposedConceptResponsePackage package where package.id = :id");
			query.setInteger("id", id);
			ProposedConceptResponsePackage result = (ProposedConceptResponsePackage) query.uniqueResult();
			if (log.isDebugEnabled()) { log.debug("getConceptProposalPackageResponseById returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get package with null id");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getConceptProposalPackageResponseByProposalUuid(Integer uuid)
	 */
	@Override
	public ProposedConceptResponsePackage getConceptProposalResponsePackageByProposalUuid(String uuid) {
		if (uuid != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ProposedConceptResponsePackage package where package.proposedConceptPackageUuid = :uuid");
			query.setString("uuid", uuid);
			ProposedConceptResponsePackage result = (ProposedConceptResponsePackage) query.uniqueResult();
			if (log.isDebugEnabled()) { log.debug("getConceptProposalPackageResponseByProposalUuid returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get package with null uuid");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.saveConceptProposalPackageResponse(ProposedConceptPackage conceptPackageResponse)
	 */
	@Override
	public ProposedConceptResponsePackage saveConceptProposalResponsePackage(ProposedConceptResponsePackage conceptPackageResponse) {
		if (conceptPackageResponse != null) {
			sessionFactory.getCurrentSession().saveOrUpdate(conceptPackageResponse);
			return conceptPackageResponse; 
		} else {
			log.warn("Attempting to save or update null package");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.deleteConceptProposalPackageResponse(ProposedConceptResponsePackage conceptPackageResponse)
	 */
	@Override
	public void deleteConceptProposalResponsePackage(ProposedConceptResponsePackage conceptPackageResponse) {
		if (conceptPackageResponse != null) {
			sessionFactory.getCurrentSession().delete(conceptPackageResponse);
		} else {
			log.warn("Attempting to delete null package");
		}
		
	}

	@Override
	public void deleteConceptProposalResponsePackageById(final int proposalId) {
		sessionFactory.
			getCurrentSession().
			createQuery("delete from ProposedConceptResponsePackage where id = ?").
			setParameter(0, proposalId).
			executeUpdate();
	}


}
