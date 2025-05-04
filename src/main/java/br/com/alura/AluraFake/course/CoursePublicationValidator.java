package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.TaskOrderManager;
import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.course.Status;
import org.springframework.stereotype.Component;

@Component
public class CoursePublicationValidator {

    private final TaskRepository taskRepository;
    private final TaskOrderManager taskOrderManager;

    public CoursePublicationValidator(TaskRepository taskRepository, TaskOrderManager taskOrderManager) {
        this.taskRepository = taskRepository;
        this.taskOrderManager = taskOrderManager;
    }

    public void validate(Course course) {
        if (course.getStatus() != Status.BUILDING) {
            throw new CoursePublicationException("Course must be in BUILDING status to be published.");
        }

        if (!taskRepository.existsByCourseIdAndType(course.getId(), Type.OPEN_TEXT)) {
            throw new CoursePublicationException("Course must have at least one OpenTextTask.");
        }

        if (!taskRepository.existsByCourseIdAndType(course.getId(), Type.SINGLE_CHOICE)) {
            throw new CoursePublicationException("Course must have at least one SingleChoiceTask.");
        }

        if (!taskRepository.existsByCourseIdAndType(course.getId(), Type.MULTIPLE_CHOICE)) {
            throw new CoursePublicationException("Course must have at least one MultipleChoiceTask.");
        }

        taskOrderManager.validateContinuousOrder(course.getId());
    }
}
