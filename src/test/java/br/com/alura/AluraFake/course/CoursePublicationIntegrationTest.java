package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.TaskRepository;
import br.com.alura.AluraFake.task.open_text.OpenTextTask;
import br.com.alura.AluraFake.task.single_choice.SingleChoiceTask;
import br.com.alura.AluraFake.task.multiple_choice.MultipleChoiceTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CoursePublicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @Sql("/sql/create-test-course.sql")
    void should_PublishCourse_When_RequirementsAreMet() {

        var course = courseRepository.findById(1L).orElseThrow();
        taskRepository.saveAll(List.of(
                new OpenTextTask(course, "OpenText Task", 1),
                new SingleChoiceTask(course, "SingleChoice Task", 2),
                new MultipleChoiceTask(course, "MultipleChoice Task", 3)
        ));

        var response = restTemplate.postForEntity("/course/1/publish", null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        var updatedCourse = courseRepository.findById(1L).orElseThrow();
        assertThat(updatedCourse.getStatus()).isEqualTo(Status.PUBLISHED);
    }
}
