package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.task.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CoursePublicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Sql("/sql/create-test-course.sql")
    @Test
    void should_NotPublishCourse_When_TasksAreMissing() {
        // Act
        var publishResponse = restTemplate.postForEntity("/course/1/publish", null, Void.class);

        // Assert
        assertThat(publishResponse.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
}
