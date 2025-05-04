package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {

    public void validateCourseStatus(Course course) {
        if (course.getStatus() != Status.BUILDING) {
            throw new TaskException("Tasks can only be added to courses in BUILDING status.");
        }
    }

    public void validateStatement(String statement) {
        if (statement.length() < 4 || statement.length() > 255) {
            throw new TaskException("Statement must be between 4 and 255 characters.");
        }
    }

    public void validateOrder(int order) {
        if (order <= 0) {
            throw new TaskException("Order must be a positive integer.");
        }
    }
}
