package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenTextTaskRepository extends JpaRepository<OpenTextTask, Long> {
    // Métodos específicos podem ser adicionados aqui, se necessário
    Optional<OpenTextTask> findByCourseIdAndStatement(Long courseId, String statement);
}
