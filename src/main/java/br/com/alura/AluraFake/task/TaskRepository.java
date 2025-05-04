package br.com.alura.AluraFake.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCourseId(Long courseId);

    boolean existsByCourseIdAndOrder(Long courseId, int order);

    @Query("SELECT MAX(t.order) FROM Task t WHERE t.course.id = :courseId")
    Integer findMaxOrderByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT t FROM Task t WHERE t.course.id = :courseId AND t.order >= :order")
    List<Task> findByCourseIdAndOrderGreaterThanEqual(@Param("courseId") Long courseId, @Param("order") int order);

    @Query("SELECT t.type, COUNT(t) FROM Task t WHERE t.course.id = :courseId GROUP BY t.type")
    List<Object[]> countTasksByTypeForCourse(@Param("courseId") Long courseId);

    @Query("SELECT COUNT(t) = (MAX(t.order) - MIN(t.order) + 1) FROM Task t WHERE t.course.id = :courseId")
    boolean hasContinuousOrderSequence(@Param("courseId") Long courseId);

    boolean existsByCourseIdAndType(Long id, Type type);
}
