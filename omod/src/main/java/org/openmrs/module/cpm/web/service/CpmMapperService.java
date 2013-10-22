package org.openmrs.module.cpm.web.service;

import org.dozer.Mapper;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpmMapperService {

	private Mapper dozerBeanMapper;

	@Autowired
	public CpmMapperService(Mapper dozerBeanMapper) {
		this.dozerBeanMapper = dozerBeanMapper;
	}

	public ProposedConceptResponsePackage convertDtoToProposedConceptResponsePackage(final SubmissionDto incomingProposal){
		return dozerBeanMapper.map(incomingProposal, ProposedConceptResponsePackage.class);
	}

	public ProposedConceptPackageDto convertProposedConceptPackageToDto(ProposedConceptPackage proposedConceptPackage){
		return dozerBeanMapper.map(proposedConceptPackage, ProposedConceptPackageDto.class);

	}

	public ProposedConceptPackage convertDtoToProposedConceptPackage(ProposedConceptPackageDto dto) {
		return dozerBeanMapper.map(dto, ProposedConceptPackage.class);
	}
}
