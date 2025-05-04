package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import jakarta.validation.constraints.*;

public class TaskDTO {

    @NotNull
    private Long courseId;

    @NotBlank
    @Size(min = 4, max = 255)
    private String statement;

    @Positive
    private int order;

    @NotNull
    private Type type;

    public TaskDTO() {}

    public TaskDTO(Long courseId, String statement, int order, Type type) {
        this.courseId = courseId;
        this.statement = statement;
        this.order = order;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public Task toEntity() {
        Task task = new Task() {
            // Task is abstract, so we provide an anonymous implementation
        };
        task.setStatement(statement);
        task.setOrder(order);
        return task;
    }
}
