package com.example.trello.service;

import com.example.trello.dto.TaskDTO;
import com.example.trello.mapper.TaskMapper;
import com.example.trello.model.Task;
import com.example.trello.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElse(null);
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        System.out.println("Creating task with details: " + taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        if (taskRepository.existsById(id)) {
            Task task = taskMapper.toEntity(taskDTO);
            task.setId(id);
            Task updatedTask = taskRepository.save(task);
            return taskMapper.toDTO(updatedTask);
        }
        return null;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
