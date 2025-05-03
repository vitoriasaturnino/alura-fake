package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MultipleChoiceTaskRepository extends JpaRepository<MultipleChoiceTask, Long> {
    // Métodos específicos podem ser adicionados aqui, se necessário
}
