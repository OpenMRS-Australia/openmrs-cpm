package org.openmrs.module.conceptpropose.web.dto.factory;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.module.conceptpropose.ProposedConcept;
import org.openmrs.module.conceptpropose.ProposedConceptComment;
import org.openmrs.module.conceptpropose.web.dto.CommentDto;
import org.openmrs.module.conceptpropose.web.dto.ProposedConceptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProposedConceptDtoFactory {

    private final DescriptionDtoFactory descriptionDtoFactory;

    private final NameDtoFactory nameDtoFactory;

	private final AnswerDtoFactory answerDtoFactory;

    @Autowired
    public ProposedConceptDtoFactory(final DescriptionDtoFactory descriptionDtoFactory, final NameDtoFactory nameDtoFactory, AnswerDtoFactory answerDtoFactory) {
        this.descriptionDtoFactory = descriptionDtoFactory;
        this.nameDtoFactory = nameDtoFactory;
		this.answerDtoFactory = answerDtoFactory;
    }

    public ProposedConceptDto create(final ProposedConcept proposedConcept, final Concept concept){
        ProposedConceptDto conceptDto = new ProposedConceptDto();

        // concept details
        conceptDto.setNames(nameDtoFactory.create(concept));
        conceptDto.setDescriptions(descriptionDtoFactory.create(concept));
		conceptDto.setAnswers(answerDtoFactory.create(concept));
        conceptDto.setUuid(concept.getUuid());
        conceptDto.setComment(proposedConcept.getComment()); // proposer's comment
        List<CommentDto> comments = new ArrayList<CommentDto>();
        for(ProposedConceptComment comment : proposedConcept.getComments())
        {
            CommentDto commentDto = new CommentDto();
            commentDto.setName(comment.getName());
            commentDto.setEmail(comment.getEmail());
            commentDto.setComment(comment.getComment());
            commentDto.setDateCreated(comment.getDateCreated());
            comments.add(commentDto);
        }
        conceptDto.setComments(comments);

        return conceptDto;
    }
}
