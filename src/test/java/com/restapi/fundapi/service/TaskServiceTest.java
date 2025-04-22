package com.restapi.fundapi.service;

import com.restapi.fundapi.model.Task;
import com.restapi.fundapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Criar tarefa deve retornar tarefa criada
    @Test

    void createTask_ShouldReturnSavedTask() {
        // Arrange
        Task task = new Task();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        Task result = taskService.createTask(task);

        // Assert
        assertNotNull(result);
        verify(taskRepository, times(1)).save(task);
    }

    //Criar tarefa deve retornar exceção quando a tarefa for nula
    @Test
    void createTask_ShouldHandleNullTask() {
        // Arrange
        when(taskRepository.save(null)).thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(null));
    }

    // Obter todas as tarefas devem retornar lista de tarefas
    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        // Arrange
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    //A tarefa específica deve retornar a tarefa quando a tarefa existir
    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {
        // Arrange
        Task task = new Task();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act
        Optional<Task> result = taskService.getTaskById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(task, result.get());
        verify(taskRepository, times(1)).findById(1L);
    }

    //A tarefa específica deve retornar vazio quando a tarefa não existir
    @Test
    void getTaskById_ShouldReturnEmpty_WhenTaskDoesNotExist() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Task> result = taskService.getTaskById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    //A tarefa de atualização deve retornar a tarefa atualizada quando a tarefa existir
    @Test
    void updateTask_ShouldReturnUpdatedTask_WhenTaskExists() {
        // Arrange
        Task existingTask = new Task();
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Act
        Optional<Task> result = taskService.updateTask(1L, updatedTask);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    //A tarefa de atualização deve retornar vazio quando a tarefa não existir
    @Test
    void updateTask_ShouldReturnEmpty_WhenTaskDoesNotExist() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Task> result = taskService.updateTask(1L, new Task());

        // Assert
        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    //A tarefa de atualização parcial deve atualizar apenas campos não nulos
    @Test
    void partialUpdateTask_ShouldUpdateOnlyNonNullFields() {
        // Arrange
        Task existingTask = new Task();
        existingTask.setTitle("Old Title");
        Task taskForAdjust = new Task();
        taskForAdjust.setTitle("New Title");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        // Act
        Optional<Task> result = taskService.partialUpdateTask(1L, taskForAdjust);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    //A tarefa de atualização parcial deve retornar vazio quando a tarefa não existir
    @Test
    void partialUpdateTask_ShouldReturnEmpty_WhenTaskDoesNotExist() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Task> result = taskService.partialUpdateTask(1L, new Task());

        // Assert
        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    //A tarefa de exclusão deve invocar deleteById
    @Test
    void deleteTask_ShouldInvokeDeleteById() {
        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).deleteById(1L);
    }

    //A tarefa de exclusão deve lidar com ID inexistente
    @Test
    void deleteTask_ShouldHandleNonExistentId() {
        // Arrange
        doNothing().when(taskRepository).deleteById(1L);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).deleteById(1L);
    }
}