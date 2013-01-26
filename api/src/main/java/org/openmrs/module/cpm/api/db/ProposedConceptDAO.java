package org.openmrs.module.cpm.api.db;

import org.openmrs.module.cpm.ProposedConcept;
import org.openmrs.module.cpm.ProposedConceptPackage;

import java.util.List;

/**
 * Service interface for the ProposedConcept service data access objects.  This interface is used by Spring to inject
 * transaction interceptors around the ProposedConceptDAO implementations
 */
public interface ProposedConceptDAO {

    void deleteProposedConcept(ProposedConcept proposedConcept);

}
