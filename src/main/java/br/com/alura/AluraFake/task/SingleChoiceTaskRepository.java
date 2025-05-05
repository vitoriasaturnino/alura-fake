package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SingleChoiceTaskRepository extends JpaRepository<SingleChoiceTask, Long> {
    // Métodos específicos podem ser adicionados aqui, se necessário
    Optional<SingleChoiceTask> findByCourseIdAndStatement(Long courseId, String statement);
}
