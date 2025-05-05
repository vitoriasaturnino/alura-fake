package br.com.alura.AluraFake.task.single_choice;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.Type; 
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("SINGLE_CHOICE")
@Table(name = "single_choice_tasks")
public class SingleChoiceTask extends Task {

    public SingleChoiceTask() {
    }

    public SingleChoiceTask(Course course, String statement, int order) {
        setCourse(course);
        setStatement(statement);
        setOrder(order);
        setType(Type.SINGLE_CHOICE);
    }
}
