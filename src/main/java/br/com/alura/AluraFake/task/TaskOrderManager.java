package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskOrderManager {

    private final TaskRepository taskRepository;

    public TaskOrderManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public int getNextOrder(Long courseId) {
        return taskRepository.findMaxOrderByCourseId(courseId).orElse(0) + 1;
    }

    @Transactional
    public void reorderTasks(Long courseId, int order) {
        List<Task> tasksToReorder = taskRepository.findByCourseIdAndOrderGreaterThanEqual(courseId, order, Sort.by("order"));

        if (tasksToReorder.isEmpty()) {
            return;
        }

        for (Task task : tasksToReorder) {
            task.setOrder(task.getOrder() + 1000); 
            taskRepository.save(task);
        }

        taskRepository.flush();

        for (Task task : tasksToReorder) {
            task.setOrder(task.getOrder() - 999);
            taskRepository.save(task);
        }

        taskRepository.flush();
    }

    @Transactional
    public void validateContinuousOrder(Long courseId) {
        List<Integer> taskOrders = taskRepository.findTaskOrdersByCourseId(courseId);
        for (int i = 1; i <= taskOrders.size(); i++) {
            if (!taskOrders.contains(i)) {
                throw new TaskException("Task orders must form a continuous sequence without gaps.");
            }
        }
    }

    public void validatePreviousOrderExists(Long courseId, int order) {
        if (order > 1 && !taskRepository.existsByCourseIdAndOrder(courseId, order - 1)) {
            throw new TaskException("The previous order does not exist. Task orders must be continuous.");
        }
    }
}
