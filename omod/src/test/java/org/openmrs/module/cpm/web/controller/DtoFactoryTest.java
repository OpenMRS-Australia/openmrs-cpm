package org.openmrs.module.cpm.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.ConceptClass;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.cpm.web.dto.factory.DtoFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DtoFactoryTest {

	@Mock
	private ProposedConceptResponse proposedConceptResponseMock;

	@Mock
	private ConceptClass conceptClassMock;

	@Test
	public void createProposedConceptResponseDto() {

		when(proposedConceptResponseMock.getConceptClass()).thenReturn(conceptClassMock);
		when(conceptClassMock.getName()).thenReturn("test");

		final ProposedConceptResponseDto proposedConceptResponseDto = DtoFactory.createProposedConceptResponseDto(proposedConceptResponseMock);

		assertThat(proposedConceptResponseDto.getConceptClass(), is(equalTo("test")));
	}
}
