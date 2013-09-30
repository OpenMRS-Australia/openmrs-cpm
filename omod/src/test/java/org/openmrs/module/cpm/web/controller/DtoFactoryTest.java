package org.openmrs.module.cpm.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptNameType;
import org.openmrs.module.cpm.ProposalStatus;
import org.openmrs.module.cpm.ProposedConceptResponse;
import org.openmrs.module.cpm.ProposedConceptResponseDescription;
import org.openmrs.module.cpm.ProposedConceptResponseName;
import org.openmrs.module.cpm.ProposedConceptResponseNumeric;
import org.openmrs.module.cpm.web.dto.ProposedConceptResponseDto;
import org.openmrs.module.cpm.web.dto.concept.DescriptionDto;
import org.openmrs.module.cpm.web.dto.concept.NameDto;
import org.openmrs.module.cpm.web.dto.concept.NumericDto;
import org.openmrs.module.cpm.web.dto.factory.DtoFactory;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DtoFactoryTest {

	@Mock
	private ProposedConceptResponse proposedConceptResponse;

	@Mock
	private ConceptClass conceptClass;
	
	private ArrayList<ProposedConceptResponseName> proposedConceptResponseNames = new ArrayList<ProposedConceptResponseName>();
	
	private ArrayList<ProposedConceptResponseDescription> proposedConceptResponseDescriptions = new ArrayList<ProposedConceptResponseDescription>();
	
	@Mock
	private ConceptDatatype conceptDatatype;
	
	@Mock
	private ProposedConceptResponseNumeric proposedConceptResponseNumeric;
	
	@Mock
	private Concept concept;

	@Test
	public void createProposedConceptResponseDto_basic() {
		setupProposedConceptResponse();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptResponseDto proposedConceptResponseDto = DtoFactory.createProposedConceptResponseDto(proposedConceptResponse);

		verifyProposedConceptResponseDto(proposedConceptResponseDto, "test name", null, null, 0);
		verifyConceptName(proposedConceptResponseDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptResponseDto);
	}
	
	@Test
	public void createProposedConceptResponseDto_withoutPreferredName() {
		setupProposedConceptResponse();
		addConceptName("test name", ConceptNameType.INDEX_TERM);
		addConceptDescription("test description");
		
		ProposedConceptResponseDto proposedConceptResponseDto = DtoFactory.createProposedConceptResponseDto(proposedConceptResponse);

		verifyProposedConceptResponseDto(proposedConceptResponseDto, null, null, null, 0);
		verifyConceptName(proposedConceptResponseDto, ConceptNameType.INDEX_TERM);
		verifyConceptDescriptions(proposedConceptResponseDto);
	}
	
	@Test
	public void createProposedConceptResponseDto_withDataType() {
		setupProposedConceptResponse();
		setupConceptDatatype();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptResponseDto proposedConceptResponseDto = DtoFactory.createProposedConceptResponseDto(proposedConceptResponse);

		verifyProposedConceptResponseDto(proposedConceptResponseDto, "test name", "test datatype", null, 0);
		verifyConceptName(proposedConceptResponseDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptResponseDto);
	}
	
	@Test
	public void createProposedConceptResponseDto_withNumericDataType() {
		setupProposedConceptResponse();
		setupNumericConceptDatatype();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptResponseDto proposedConceptResponseDto = DtoFactory.createProposedConceptResponseDto(proposedConceptResponse);

		verifyProposedConceptResponseDto(proposedConceptResponseDto, "test name", "test datatype", null, 0);
		verifyConceptName(proposedConceptResponseDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptResponseDto);
		verifyNumericDetails(proposedConceptResponseDto);
	}
	
	@Test
	public void createProposedConceptResponseDto_withConceptClass() {
		setupProposedConceptResponse();
		setupConceptClass();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptResponseDto proposedConceptResponseDto = DtoFactory.createProposedConceptResponseDto(proposedConceptResponse);

		verifyProposedConceptResponseDto(proposedConceptResponseDto, "test name", null, "test concept class", 0);
		verifyConceptName(proposedConceptResponseDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptResponseDto);
	}
	
	public void createProposedConceptResponseDto_withConcept() {
		setupProposedConceptResponse();
		setupConcept();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptResponseDto proposedConceptResponseDto = DtoFactory.createProposedConceptResponseDto(proposedConceptResponse);

		verifyProposedConceptResponseDto(proposedConceptResponseDto, "test name", null, null, 1);
		verifyConceptName(proposedConceptResponseDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptResponseDto);
	}
	
	
	private void setupProposedConceptResponse() {		
		when(proposedConceptResponse.getId()).thenReturn(1);
		when(proposedConceptResponse.getNames()).thenReturn(proposedConceptResponseNames);
		when(proposedConceptResponse.getDescriptions()).thenReturn(proposedConceptResponseDescriptions);
		
		when(proposedConceptResponse.getStatus()).thenReturn(ProposalStatus.SUBMITTED);
		when(proposedConceptResponse.getComment()).thenReturn("test comment");
		when(proposedConceptResponse.getReviewComment()).thenReturn("test review comment");
	}
	
	private void setupConceptDatatype() {
		when(conceptDatatype.getName()).thenReturn("test datatype");
		when(conceptDatatype.getUuid()).thenReturn("");
		when(proposedConceptResponse.getDatatype()).thenReturn(conceptDatatype);
	}
	
	private void setupNumericConceptDatatype() {
		setupConceptDatatype();
		when(conceptDatatype.getUuid()).thenReturn(ConceptDatatype.NUMERIC_UUID);
		when(proposedConceptResponseNumeric.getUnits()).thenReturn("test units");
		when(proposedConceptResponseNumeric.getPrecise()).thenReturn(true);
		when(proposedConceptResponseNumeric.getHiNormal()).thenReturn(10.0);
		when(proposedConceptResponseNumeric.getHiCritical()).thenReturn(11.0);
		when(proposedConceptResponseNumeric.getHiAbsolute()).thenReturn(100.0);
		when(proposedConceptResponseNumeric.getLowNormal()).thenReturn(2.0);
		when(proposedConceptResponseNumeric.getLowCritical()).thenReturn(1.0);
		when(proposedConceptResponseNumeric.getLowAbsolute()).thenReturn(0.0);
		when(proposedConceptResponse.getNumericDetails()).thenReturn(proposedConceptResponseNumeric);
	}
	
	private void setupConceptClass() {
		when(proposedConceptResponse.getConceptClass()).thenReturn(conceptClass);
		when(conceptClass.getName()).thenReturn("test concept class");
	}
	
	private void setupConcept() {
		when(concept.getId()).thenReturn(1);
		when(proposedConceptResponse.getConcept()).thenReturn(concept);
	}
	
	private void addConceptName(String name, ConceptNameType type) {
		ProposedConceptResponseName proposedConceptResponseName = mock(ProposedConceptResponseName.class);
		when(proposedConceptResponseName.getName()).thenReturn(name);
		when(proposedConceptResponseName.getType()).thenReturn(type);
		when(proposedConceptResponseName.getLocale()).thenReturn(Locale.ENGLISH);
		proposedConceptResponseNames.add(proposedConceptResponseName);
	}
	
	private void addConceptDescription(String description) {
		ProposedConceptResponseDescription proposedConceptResponseDescription = mock(ProposedConceptResponseDescription.class);
		when(proposedConceptResponseDescription.getDescription()).thenReturn("test description");
		when(proposedConceptResponseDescription.getLocale()).thenReturn(Locale.ENGLISH);
		proposedConceptResponseDescriptions.add(proposedConceptResponseDescription);
	}
	
	private void verifyProposedConceptResponseDto(ProposedConceptResponseDto proposedConceptResponseDto, String preferredName, String dataType, String conceptClass, int conceptId) {
		assertThat(proposedConceptResponseDto.getId(), is(1));
		assertThat(proposedConceptResponseDto.getPreferredName(), is(preferredName));
		assertThat(proposedConceptResponseDto.getDatatype(), is(dataType));
		
		assertThat(proposedConceptResponseDto.getStatus(), is(ProposalStatus.SUBMITTED));
		assertThat(proposedConceptResponseDto.getComment(), is("test comment"));
		assertThat(proposedConceptResponseDto.getReviewComment(), is("test review comment"));
		assertThat(proposedConceptResponseDto.getConceptClass(), is(conceptClass));
		assertThat(proposedConceptResponseDto.getConceptId(), is(conceptId));
	}
	
	private void verifyConceptName(ProposedConceptResponseDto proposedConceptResponseDto, ConceptNameType nameType) {
		Collection<NameDto> names = proposedConceptResponseDto.getNames();
		assertThat(names.size(), is(1));
		NameDto name = Iterables.get(names, 0);
		assertThat(name.getName(), is("test name"));
		assertThat(name.getType(), is(nameType));
		assertThat(name.getLocale(), is("en"));
	}
	
	private void verifyConceptDescriptions(ProposedConceptResponseDto proposedConceptResponseDto) {
		Collection<DescriptionDto> descriptions = proposedConceptResponseDto.getDescriptions();
		assertThat(descriptions.size(), is(1));
		DescriptionDto description = Iterables.get(descriptions, 0);
		assertThat(description.getDescription(), is("test description"));
		assertThat(description.getLocale(), is("en"));
	}
	
	private void verifyNumericDetails(ProposedConceptResponseDto proposedConceptResponseDto) {
		NumericDto numericDto = proposedConceptResponseDto.getNumericDetails();
		assertThat(numericDto.getUnits(), is("test units"));
		assertThat(numericDto.getPrecise(), is(true));
		assertThat(numericDto.getHiNormal(), is(10.0));
		assertThat(numericDto.getHiCritical(), is(11.0));
		assertThat(numericDto.getHiAbsolute(), is(100.0));
		assertThat(numericDto.getLowNormal(), is(2.0));
		assertThat(numericDto.getLowCritical(), is(1.0));
		assertThat(numericDto.getLowAbsolute(), is(0.0));
	}
}
