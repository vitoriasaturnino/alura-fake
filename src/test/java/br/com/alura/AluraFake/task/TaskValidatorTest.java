package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskValidatorTest {

    private TaskValidator taskValidator;

    @BeforeEach
    void setup() {
        taskValidator = new TaskValidator();
    }

    @Test
    void should_ValidateSuccessfully_When_StatementIsValid() {
        assertDoesNotThrow(() -> taskValidator.validateStatement("Valid statement"));
    }

    @Test
    void should_ThrowException_When_StatementIsTooShort() {
        assertThrows(TaskException.class, () -> taskValidator.validateStatement("abc"));
    }

    @Test
    void should_ThrowException_When_CourseStatusIsNotBuilding() {
        User instructor = mock(User.class);
        when(instructor.isInstructor()).thenReturn(true);

        Course course = new Course("Java", "Curso de Java", instructor);
        course.setStatus(Status.PUBLISHED);

        assertThrows(TaskException.class, () -> taskValidator.validateCourseStatus(course));
    }
}
