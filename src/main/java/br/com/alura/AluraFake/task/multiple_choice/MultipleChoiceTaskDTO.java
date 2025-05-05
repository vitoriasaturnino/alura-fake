package br.com.alura.AluraFake.task.multiple_choice;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.answer_options.AnswerOptionDTO;
import jakarta.validation.constraints.*;
import java.util.List;

public class MultipleChoiceTaskDTO {

    @NotNull
    private Long courseId;

    @NotBlank
    @Size(min = 4, max = 255)
    private String statement;

    @Positive
    private int order;

    @NotNull
    @Size(min = 3, max = 5)
    private List<AnswerOptionDTO> options;

    public MultipleChoiceTaskDTO() {}

    public MultipleChoiceTaskDTO(Long courseId, String statement, int order, List<AnswerOptionDTO> options) {
        this.courseId = courseId;
        this.statement = statement;
        this.order = order;
        this.options = options;
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

    public List<AnswerOptionDTO> getOptions() {
        return options;
    }

    public MultipleChoiceTask toEntity(Course course) {
        MultipleChoiceTask task = new MultipleChoiceTask();
        task.setCourse(course);
        task.setStatement(statement);
        task.setOrder(order);
        return task;
    }
}
