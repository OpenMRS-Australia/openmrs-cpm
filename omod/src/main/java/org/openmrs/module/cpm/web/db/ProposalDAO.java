package org.openmrs.module.cpm.web.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.cpm.web.models.Proposal;

public class ProposalDAO {

private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @SuppressWarnings("unchecked")
    public List<Proposal> getAllProposals() {
    	Query query = sessionFactory.getCurrentSession().createQuery("from Proposals");
    	return (List<Proposal>) query.list();
    }
}
