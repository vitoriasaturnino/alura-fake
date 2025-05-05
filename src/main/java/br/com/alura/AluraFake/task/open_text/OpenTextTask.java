package br.com.alura.AluraFake.task.open_text;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.Type;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("OPEN_TEXT")
@Table(name = "open_text_tasks")
public class OpenTextTask extends Task {

    public OpenTextTask() {
    }

    public OpenTextTask(Course course, String statement, int order) {
        setCourse(course);
        setStatement(statement);
        setOrder(order);
        setType(Type.OPEN_TEXT);
    }
}
