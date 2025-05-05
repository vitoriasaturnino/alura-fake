package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import org.springframework.stereotype.Component;

@Component
public class OpenTextTaskValidator {

    private final TaskValidator taskValidator;

    public OpenTextTaskValidator(TaskValidator taskValidator) {
        this.taskValidator = taskValidator;
    }

    public void validate(OpenTextTaskDTO dto, Course course) {
        taskValidator.validateCourseStatus(course);
        taskValidator.validateStatement(dto.getStatement()); // Certifique-se de que este método lança uma exceção para statements inválidos
        taskValidator.validateOrder(dto.getOrder());
    }
}
