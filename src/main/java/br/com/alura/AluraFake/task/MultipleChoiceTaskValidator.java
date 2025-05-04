package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultipleChoiceTaskValidator {

    private final TaskValidator taskValidator;

    public MultipleChoiceTaskValidator(TaskValidator taskValidator) {
        this.taskValidator = taskValidator;
    }

    public void validate(MultipleChoiceTaskDTO dto, Course course) {
        taskValidator.validateCourseStatus(course);
        taskValidator.validateStatement(dto.getStatement());
        taskValidator.validateOrder(dto.getOrder());

        List<AnswerOptionDTO> options = dto.getOptions();
        if (options.size() < 3 || options.size() > 5) {
            throw new TaskException("MultipleChoiceTask must have between 3 and 5 options.");
        }

        long correctCount = options.stream().filter(AnswerOptionDTO::getIsCorrect).count();
        if (correctCount < 2) {
            throw new TaskException("MultipleChoiceTask must have at least two correct options.");
        }

        long incorrectCount = options.size() - correctCount;
        if (incorrectCount < 1) {
            throw new TaskException("MultipleChoiceTask must have at least one incorrect option.");
        }

        validateOptionTexts(options, dto.getStatement());
    }

    private void validateOptionTexts(List<AnswerOptionDTO> options, String statement) {
        for (AnswerOptionDTO option : options) {
            if (option.getOption().equalsIgnoreCase(statement)) {
                throw new TaskException("Option text cannot be the same as the statement.");
            }
            if (option.getOption().length() < 4 || option.getOption().length() > 80) {
                throw new TaskException("Option text must be between 4 and 80 characters.");
            }
        }
    }
}
