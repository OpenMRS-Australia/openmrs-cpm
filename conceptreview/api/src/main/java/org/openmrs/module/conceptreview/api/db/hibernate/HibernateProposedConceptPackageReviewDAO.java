package org.openmrs.module.conceptreview.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.api.APIException;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewPackage;
import org.openmrs.module.conceptreview.api.db.ProposedConceptPackageReviewDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate specific database methods for {@link ProposedConceptPackageReviewDAO}
 */
@Repository
public class HibernateProposedConceptPackageReviewDAO implements ProposedConceptPackageReviewDAO {
	
	private static Log log = LogFactory.getLog(HibernateProposedConceptPackageReviewDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<ProposedConceptReviewPackage> getAllConceptProposalReviewPackages() {
		@SuppressWarnings("unchecked")
        List<ProposedConceptReviewPackage> result = (List<ProposedConceptReviewPackage>) sessionFactory.getCurrentSession().createQuery("from ProposedConceptReviewPackage").list();
		if (log.isDebugEnabled()) { log.info("getAllConceptProposalPackageReviews returned: " + result.size() + " results");
		}
		return result;
	}
	
	@Override
	public ProposedConceptReviewPackage getConceptProposalReviewPackageById(Integer id) {
		if (id != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ProposedConceptReviewPackage package where package.id = :id");
			query.setInteger("id", id);
			ProposedConceptReviewPackage result = (ProposedConceptReviewPackage) query.uniqueResult();
			if (log.isDebugEnabled()) { log.debug("getConceptProposalPackageReviewById returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get package with null id");
			return null;
		}
	}
	
	@Override
	public ProposedConceptReviewPackage getConceptProposalReviewPackageByProposalUuid(String uuid) {
		if (uuid != null) {
			Query query = sessionFactory.getCurrentSession().createQuery("from ProposedConceptReviewPackage package where package.proposedConceptPackageUuid = :uuid");
			query.setString("uuid", uuid);
			ProposedConceptReviewPackage result = (ProposedConceptReviewPackage) query.uniqueResult();
			if (log.isDebugEnabled()) { log.debug("getConceptProposalPackageReviewByProposalUuid returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get package with null uuid");
			return null;
		}
	}
	
	@Override
	public ProposedConceptReviewPackage saveConceptProposalReviewPackage(ProposedConceptReviewPackage conceptPackageReview) {
		if (conceptPackageReview != null) {
			sessionFactory.getCurrentSession().saveOrUpdate(conceptPackageReview);
			return conceptPackageReview;
		} else {
			log.warn("Attempting to save or update null package");
			return null;
		}
	}
	
	@Override
	public void deleteConceptProposalReviewPackage(ProposedConceptReviewPackage conceptPackageReview) {
		if (conceptPackageReview != null) {
			sessionFactory.getCurrentSession().delete(conceptPackageReview);
		} else {
			log.warn("Attempting to delete null package");
		}
		
	}

	@Override
	public void deleteConceptProposalReviewPackageById(final int proposalId) {
		sessionFactory.
			getCurrentSession().
			createQuery("delete from ProposedConceptReviewPackage where id = ?").
			setParameter(0, proposalId).
			executeUpdate();
	}

	@Override
	public ProposedConceptReview getConceptProposalReviewBySourceProposalUuidAndSourceConceptUuid(String packageUuid, String conceptUuid) throws APIException {
		if (packageUuid!= null && conceptUuid != null) {
			ProposedConceptReviewPackage conceptReviewPackage = getConceptProposalReviewPackageByProposalUuid(packageUuid);
			ProposedConceptReview result = null;
			for(ProposedConceptReview review : conceptReviewPackage.getProposedConcepts())
			{
				if(review.getProposedConceptUuid().equals(conceptUuid)){
					result = review;
					break;
				}
			}
			if (log.isDebugEnabled()) { log.debug("getConceptProposalReviewBySourceProposalUuidAndSourceConceptUuid returned: " + result); }
			return result;
		} else {
			log.warn("Attempting to get concept with null source package uuid and null source concept uuid");
			return null;
		}
	}


}
