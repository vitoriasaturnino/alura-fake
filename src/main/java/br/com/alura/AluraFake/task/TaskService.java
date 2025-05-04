package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.task.TaskDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Task createTask(TaskDTO taskDTO) {
        Course course = courseRepository.findById(taskDTO.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + taskDTO.getCourseId()));

        int requestedOrder = taskDTO.getOrder();

        // Validar se a ordem não cria "saltos" na sequência
        validateTaskOrderSequence(course.getId(), requestedOrder);

        // Reordenar tarefas existentes usando abordagem em duas fases
        reorderTasksForInsertion(course.getId(), requestedOrder);

        // Criar e salvar a nova tarefa
        Task task = new Task() {
            // Implementação anônima para a classe abstrata Task
        };
        task.setCourse(course);
        task.setStatement(taskDTO.getStatement());
        task.setOrder(requestedOrder);
        task.setType(taskDTO.getType());

        return taskRepository.save(task);
    }

    private void validateTaskOrderSequence(Long courseId, int requestedOrder) {
        if (requestedOrder <= 0) {
            throw new IllegalArgumentException("Task order must be positive");
        }

        int maxOrder = taskRepository.findMaxOrderByCourseId(courseId).orElse(0);

        if (requestedOrder > maxOrder + 1) {
            throw new IllegalArgumentException(
                    "Invalid task order. The maximum allowed order is " + (maxOrder + 1) +
                            " since the current highest order is " + maxOrder);
        }
    }

    private void reorderTasksForInsertion(Long courseId, int newTaskOrder) {
        List<Task> tasksToReorder = taskRepository.findByCourseIdAndOrderGreaterThanEqual(courseId, newTaskOrder, Sort.by("order"));

        if (tasksToReorder.isEmpty()) {
            return;
        }

        int offset = tasksToReorder.size() + 1;

        // FASE 1: Move todas as tarefas afetadas para valores negativos temporários
        for (Task task : tasksToReorder) {
            task.setOrder(-1 * (task.getOrder() + offset));
            taskRepository.save(task);
        }

        taskRepository.flush();

        // FASE 2: Move todas as tarefas para suas posições finais
        for (Task task : tasksToReorder) {
            int originalPosition = -1 * task.getOrder() - offset;
            task.setOrder(originalPosition + 1);
            taskRepository.save(task);
        }

        taskRepository.flush();
    }
}
