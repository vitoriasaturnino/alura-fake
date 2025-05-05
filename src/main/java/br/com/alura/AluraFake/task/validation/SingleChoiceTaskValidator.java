package br.com.alura.AluraFake.task.validation;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.TaskException;
import br.com.alura.AluraFake.task.TaskValidator;
import br.com.alura.AluraFake.task.answer_options.AnswerOptionDTO;
import br.com.alura.AluraFake.task.single_choice.SingleChoiceTaskDTO;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SingleChoiceTaskValidator {

    private final TaskValidator taskValidator;

    public SingleChoiceTaskValidator(TaskValidator taskValidator) {
        this.taskValidator = taskValidator;
    }

    public void validate(SingleChoiceTaskDTO dto, Course course) {
        taskValidator.validateCourseStatus(course);
        taskValidator.validateStatement(dto.getStatement());
        taskValidator.validateOrder(dto.getOrder());

        List<AnswerOptionDTO> options = dto.getOptions();
        if (options.size() < 2 || options.size() > 5) {
            throw new TaskException("SingleChoiceTask must have between 2 and 5 options.");
        }

        long correctCount = options.stream().filter(AnswerOptionDTO::getIsCorrect).count();
        if (correctCount != 1) {
            throw new TaskException("SingleChoiceTask must have exactly one correct option.");
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
