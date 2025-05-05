package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.answer_options.AnswerOptionRepository;
import br.com.alura.AluraFake.task.multiple_choice.MultipleChoiceTaskRepository;
import br.com.alura.AluraFake.task.validation.MultipleChoiceTaskValidator;
import br.com.alura.AluraFake.task.open_text.OpenTextTaskDTO;
import br.com.alura.AluraFake.task.open_text.OpenTextTaskRepository;
import br.com.alura.AluraFake.task.validation.OpenTextTaskValidator;
import br.com.alura.AluraFake.task.single_choice.SingleChoiceTaskRepository;
import br.com.alura.AluraFake.task.validation.SingleChoiceTaskValidator;
import br.com.alura.AluraFake.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private OpenTextTaskRepository openTextTaskRepository;

    @MockBean
    private SingleChoiceTaskRepository singleChoiceTaskRepository;

    @MockBean
    private MultipleChoiceTaskRepository multipleChoiceTaskRepository;

    @MockBean
    private AnswerOptionRepository answerOptionRepository;

    @MockBean
    private TaskOrderManager taskOrderManager;

    @MockBean
    private TaskValidator taskValidator;

    @MockBean
    private OpenTextTaskValidator openTextTaskValidator;

    @MockBean
    private SingleChoiceTaskValidator singleChoiceTaskValidator;

    @MockBean
    private MultipleChoiceTaskValidator multipleChoiceTaskValidator;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_CreateOpenTextTask_When_DataIsValid() throws Exception {
        User instructor = mock(User.class);
        when(instructor.isInstructor()).thenReturn(true);

        Course course = new Course("Java", "Curso de Java", instructor);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(taskOrderManager.getNextOrder(1L)).thenReturn(1);

        OpenTextTaskDTO dto = new OpenTextTaskDTO(1L, "O que aprendemos hoje?", 1);

        mockMvc.perform(post("/task/new/opentext")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnBadRequest_When_OpenTextTaskStatementIsInvalid() throws Exception {
        User instructor = mock(User.class);
        when(instructor.isInstructor()).thenReturn(true);

        Course course = new Course("Java", "Curso de Java", instructor);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Configura o mock para lançar uma exceção ao validar o statement inválido
        doThrow(new TaskException("Statement must be between 4 and 255 characters."))
                .when(openTextTaskValidator).validate(any(OpenTextTaskDTO.class), eq(course));

        OpenTextTaskDTO dto = new OpenTextTaskDTO(1L, "abc", 1);

        mockMvc.perform(post("/task/new/opentext")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.field").value("task"))
                .andExpect(jsonPath("$.message").value("Statement must be between 4 and 255 characters."));
    }
}
