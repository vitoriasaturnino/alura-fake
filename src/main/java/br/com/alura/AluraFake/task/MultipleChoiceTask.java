package br.com.alura.AluraFake.task;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE") // Define o valor correto do enum para MultipleChoiceTask
@Table(name = "multiple_choice_tasks")
public class MultipleChoiceTask extends Task {
    // Nenhum campo adicional
}
