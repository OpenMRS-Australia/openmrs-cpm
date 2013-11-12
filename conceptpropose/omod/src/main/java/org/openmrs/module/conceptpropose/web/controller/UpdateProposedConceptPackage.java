package org.openmrs.module.conceptpropose.web.controller;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.ProposedConcept;
import org.openmrs.module.conceptpropose.ProposedConceptPackage;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptPackageDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateProposedConceptPackage {

	public void updateProposedConcepts(ProposedConceptPackage conceptPackage,
								ProposedConceptPackageDto packageDto) {
		Preconditions.checkNotNull(conceptPackage, "ProposedConceptPackage should not be null");
		Preconditions.checkNotNull(packageDto, "ProposedConceptPackageDto should not be null");
		removeDeletedProposedConcepts(conceptPackage, packageDto);
		addOrModifyProposedConcepts(conceptPackage, packageDto);
	}

	private void removeDeletedProposedConcepts(ProposedConceptPackage conceptPackage,
									   ProposedConceptPackageDto packageDto) {
		//remove concept(s)
		if (conceptPackage.getProposedConcepts().size() > 0) {
			List<Integer> newConceptIds = Lists.newArrayList();
			for (ProposedConceptDto p : packageDto.getConcepts()) {
				newConceptIds.add(p.getId());
			}
			List<Integer> existingConceptIds = Lists.newArrayList();
			for (ProposedConcept p : conceptPackage.getProposedConcepts()) {
				existingConceptIds.add(p.getId());
			}
			//Find concepts that have been deleted
			for (Integer existingId : existingConceptIds) {
				if (!newConceptIds.contains(existingId)) {
					//delete the concept
					ProposedConcept removedConcept = conceptPackage.getProposedConcept(existingId);
					conceptPackage.removeProposedConcept(removedConcept);
				}
			}
		}
	}

	private void addOrModifyProposedConcepts(ProposedConceptPackage conceptPackage,
									 final ProposedConceptPackageDto packageDto) {
		//Add and modify concepts
		for (final ProposedConceptDto newProposedConcept : packageDto.getConcepts()) {
			ProposedConcept proposedConcept = new ProposedConcept();
			final ConceptService conceptService = Context.getConceptService();

			if (conceptPackage.getProposedConcept(newProposedConcept.getId()) != null) {
				//Modify already persisted concept
				proposedConcept = conceptPackage.getProposedConcept(newProposedConcept.getId());
				// todo save comments
			} else {
				//New concept added to the ProposedConceptPackage
				final Concept concept = conceptService.getConcept(newProposedConcept.getId());
				Preconditions.checkNotNull(concept, "Concept should not be null");
				proposedConcept.setConcept(concept);
				proposedConcept.setComment(newProposedConcept.getComment());
				conceptPackage.addProposedConcept(proposedConcept);
			}
		}
	}
}