package com.restapi.fundapi.controller;

import com.restapi.fundapi.exception.ResourceNotFoundException;
import com.restapi.fundapi.model.Task;
import com.restapi.fundapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {

        Task registeredTask = taskService.createTask(task);
        return new ResponseEntity<>(registeredTask, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAlltasks() {

        List<Task> existingTasks = taskService.getAllTasks();
        return ResponseEntity.ok(existingTasks);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> specificTask(@PathVariable Long id) {

        Optional<Task> searchedTask = taskService.getTaskById(id);

        if (searchedTask.isEmpty()) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        return ResponseEntity.ok(searchedTask.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskForAdjust) {

        Optional<Task> adjustedTask = taskService.updateTask(id, taskForAdjust);

        if(adjustedTask.isEmpty()) {
            throw  new ResourceNotFoundException("Task not found with id: " + id);
        }
        return ResponseEntity.ok(adjustedTask.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> partialUpdateTask(@PathVariable Long id, @RequestBody Task taskForAdjust) {

        Optional<Task> adjustedTask = taskService.partialUpdateTask(id, taskForAdjust);

        if(adjustedTask.isEmpty()) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        return ResponseEntity.ok(adjustedTask.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        Optional<Task> taskToDelete = taskService.getTaskById(id);

        if (taskToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Task not found with id: " + id);

        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
