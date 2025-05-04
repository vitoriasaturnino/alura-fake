package br.com.alura.AluraFake.task;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SINGLE_CHOICE") // Define o valor correto do enum para SingleChoiceTask
@Table(name = "single_choice_tasks")
public class SingleChoiceTask extends Task {
    // Nenhum campo adicional
}
