package br.com.alura.AluraFake.task;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.course.id = :courseId ORDER BY t.order ASC")
    List<Task> findByCourseId(@Param("courseId") Long courseId);

    boolean existsByCourseIdAndOrder(Long courseId, int order); // Corrigido para usar 'order'

    @Query("SELECT t FROM Task t WHERE t.course.id = :courseId AND t.order >= :order ORDER BY t.order ASC")
    List<Task> findByCourseIdAndOrderGreaterThanEqual(@Param("courseId") Long courseId, @Param("order") int order, Sort sort);

    @Query("SELECT t.type, COUNT(t) FROM Task t WHERE t.course.id = :courseId GROUP BY t.type")
    List<Object[]> countTasksByTypeForCourse(@Param("courseId") Long courseId);

    @Query("SELECT CASE WHEN COUNT(t) = (MAX(t.order) - MIN(t.order) + 1) THEN TRUE ELSE FALSE END " +
           "FROM Task t WHERE t.course.id = :courseId")
    boolean hasContinuousOrderSequence(@Param("courseId") Long courseId);

    boolean existsByCourseIdAndType(Long id, Type type);

    @Query("SELECT t.order FROM Task t WHERE t.course.id = :courseId ORDER BY t.order ASC")
    List<Integer> findTaskOrdersByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT MAX(t.order) FROM Task t WHERE t.course.id = :courseId")
    Optional<Integer> findMaxOrderByCourseId(@Param("courseId") Long courseId);
}
