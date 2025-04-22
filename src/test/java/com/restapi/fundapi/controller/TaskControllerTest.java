package com.restapi.fundapi.controller;

import com.restapi.fundapi.exception.ResourceNotFoundException;
import com.restapi.fundapi.model.Task;
import com.restapi.fundapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Criar tarefa deve retornar tarefa criada
    @Test
    void createTask_ShouldReturnCreatedTask() {
        // Arrange
        Task task = new Task();
        when(taskService.createTask(task)).thenReturn(task);

        // Act
        ResponseEntity<Task> response = taskController.createTask(task);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).createTask(task);
    }

    //Obter todas as tarefas devem retornar lista de tarefas
    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        // Arrange
        List<Task> tasks = Collections.singletonList(new Task());
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Act
        ResponseEntity<List<Task>> response = taskController.getAlltasks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
        verify(taskService, times(1)).getAllTasks();
    }

    //Tarefa específica deve retornar tarefa quando a tarefa existir
    @Test
    void specificTask_ShouldReturnTask_WhenTaskExists() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        // Act
        ResponseEntity<Task> response = taskController.specificTask(taskId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    //Tarefa específica deve lançar exceção quando a tarefa não existir
    @Test
    void specificTask_ShouldThrowException_WhenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;
        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskController.specificTask(taskId));
        verify(taskService, times(1)).getTaskById(taskId);
    }

    //Atualizar tarefa deve retornar tarefa atualizada quando a tarefa existir
    @Test
    void updateTask_ShouldReturnUpdatedTask_WhenTaskExists() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.updateTask(taskId, task)).thenReturn(Optional.of(task));

        // Act
        ResponseEntity<Task> response = taskController.updateTask(taskId, task);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).updateTask(taskId, task);
    }

    //Atualizar tarefa deve lançar exceção quando a tarefa não existir
    @Test
    void updateTask_ShouldThrowException_WhenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.updateTask(taskId, task)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskController.updateTask(taskId, task));
        verify(taskService, times(1)).updateTask(taskId, task);
    }

    //Atualizar parcialmente a tarefa deve retornar tarefa atualizada quando a tarefa existir
    @Test
    void partialUpdateTask_ShouldReturnUpdatedTask_WhenTaskExists() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.partialUpdateTask(taskId, task)).thenReturn(Optional.of(task));

        // Act
        ResponseEntity<Task> response = taskController.partialUpdateTask(taskId, task);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
        verify(taskService, times(1)).partialUpdateTask(taskId, task);
    }

    //Atualizar parcialmente a tarefa deve lançar exceção quando a tarefa não existir
    @Test
    void partialUpdateTask_ShouldThrowException_WhenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.partialUpdateTask(taskId, task)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskController.partialUpdateTask(taskId, task));
        verify(taskService, times(1)).partialUpdateTask(taskId, task);
    }

    //Deletar tarefa deve retornar NoContent quando a tarefa existir
    @Test
    void deleteTask_ShouldReturnNoContent_WhenTaskExists() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        // Act
        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(taskId);
    }

    //Deletar tarefa deve lançar exceção quando a tarefa não existir
    @Test
    void deleteTask_ShouldThrowException_WhenTaskDoesNotExist() {
        // Arrange
        Long taskId = 1L;
        when(taskService.getTaskById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> taskController.deleteTask(taskId));
        verify(taskService, times(1)).getTaskById(taskId);
    }


}
