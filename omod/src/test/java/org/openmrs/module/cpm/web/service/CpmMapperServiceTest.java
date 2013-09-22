package org.openmrs.module.cpm.web.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.cpm.*;
import org.openmrs.module.cpm.web.controller.BaseCpmOmodTest;
import org.openmrs.module.cpm.web.dto.SubmissionDto;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@PrepareForTest(Context.class)
public class CpmMapperServiceTest extends BaseCpmOmodTest {


	@Autowired
	private CpmMapperService mapperService;

	@Test
	public void convertDtoToProposedConceptResponsePackage_regularProposal() throws Exception {
		final SubmissionDto dto = setupRegularProposalFixtureWithJson();

		ProposedConceptResponsePackage value = mapperService.convertDtoToProposedConceptResponsePackage(dto);
		assertThat(value.getName(), is("A proposal"));
		assertThat(value.getEmail(), is("asdf@asdf.com"));
		assertThat(value.getDescription(), is("A description"));

		final List<ProposedConceptResponse> proposedConcepts = new ArrayList<ProposedConceptResponse>(value.getProposedConcepts());
		final ProposedConceptResponse proposedConceptResponse = proposedConcepts.get(0);
		assertThat(proposedConceptResponse.getProposedConceptUuid(), is("concept-uuid"));
		assertThat(proposedConceptResponse.getComment(), is("some comment"));
		assertNull(proposedConceptResponse.getConceptClass());
		assertNull(proposedConceptResponse.getDatatype());

		final List<ProposedConceptResponseName> names = proposedConceptResponse.getNames();
		assertThat(names.get(0).getName(), is("Concept name"));

		final List<ProposedConceptResponseDescription> descriptions = proposedConceptResponse.getDescriptions();
		assertThat(descriptions.get(0).getDescription(), is("Concept description"));
	}


	private SubmissionDto setupRegularProposalFixtureWithJson() throws Exception {

		String regularFixture =
				"{" +
				"  'name': 'A proposal'," +
				"  'email': 'asdf@asdf.com'," +
				"  'description': 'A description'," +
				"  'concepts': [" +
				"    {" +
				"      'uuid': 'concept-uuid'," +
				"      'conceptClass': 'concept-class-uuid'," +
				"      'datatype': 'datatype-uuid'," +
				"      'comment': 'some comment'," +
				"      'names': [" +
				"        {" +
				"          'name': 'Concept name'," +
				"          'locale': 'en'" +
				"        }" +
				"      ]," +
				"      'descriptions': [" +
				"        {" +
				"          'description': 'Concept description'," +
				"          'locale': 'en'" +
				"        }" +
				"      ]" +
				"    }" +
				"  ]" +
				"}";

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(regularFixture.replace("'", "\""), SubmissionDto.class);
	}


	@Test
	public void convertDtoToProposedConceptResponsePackage_numbericProposal() throws Exception {
		final SubmissionDto dto = setupNumericProposalFixture();

		ProposedConceptResponsePackage value = mapperService.convertDtoToProposedConceptResponsePackage(dto);

		final List<ProposedConceptResponse> proposedConcepts = new ArrayList<ProposedConceptResponse>(value.getProposedConcepts());
		final ProposedConceptResponse proposedConceptResponse = proposedConcepts.get(0);

		final ProposedConceptResponseNumeric numericDetails = proposedConceptResponse.getNumericDetails();
		assertThat(numericDetails.getUnits(), is("ml"));
		assertThat(numericDetails.getHiNormal(), is(100.5));
		assertThat(numericDetails.getHiCritical(), is(110.0));
		assertThat(numericDetails.getHiAbsolute(), is(1000.0));
		assertThat(numericDetails.getLowNormal(), is(20.3));
		assertThat(numericDetails.getLowCritical(), is(15.0));
		assertThat(numericDetails.getLowAbsolute(), is(0.0));
	}

	private SubmissionDto setupNumericProposalFixture() throws IOException {
		final String fixture =
				"{" +
				"  'name': 'A proposal'," +
				"  'email': 'asdf@asdf.com'," +
				"  'description': 'A description'," +
				"  'concepts': [" +
				"    {" +
				"      'uuid': 'concept-uuid'," +
				"      'conceptClass': 'concept-class-uuid'," +
				"      'datatype': '8d4a4488-c2cc-11de-8d13-0010c6dffd0f'," +
				"      'comment': 'some comment'," +
				"      'names': [" +
				"        {" +
				"          'name': 'Concept name'," +
				"          'locale': 'en'" +
				"        }" +
				"      ]," +
				"      'descriptions': [" +
				"        {" +
				"          'description': 'Concept description'," +
				"          'locale': 'en'" +
				"        }" +
				"      ]," +
				"      'numericDetails': {" +
				"        'units': 'ml'," +
				"        'precise': true," +
				"        'hiNormal': 100.5," +
				"        'hiCritical': 110," +
				"        'hiAbsolute': 1000," +
				"        'lowNormal': 20.3," +
				"        'lowCritical': 15," +
				"        'lowAbsolute': 0" +
				"      }" +
				"    }" +
				"  ]" +
				"}";
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(fixture.replace("'", "\""), SubmissionDto.class);
	}


}
