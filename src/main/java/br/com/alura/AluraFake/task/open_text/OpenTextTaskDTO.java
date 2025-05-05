package br.com.alura.AluraFake.task.open_text;

import br.com.alura.AluraFake.course.Course;
import jakarta.validation.constraints.*;

public class OpenTextTaskDTO {

    @NotNull
    private Long courseId;

    @NotBlank
    @Size(min = 4, max = 255)
    private String statement;

    @Positive
    private int order;

    public OpenTextTaskDTO() {}

    public OpenTextTaskDTO(Long courseId, String statement, int order) {
        this.courseId = courseId;
        this.statement = statement;
        this.order = order;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getStatement() {
        return statement;
    }

    public int getOrder() {
        return order;
    }

    public OpenTextTask toEntity(Course course) {
        OpenTextTask task = new OpenTextTask();
        task.setCourse(course);
        task.setStatement(statement);
        task.setOrder(order);
        return task;
    }
}
