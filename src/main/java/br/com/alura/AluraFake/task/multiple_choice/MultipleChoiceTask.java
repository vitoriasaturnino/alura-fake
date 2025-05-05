package br.com.alura.AluraFake.task.multiple_choice;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.Type;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
@Table(name = "multiple_choice_tasks")
public class MultipleChoiceTask extends Task {

    public MultipleChoiceTask() {
    }

    public MultipleChoiceTask(Course course, String statement, int order) {
        setCourse(course);
        setStatement(statement);
        setOrder(order);
        setType(Type.MULTIPLE_CHOICE);
    }
}
