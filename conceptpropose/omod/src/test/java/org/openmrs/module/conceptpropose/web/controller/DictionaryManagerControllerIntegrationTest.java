package org.openmrs.module.conceptpropose.web.controller;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.openmrs.Concept;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptpropose.SubmissionResponseStatus;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionDto;
import org.openmrs.module.conceptpropose.web.dto.SubmissionResponseDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@PrepareForTest(Context.class)
public class DictionaryManagerControllerIntegrationTest extends BaseCpmOmodTest {

    @Autowired
	private DictionaryManagerController controller;

    private static List<Concept> allConcepts;

	@Rule
	public ExpectedException exception = ExpectedException.none();

    @Before
    public  void beforeTest(){
        if(allConcepts == null) {
            allConcepts = Context.getConceptService().getAllConcepts(null, true, false);
        }

    }

	@Test
	@Ignore  // Test is failing due to org.hibernate.LazyInitializationException in createValidSubmissionDto()
	public void submitProposal_validSubmission_shouldSaveProposedConceptResponsePackageAndReturnResponse() throws Exception {
        SubmissionDto submissionDto = createValidSubmissionDto();
        assertNotNull(controller);

        final SubmissionResponseDto response = controller.submitProposal(submissionDto);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(SubmissionResponseStatus.SUCCESS.equals(response.getStatus()));
        assertTrue("All Good!".equalsIgnoreCase(response.getMessage()));
    }

    @Test
    public void submitProposal_invalidSubmission_shouldReturnErrorResponse() throws Exception {
        NameDto nameDto = new NameDto();
        nameDto.setName("SAME");
        nameDto.setType(ConceptNameType.SHORT);
        nameDto.setLocale(Locale.getDefault().toString());
        List<NameDto> nameDtos = Lists.newArrayList(nameDto);

        ProposedConceptDto dto = new ProposedConceptDto();
        dto.setComment("bla");
        dto.setNames(nameDtos);
        dto.setDescriptions(new ArrayList<DescriptionDto>());
        List<ProposedConceptDto> proposedConceptDtos = Lists.newArrayList();
        proposedConceptDtos.add(dto);

        final SubmissionDto submissionDto = new SubmissionDto();
        submissionDto.setConcepts(proposedConceptDtos);
        submissionDto.setDescription("Invalid data type missing example");

        final SubmissionResponseDto response = controller.submitProposal(submissionDto);

        assertNotNull(response);
        assertTrue(SubmissionResponseStatus.FAILURE.equals(response.getStatus()));


    }



    private SubmissionDto createValidSubmissionDto() throws Exception{

        List<Concept> filteredConcepts = ImmutableList.copyOf(Iterables.filter(allConcepts, new Predicate<Concept>() {
            @Override
            public boolean apply( org.openmrs.Concept concept) {
                if(concept.getDatatype() == null) {
                    return false;
                }
                return true;
            }
        }));
        Concept testConcept = filteredConcepts.get(0);
        ProposedConceptDto proposedConceptDto = new ProposedConceptDto();
        BeanUtils.copyProperties(proposedConceptDto, testConcept);
        Collection<NameDto> nameDtos = Lists.newArrayList();
        for(ConceptName name : testConcept.getNames()) {
            NameDto nameDto = new NameDto();
            BeanUtils.copyProperties(nameDto, name);
            nameDtos.add(nameDto);
        }
        Collection<DescriptionDto> descriptionDtos = Lists.newArrayList();
        for(ConceptDescription description : testConcept.getDescriptions()) {
            DescriptionDto dto = new DescriptionDto();
            BeanUtils.copyProperties(dto, description);
            descriptionDtos.add(dto);
        }
        proposedConceptDto.setComment("bla");
        proposedConceptDto.setNames(nameDtos);
        proposedConceptDto.setDescriptions(descriptionDtos);
        proposedConceptDto.setDatatype(testConcept.getDatatype().getUuid());
        proposedConceptDto.setConceptClass(testConcept.getConceptClass().getUuid());


        List<ProposedConceptDto> proposedConceptDtos = Lists.newArrayList();
        proposedConceptDtos.add(proposedConceptDto);

        final SubmissionDto incomingProposals = new SubmissionDto();
        incomingProposals.setConcepts(proposedConceptDtos);
        incomingProposals.setEmail("testCpmDictionaryManagerControler@test.com");
        incomingProposals.setName("Mr Cpm Integration Test");

        return incomingProposals;

    }



}
