package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.TaskOrderManager;
import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoursePublicationValidatorTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskOrderManager taskOrderManager;

    @InjectMocks
    private CoursePublicationValidator coursePublicationValidator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_ValidateSuccessfully_When_CourseMeetsRequirements() {
        User instructor = mock(User.class);
        when(instructor.isInstructor()).thenReturn(true);

        Course course = new Course("Java", "Curso de Java", instructor);
        course.setId(1L); // Certifique-se de que o ID do curso está configurado
        course.setStatus(Status.BUILDING);

        // Configura os mocks para retornar true para todos os tipos de tarefas
        when(taskRepository.existsByCourseIdAndType(1L, Type.OPEN_TEXT)).thenReturn(true);
        when(taskRepository.existsByCourseIdAndType(1L, Type.SINGLE_CHOICE)).thenReturn(true);
        when(taskRepository.existsByCourseIdAndType(1L, Type.MULTIPLE_CHOICE)).thenReturn(true);

        assertDoesNotThrow(() -> coursePublicationValidator.validate(course));
    }

    @Test
    void should_ThrowException_When_CourseDoesNotHaveAllTaskTypes() {
        User instructor = mock(User.class);
        when(instructor.isInstructor()).thenReturn(true);

        Course course = new Course("Java", "Curso de Java", instructor);
        course.setStatus(Status.BUILDING);

        when(taskRepository.existsByCourseIdAndType(1L, Type.OPEN_TEXT)).thenReturn(false);

        assertThrows(CoursePublicationException.class, () -> coursePublicationValidator.validate(course));
    }
}
