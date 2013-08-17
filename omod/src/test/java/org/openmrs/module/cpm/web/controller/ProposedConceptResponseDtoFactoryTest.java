package org.openmrs.module.cpm.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.ConceptClass;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.cpm.web.dto.factory.DescriptionDtoFactory;
import org.openmrs.module.cpm.web.dto.factory.NameDtoFactory;
import org.openmrs.module.cpm.web.dto.factory.ProposedConceptResponseDtoFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProposedConceptResponseDtoFactoryTest {

	@Mock
	private ProposedConceptResponse proposedConceptResponseMock;

	@Mock
	private ConceptClass conceptClassMock;

    @InjectMocks
    private ProposedConceptResponseDtoFactory proposedConceptResponseDtoFactory =
            new ProposedConceptResponseDtoFactory(new DescriptionDtoFactory(), new NameDtoFactory());

	@Test
	public void createProposedConceptResponseDto() {

		when(proposedConceptResponseMock.getConceptClass()).thenReturn(conceptClassMock);
		when(conceptClassMock.getName()).thenReturn("test");

		final ProposedConceptResponseDto proposedConceptResponseDto = proposedConceptResponseDtoFactory.createProposedConceptResponseDto(proposedConceptResponseMock);

		assertThat(proposedConceptResponseDto.getConceptClass(), is(equalTo("test")));
	}
}
