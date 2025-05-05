package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MultipleChoiceTaskRepository extends JpaRepository<MultipleChoiceTask, Long> {
    // Métodos específicos podem ser adicionados aqui, se necessário
    Optional<MultipleChoiceTask> findByCourseIdAndStatement(Long courseId, String statement);
}
