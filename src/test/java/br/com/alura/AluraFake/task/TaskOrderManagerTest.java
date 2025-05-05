package br.com.alura.AluraFake.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskOrderManagerTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskOrderManager taskOrderManager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_CalculateNextOrder_When_TasksExist() {
        when(taskRepository.findMaxOrderByCourseId(1L)).thenReturn(Optional.of(3));

        int nextOrder = taskOrderManager.getNextOrder(1L);

        assertEquals(4, nextOrder);
    }

    @Test
    void should_ValidateContinuousOrder_When_OrderIsValid() {
        when(taskRepository.findTaskOrdersByCourseId(1L)).thenReturn(List.of(1, 2, 3));

        assertDoesNotThrow(() -> taskOrderManager.validateContinuousOrder(1L));
    }

    @Test
    void should_ThrowException_When_OrderIsNotContinuous() {
        when(taskRepository.findTaskOrdersByCourseId(1L)).thenReturn(List.of(1, 3));

        assertThrows(TaskException.class, () -> taskOrderManager.validateContinuousOrder(1L));
    }
}
