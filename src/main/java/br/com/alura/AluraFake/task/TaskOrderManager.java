package br.com.alura.AluraFake.task;

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
        Integer maxOrder = taskRepository.findMaxOrderByCourseId(courseId);
        return (maxOrder == null) ? 1 : maxOrder + 1;
    }

    @Transactional
    public void reorderTasks(Long courseId, int newOrder) {
        List<Task> tasksToReorder = taskRepository.findByCourseIdAndOrderGreaterThanEqual(courseId, newOrder);
        for (Task task : tasksToReorder) {
            task.setOrder(task.getOrder() + 1);
        }
        taskRepository.saveAll(tasksToReorder);
    }

    public void validateContinuousOrder(Long courseId) {
        boolean isContinuous = taskRepository.hasContinuousOrderSequence(courseId);
        if (!isContinuous) {
            throw new TaskException("Task orders must form a continuous sequence without gaps.");
        }
    }
}
