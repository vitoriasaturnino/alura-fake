package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleChoiceTaskRepository extends JpaRepository<SingleChoiceTask, Long> {
    // Métodos específicos podem ser adicionados aqui, se necessário
}
