package org.openmrs.module.cpm.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.cpm.ConceptProposalPackageResponse;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageResponseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate specific database methods for {@link ConceptProposalPackageResponseDAO}
 */
@Repository
public class HibernateConceptProposalPackageResponseDAO implements ConceptProposalPackageResponseDAO {
	
	private static Log log = LogFactory.getLog(HibernateConceptProposalPackageDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getAllConceptProposalPackageResponses()
	 */
	@Override
	public List<ConceptProposalPackageResponse> getAllConceptProposalPackageResponses() {
		@SuppressWarnings("unchecked")
        List<ConceptProposalPackageResponse> result = (List<ConceptProposalPackageResponse>) sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackageResponse").list();
		if (log.isDebugEnabled()) { log.info("getAllConceptProposalPackageResponses returned: " + result.size() + " results");
		}
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getConceptProposalPackageResponseById(Integer id)
	 */
	@Override
	public ConceptProposalPackageResponse getConceptProposalPackageResponseById(Integer id) {
		if (id != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackageResponse package where package.id = :id");
			query.setInteger("id", id);
			ConceptProposalPackageResponse result = (ConceptProposalPackageResponse) query.uniqueResult();
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
	public ConceptProposalPackageResponse getConceptProposalPackageResponseByProposalUuid(String uuid) {
		if (uuid != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackageResponse package where package.conceptProposalPackageUuid = :uuid");
			query.setString("uuid", uuid);
			ConceptProposalPackageResponse result = (ConceptProposalPackageResponse) query.uniqueResult();
			if (log.isDebugEnabled()) { log.debug("getConceptProposalPackageResponseByProposalUuid returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get package with null uuid");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.saveConceptProposalPackageResponse(ConceptProposalPackage conceptPackageResponse)
	 */
	@Override
	public ConceptProposalPackageResponse saveConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse) {
		if (conceptPackageResponse != null) {
			sessionFactory.getCurrentSession().saveOrUpdate(conceptPackageResponse);
			return conceptPackageResponse; 
		} else {
			log.warn("Attempting to delete null package");
			return null;
		}
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.deleteConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse)
	 */
	@Override
	public void deleteConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse) {
		if (conceptPackageResponse != null) {
			sessionFactory.getCurrentSession().delete(conceptPackageResponse);
		} else {
			log.warn("Attempting to delete null package");
		}
		
	}


}
