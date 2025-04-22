package com.restapi.fundapi.service;

import com.restapi.fundapi.model.Task;
import com.restapi.fundapi.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return (List<Task>) taskRepository.findAll();

    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }


    public Optional<Task> updateTask(Long id, Task taskForAdjust) {

        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();

            existingTask.setTitle(taskForAdjust.getTitle());
            existingTask.setDescription(taskForAdjust.getDescription());
            existingTask.setCompleted(taskForAdjust.isCompleted());

            return Optional.of(taskRepository.save(existingTask));
        }
        return Optional.empty();
    }

    public Optional<Task> partialUpdateTask(Long id, Task taskForAdjust) {

        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();

            if (taskForAdjust.getTitle() != null) {
                existingTask.setTitle(taskForAdjust.getTitle());
            }
            if (taskForAdjust.getDescription() != null) {
                existingTask.setDescription(taskForAdjust.getDescription());
            }
            if (taskForAdjust.isCompleted()) {
                existingTask.setCompleted(taskForAdjust.isCompleted());
            }

            //taskRepository.save(existingTask);

            return Optional.of(taskRepository.save(existingTask));
        }
        return Optional.empty();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


}
