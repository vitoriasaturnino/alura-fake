package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.course.id = :courseId ORDER BY t.taskOrder ASC")
    List<Task> findByCourseId(@Param("courseId") Long courseId);

    boolean existsByCourseIdAndTaskOrder(Long courseId, int taskOrder); // Corrigido para usar taskOrder

    @Query("SELECT MAX(t.taskOrder) FROM Task t WHERE t.course.id = :courseId")
    Integer findMaxOrderByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT t FROM Task t WHERE t.course.id = :courseId AND t.taskOrder >= :order ORDER BY t.taskOrder ASC")
    List<Task> findByCourseIdAndOrderGreaterThanEqual(@Param("courseId") Long courseId, @Param("order") int order);

    @Query("SELECT t.type, COUNT(t) FROM Task t WHERE t.course.id = :courseId GROUP BY t.type")
    List<Object[]> countTasksByTypeForCourse(@Param("courseId") Long courseId);

    @Query("SELECT CASE WHEN COUNT(t) = (MAX(t.taskOrder) - MIN(t.taskOrder) + 1) THEN TRUE ELSE FALSE END " +
           "FROM Task t WHERE t.course.id = :courseId")
    boolean hasContinuousOrderSequence(@Param("courseId") Long courseId);

    boolean existsByCourseIdAndType(Long id, Type type);

    @Query("SELECT t.taskOrder FROM Task t WHERE t.course.id = :courseId ORDER BY t.taskOrder ASC")
    List<Integer> findTaskOrdersByCourseId(@Param("courseId") Long courseId);
}
