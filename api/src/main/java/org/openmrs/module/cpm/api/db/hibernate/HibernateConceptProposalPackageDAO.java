package org.openmrs.module.cpm.api.db.hibernate;

import java.util.List;

import org.hibernate.Query;
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
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalPackage.getAllConceptProposalPackages()
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
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalPackage.getConceptProposalPackageById(Integer id)
	 */
	@Override
	public ConceptProposalPackage getConceptProposalPackageById(Integer id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackage package where package.id = :id");
		query.setInteger("id", id);
        ConceptProposalPackage result = (ConceptProposalPackage) query.uniqueResult();
		if (Log.isDebugEnabled()) { Log.debug("getConceptProposalPackageById returned: " + result); }
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalPackage.getConceptProposalPackageByUuid(Integer uuid)
	 */
	@Override
	public ConceptProposalPackage getConceptProposalPackageByUuid(String uuid) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ConceptProposalPackage package where package.uuid = :uuid");
		query.setString("uuid", uuid);
        ConceptProposalPackage result = (ConceptProposalPackage) query.uniqueResult();
		if (Log.isDebugEnabled()) { Log.debug("getConceptProposalPackageByUuid returned: " + result); }
		return result;
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalPackage.saveConceptProposalPackage(ConceptProposalPackage conceptPackage)
	 */
	@Override
	public ConceptProposalPackage saveConceptProposalPackage(ConceptProposalPackage conceptPackage) {
		sessionFactory.getCurrentSession().saveOrUpdate(conceptPackage);
		return conceptPackage; 
	}
	
	/**
	 * @see org.openmrs.module.metadatasharing.api.ConceptProposalPackage.getConceptProposalPackageById(Integer id)
	 */
	@Override
	public void deleteConceptProposalPackage(ConceptProposalPackage conceptPackage) {
		sessionFactory.getCurrentSession().delete(conceptPackage);
	}
	
}
