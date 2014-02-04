package org.openmrs.module.conceptpropose.web.service;

import org.dozer.Mapper;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptPackageDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConceptProposeMapperService {

	private Mapper dozerBeanMapper;

	@Autowired
	public ConceptProposeMapperService(@Qualifier("conceptproposemapper") Mapper dozerBeanMapper) {
		this.dozerBeanMapper = dozerBeanMapper;
	}

	public SubmissionDto convertProposedConceptPackageToSubmissionDto(ProposedConceptPackage proposedConceptPackage) {
		return dozerBeanMapper.map(proposedConceptPackage, SubmissionDto.class);
	}

	public ProposedConceptPackageDto convertProposedConceptPackageToProposedConceptDto(ProposedConceptPackage proposedConceptPackage){
		return dozerBeanMapper.map(proposedConceptPackage, ProposedConceptPackageDto.class);
	}

	public ProposedConceptPackage convertProposedConceptDtoToProposedConceptPackage(ProposedConceptPackageDto dto) {
		return dozerBeanMapper.map(dto, ProposedConceptPackage.class);
	}


}
