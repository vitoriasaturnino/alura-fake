package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List; // Adicionada a importação da classe List

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Sql("/sql/create-test-course.sql")
    @Test
    void should_CompleteFullTaskCreationFlow() {
        // Verifica se o curso foi criado
        assertThat(courseRepository.findById(1L)).isPresent();

        // Arrange
        OpenTextTaskDTO openTextTask = new OpenTextTaskDTO(1L, "What is Java?", 1);
        SingleChoiceTaskDTO singleChoiceTask = new SingleChoiceTaskDTO(1L, "Choose the correct option", 2, List.of(
                new AnswerOptionDTO("Option A", false),
                new AnswerOptionDTO("Option B", true)
        ));
        MultipleChoiceTaskDTO multipleChoiceTask = new MultipleChoiceTaskDTO(1L, "Select all correct options", 3, List.of(
                new AnswerOptionDTO("Option A", true),
                new AnswerOptionDTO("Option B", true),
                new AnswerOptionDTO("Option C", false)
        ));

        // Act
        var openTextResponse = restTemplate.postForEntity("/task/new/opentext", openTextTask, Void.class);
        var singleChoiceResponse = restTemplate.postForEntity("/task/new/singlechoice", singleChoiceTask, Void.class);
        var multipleChoiceResponse = restTemplate.postForEntity("/task/new/multiplechoice", multipleChoiceTask, Void.class);
        var publishResponse = restTemplate.postForEntity("/course/1/publish", null, Void.class);

        // Assert
        assertThat(openTextResponse.getStatusCode()).isEqualTo(OK);
        assertThat(singleChoiceResponse.getStatusCode()).isEqualTo(OK);
        assertThat(multipleChoiceResponse.getStatusCode()).isEqualTo(OK);
        assertThat(publishResponse.getStatusCode()).isEqualTo(OK);

        var tasks = taskRepository.findByCourseId(1L);
        assertThat(tasks).hasSize(3);
        assertThat(tasks).extracting("order").containsExactly(1, 2, 3);
    }

    @Sql("/sql/create-test-course.sql")
    @Test
    void should_ReorderTasks_When_InsertingInMiddle() {
        // Arrange
        OpenTextTaskDTO firstTask = new OpenTextTaskDTO(1L, "First Task", 1);
        OpenTextTaskDTO secondTask = new OpenTextTaskDTO(1L, "Second Task", 2);
        OpenTextTaskDTO middleTask = new OpenTextTaskDTO(1L, "Middle Task", 2);

        restTemplate.postForEntity("/task/new/opentext", firstTask, Void.class);
        restTemplate.postForEntity("/task/new/opentext", secondTask, Void.class);

        // Act
        var middleTaskResponse = restTemplate.postForEntity("/task/new/opentext", middleTask, Void.class);

        // Assert
        assertThat(middleTaskResponse.getStatusCode()).isEqualTo(OK);

        var tasks = taskRepository.findByCourseId(1L);
        assertThat(tasks).hasSize(3);
        assertThat(tasks).extracting("statement").containsExactly("First Task", "Middle Task", "Second Task");
        assertThat(tasks).extracting("order").containsExactly(1, 2, 3);
    }
}
