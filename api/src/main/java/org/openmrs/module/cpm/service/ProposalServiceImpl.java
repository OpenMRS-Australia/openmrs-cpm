package org.openmrs.module.cpm.service;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.cpm.db.ProposalDao;
import org.openmrs.module.cpm.model.Proposal;

public class ProposalServiceImpl extends BaseOpenmrsService implements ProposalService {

	private ProposalDao dao;

	public void setProposalDao(final ProposalDao dao) {
		this.dao = dao;
	}

	public Proposal getProposalById(final Integer id) {
		return dao.fetch(id);
	}

	public List<Proposal> findAll() {
		return dao.findAll();
	}

}
