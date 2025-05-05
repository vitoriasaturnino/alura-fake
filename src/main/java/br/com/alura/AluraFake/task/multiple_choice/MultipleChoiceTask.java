package br.com.alura.AluraFake.task.multiple_choice;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import br.com.alura.AluraFake.task.Task;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE") // Define o valor correto do enum para MultipleChoiceTask
@Table(name = "multiple_choice_tasks")
public class MultipleChoiceTask extends Task {
    // Nenhum campo adicional
}
