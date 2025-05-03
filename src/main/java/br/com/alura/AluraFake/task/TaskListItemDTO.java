package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.task.Task;
import br.com.alura.AluraFake.task.Type;

public class TaskListItemDTO {

    private Long id;
    private String statement;
    private int order;
    private Type type;

    public TaskListItemDTO(Task task) {
        this.id = task.getId();
        this.statement = task.getStatement();
        this.order = task.getOrder();
        this.type = task.getType();
    }

    public Long getId() {
        return id;
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
}
