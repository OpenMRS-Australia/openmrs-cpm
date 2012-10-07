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

	@Override
	public Proposal getProposalById(final Integer id) {
		return dao.fetch(id);
	}

	@Override
	public List<Proposal> findAll() {
		return dao.findAll();
	}

	@Override
	public Integer createProposal(final Proposal proposal) {
		return dao.persist(proposal);
	}

	@Override
	public void saveProposal(final Proposal proposal) {
		dao.persist(proposal);
	}

	@Override
	public void deleteProposal(final Integer id) {
		dao.remove(dao.fetch(id));
	}

}
