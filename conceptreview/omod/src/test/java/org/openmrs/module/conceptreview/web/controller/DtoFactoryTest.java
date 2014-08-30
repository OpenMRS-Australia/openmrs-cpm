package org.openmrs.module.conceptreview.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.api.ConceptNameType;

import com.google.common.collect.Iterables;
import org.openmrs.module.conceptpropose.ProposalStatus;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptReviewDto;
import org.openmrs.module.conceptpropose.web.dto.concept.DescriptionDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NameDto;
import org.openmrs.module.conceptpropose.web.dto.concept.NumericDto;
import org.openmrs.module.conceptreview.ProposedConceptReview;
import org.openmrs.module.conceptreview.ProposedConceptReviewDescription;
import org.openmrs.module.conceptreview.ProposedConceptReviewName;
import org.openmrs.module.conceptreview.ProposedConceptReviewNumeric;
import org.openmrs.module.conceptreview.web.dto.factory.DtoFactory;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DtoFactoryTest {

	@Mock
	private ProposedConceptReview proposedConceptReview;

	@Mock
	private ConceptClass conceptClass;
	
	private ArrayList<ProposedConceptReviewName> proposedConceptReviewNames = new ArrayList<ProposedConceptReviewName>();
	
	private ArrayList<ProposedConceptReviewDescription> proposedConceptReviewDescriptions = new ArrayList<ProposedConceptReviewDescription>();
	
	@Mock
	private ConceptDatatype conceptDatatype;
	
	@Mock
	private ProposedConceptReviewNumeric proposedConceptReviewNumeric;
	
	@Mock
	private Concept concept;

	@Test
	public void createProposedConceptReviewDto_basic() {
		setupProposedConceptReview();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptReviewDto proposedConceptReviewDto = DtoFactory.createProposedConceptReviewDto(proposedConceptReview);

		verifyProposedConceptReviewDto(proposedConceptReviewDto, "test name", null, null, 0);
		verifyConceptName(proposedConceptReviewDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptReviewDto);
	}
	
	@Test
	public void createProposedConceptReviewDto_withoutPreferredName() {
		setupProposedConceptReview();
		addConceptName("test name", ConceptNameType.INDEX_TERM);
		addConceptDescription("test description");
		
		ProposedConceptReviewDto proposedConceptReviewDto = DtoFactory.createProposedConceptReviewDto(proposedConceptReview);

		verifyProposedConceptReviewDto(proposedConceptReviewDto, null, null, null, 0);
		verifyConceptName(proposedConceptReviewDto, ConceptNameType.INDEX_TERM);
		verifyConceptDescriptions(proposedConceptReviewDto);
	}
	
	@Test
	public void createProposedConceptReviewDto_withDataType() {
		setupProposedConceptReview();
		setupConceptDatatype();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptReviewDto proposedConceptReviewDto = DtoFactory.createProposedConceptReviewDto(proposedConceptReview);

		verifyProposedConceptReviewDto(proposedConceptReviewDto, "test name", "test datatype", null, 0);
		verifyConceptName(proposedConceptReviewDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptReviewDto);
	}
	
	@Test
	public void createProposedConceptReviewDto_withNumericDataType() {
		setupProposedConceptReview();
		setupNumericConceptDatatype();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptReviewDto proposedConceptReviewDto = DtoFactory.createProposedConceptReviewDto(proposedConceptReview);

		verifyProposedConceptReviewDto(proposedConceptReviewDto, "test name", "test datatype", null, 0);
		verifyConceptName(proposedConceptReviewDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptReviewDto);
		verifyNumericDetails(proposedConceptReviewDto);
	}
	
	@Test
	public void createProposedConceptReviewDto_withConceptClass() {
		setupProposedConceptReview();
		setupConceptClass();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptReviewDto proposedConceptReviewDto = DtoFactory.createProposedConceptReviewDto(proposedConceptReview);

		verifyProposedConceptReviewDto(proposedConceptReviewDto, "test name", null, "test concept class", 0);
		verifyConceptName(proposedConceptReviewDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptReviewDto);
	}

	@Test
	public void createProposedConceptReviewDto_withConcept() {
		setupProposedConceptReview();
		setupConcept();
		addConceptName("test name", ConceptNameType.FULLY_SPECIFIED);
		addConceptDescription("test description");
		
		ProposedConceptReviewDto proposedConceptReviewDto = DtoFactory.createProposedConceptReviewDto(proposedConceptReview);

		verifyProposedConceptReviewDto(proposedConceptReviewDto, "test name", null, null, 1);
		verifyConceptName(proposedConceptReviewDto, ConceptNameType.FULLY_SPECIFIED);
		verifyConceptDescriptions(proposedConceptReviewDto);
	}
	
	
	private void setupProposedConceptReview() {
		when(proposedConceptReview.getId()).thenReturn(1);
		when(proposedConceptReview.getNames()).thenReturn(proposedConceptReviewNames);
		when(proposedConceptReview.getDescriptions()).thenReturn(proposedConceptReviewDescriptions);
		
		when(proposedConceptReview.getStatus()).thenReturn(ProposalStatus.SUBMITTED);
		when(proposedConceptReview.getComment()).thenReturn("test comment");
		when(proposedConceptReview.getReviewComment()).thenReturn("test review comment");
	}
	
	private void setupConceptDatatype() {
		when(conceptDatatype.getName()).thenReturn("test datatype");
		when(conceptDatatype.getUuid()).thenReturn("");
		when(proposedConceptReview.getDatatype()).thenReturn(conceptDatatype);
	}
	
	private void setupNumericConceptDatatype() {
		setupConceptDatatype();
		when(conceptDatatype.getUuid()).thenReturn(ConceptDatatype.NUMERIC_UUID);
		when(proposedConceptReviewNumeric.getUnits()).thenReturn("test units");
		when(proposedConceptReviewNumeric.getPrecise()).thenReturn(true);
		when(proposedConceptReviewNumeric.getHiNormal()).thenReturn(10.0);
		when(proposedConceptReviewNumeric.getHiCritical()).thenReturn(11.0);
		when(proposedConceptReviewNumeric.getHiAbsolute()).thenReturn(100.0);
		when(proposedConceptReviewNumeric.getLowNormal()).thenReturn(2.0);
		when(proposedConceptReviewNumeric.getLowCritical()).thenReturn(1.0);
		when(proposedConceptReviewNumeric.getLowAbsolute()).thenReturn(0.0);
		when(proposedConceptReview.getNumericDetails()).thenReturn(proposedConceptReviewNumeric);
	}
	
	private void setupConceptClass() {
		when(proposedConceptReview.getConceptClass()).thenReturn(conceptClass);
		when(conceptClass.getName()).thenReturn("test concept class");
	}
	
	private void setupConcept() {
		when(concept.getId()).thenReturn(1);
		when(proposedConceptReview.getConcept()).thenReturn(concept);
	}
	
	private void addConceptName(String name, ConceptNameType type) {
		ProposedConceptReviewName proposedConceptReviewName = mock(ProposedConceptReviewName.class);
		when(proposedConceptReviewName.getName()).thenReturn(name);
		when(proposedConceptReviewName.getType()).thenReturn(type);
		when(proposedConceptReviewName.getLocale()).thenReturn(Locale.ENGLISH);
		proposedConceptReviewNames.add(proposedConceptReviewName);
	}
	
	private void addConceptDescription(String description) {
		ProposedConceptReviewDescription proposedConceptReviewDescription = mock(ProposedConceptReviewDescription.class);
		when(proposedConceptReviewDescription.getDescription()).thenReturn("test description");
		when(proposedConceptReviewDescription.getLocale()).thenReturn(Locale.ENGLISH);
		proposedConceptReviewDescriptions.add(proposedConceptReviewDescription);
	}
	
	private void verifyProposedConceptReviewDto(ProposedConceptReviewDto proposedConceptReviewDto, String preferredName, String dataType, String conceptClass, int conceptId) {
		assertThat(proposedConceptReviewDto.getId(), is(1));
		assertThat(proposedConceptReviewDto.getPreferredName(), is(preferredName));
		assertThat(proposedConceptReviewDto.getDatatype(), is(dataType));
		
		assertThat(proposedConceptReviewDto.getStatus(), is(ProposalStatus.SUBMITTED));
		assertThat(proposedConceptReviewDto.getComment(), is("test comment"));
		assertThat(proposedConceptReviewDto.getReviewComment(), is("test review comment"));
		assertThat(proposedConceptReviewDto.getConceptClass(), is(conceptClass));
		assertThat(proposedConceptReviewDto.getConceptId(), is(conceptId));
	}
	
	private void verifyConceptName(ProposedConceptReviewDto proposedConceptReviewDto, ConceptNameType nameType) {
		Collection<NameDto> names = proposedConceptReviewDto.getNames();
		assertThat(names.size(), is(1));
		NameDto name = Iterables.get(names, 0);
		assertThat(name.getName(), is("test name"));
		assertThat(name.getType(), is(nameType));
		assertThat(name.getLocale(), is("en"));
	}
	
	private void verifyConceptDescriptions(ProposedConceptReviewDto proposedConceptReviewDto) {
		Collection<DescriptionDto> descriptions = proposedConceptReviewDto.getDescriptions();
		assertThat(descriptions.size(), is(1));
		DescriptionDto description = Iterables.get(descriptions, 0);
		assertThat(description.getDescription(), is("test description"));
		assertThat(description.getLocale(), is("en"));
	}
	
	private void verifyNumericDetails(ProposedConceptReviewDto proposedConceptReviewDto) {
		NumericDto numericDto = proposedConceptReviewDto.getNumericDetails();
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
