package org.openmrs.module.cpm.web.services;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.cpm.web.db.ProposalDAO;
import org.openmrs.module.cpm.web.models.Proposal;

public class ProposalService implements OpenmrsService{

    private ProposalDAO proposalDAO;
    
    public ProposalService(ProposalDAO proposalDAO){
    	this.proposalDAO = proposalDAO;
    }
	
    public List<Proposal> getAllProposals(){
    	return proposalDAO.getAllProposals();
		
    }

	@Override
	public void onShutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartup() {
		// TODO Auto-generated method stub
		
	}; 
}
