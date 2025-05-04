package br.com.alura.AluraFake.task;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("OPEN_TEXT") // Define o valor correto do enum para OpenTextTask
@Table(name = "open_text_tasks")
public class OpenTextTask extends Task {
    // Nenhum campo adicional
}
