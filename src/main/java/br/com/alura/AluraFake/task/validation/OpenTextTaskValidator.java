package br.com.alura.AluraFake.task.validation;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.TaskValidator;
import br.com.alura.AluraFake.task.open_text.OpenTextTaskDTO; // Corrigido o caminho do DTO

import org.springframework.stereotype.Component;

@Component
public class OpenTextTaskValidator {

    private final TaskValidator taskValidator;

    public OpenTextTaskValidator(TaskValidator taskValidator) {
        this.taskValidator = taskValidator;
    }

    public void validate(OpenTextTaskDTO dto, Course course) {
        taskValidator.validateCourseStatus(course);
        taskValidator.validateStatement(dto.getStatement());
        taskValidator.validateOrder(dto.getOrder());
    }
}
