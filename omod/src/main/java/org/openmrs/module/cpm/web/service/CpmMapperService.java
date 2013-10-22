package org.openmrs.module.cpm.web.service;

import org.dozer.Mapper;
import org.openmrs.module.cpm.ProposedConceptPackage;
import org.openmrs.module.cpm.ProposedConceptResponsePackage;
import org.openmrs.module.cpm.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponsePackageDto;
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

	public ProposedConceptResponsePackage convertSubmissionDtoToProposedConceptResponsePackage(final SubmissionDto incomingProposal){
		return dozerBeanMapper.map(incomingProposal, ProposedConceptResponsePackage.class);
	}

	public ProposedConceptPackageDto convertProposedConceptPackageToProposedConceptDto(ProposedConceptPackage proposedConceptPackage){
		return dozerBeanMapper.map(proposedConceptPackage, ProposedConceptPackageDto.class);
	}

	public ProposedConceptPackage convertProposedConceptDtoToProposedConceptPackage(ProposedConceptPackageDto dto) {
		return dozerBeanMapper.map(dto, ProposedConceptPackage.class);
	}

	public ProposedConceptResponsePackageDto convertProposedConceptResponsePackageToProposedConceptResponseDto(ProposedConceptResponsePackage proposedConceptPackage){
		return dozerBeanMapper.map(proposedConceptPackage, ProposedConceptResponsePackageDto.class);
	}

	public ProposedConceptResponsePackage convertProposedConceptResponseDtoToProposedConceptResponsePackage(ProposedConceptResponsePackageDto dto) {
		return dozerBeanMapper.map(dto, ProposedConceptResponsePackage.class);
	}

}
