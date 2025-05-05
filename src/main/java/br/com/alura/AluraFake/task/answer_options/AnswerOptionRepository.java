package br.com.alura.AluraFake.task.answer_options;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
    // Métodos específicos podem ser adicionados aqui, se necessário
}
