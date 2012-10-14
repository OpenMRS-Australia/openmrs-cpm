package org.openmrs.module.cpm.api.db.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.openmrs.module.cpm.ConceptProposalPackageResponse;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageDAO;
import org.openmrs.module.cpm.api.db.ConceptProposalPackageResponseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate specific database methods for {@link ConceptProposalPackageResponseDAO}
 */
@Repository
public class HibernateConceptProposalPackageResponseDAO implements ConceptProposalPackageResponseDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getAllConceptProposalPackageResponses()
	 */
	@Override
	public List<ConceptProposalPackageResponse> getAllConceptProposalPackageResponses() {
		@SuppressWarnings("unchecked")
        List<ConceptProposalPackageResponse> result = (List<ConceptProposalPackageResponse>) sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackageResponse").list();
		if (Log.isInfoEnabled()) {
			Log.info("getAllConceptProposalPackageResponses returned: " + result.size() + " results");
		}
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getConceptProposalPackageResponseById(Integer id)
	 */
	@Override
	public ConceptProposalPackageResponse getConceptProposalPackageResponseById(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackageResponse package where package.id = :id");
		query.setInteger("id", id);
		ConceptProposalPackageResponse result = (ConceptProposalPackageResponse) query.uniqueResult();
		if (Log.isDebugEnabled()) { Log.debug("getConceptProposalPackageResponseById returned: " + result); }
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.getConceptProposalPackageResponseByProposalUuid(Integer uuid)
	 */
	@Override
	public ConceptProposalPackageResponse getConceptProposalPackageResponseByProposalUuid(String uuid) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackageResponse package where package.conceptProposalUuid = :uuid");
		query.setString("uuid", uuid);
		ConceptProposalPackageResponse result = (ConceptProposalPackageResponse) query.uniqueResult();
		if (Log.isDebugEnabled()) { Log.debug("getConceptProposalPackageResponseByProposalUuid returned: " + result); }
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.saveConceptProposalPackageResponse(ConceptProposalPackage conceptPackageResponse)
	 */
	@Override
	public ConceptProposalPackageResponse saveConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse) {
		sessionFactory.getCurrentSession().saveOrUpdate(conceptPackageResponse);
		return conceptPackageResponse; 
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalService.deleteConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse)
	 */
	@Override
	public void deleteConceptProposalPackageResponse(ConceptProposalPackageResponse conceptPackageResponse) {
		sessionFactory.getCurrentSession().delete(conceptPackageResponse);
	}


}
