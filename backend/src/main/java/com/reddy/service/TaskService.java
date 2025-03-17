package com.reddy.service;

import com.reddy.dto.task.CreateTaskRequest;
import com.reddy.dto.task.TaskResponse;
import com.reddy.dto.task.UpdateTaskRequest;
import com.reddy.enums.TaskStatus;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Employee;
import com.reddy.model.Project;
import com.reddy.model.Task;
import com.reddy.repository.EmployeeRepository;
import com.reddy.repository.ProjectRepository;
import com.reddy.repository.TaskRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public Task createTask(CreateTaskRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Employee employee = employeeRepository.findById(request.getAssignedEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Task task = new Task();
        task.setProject(project);
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStartDate(request.getStartDate());
        task.setTitle(request.getTitle());
        task.setAssignedEmployee(employee);
        task.setEndDate(request.getEndDate());
        //Task task = modelMapper.map(request, Task.class);
         //Set fields and relationships
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, @Valid UpdateTaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        // Update fields from request
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setEndDate(request.getEndDate());
        task.setAssignedEmployee(employeeRepository
                .findById(request.getAssignedEmployeeId())
                .orElseThrow(()
                        -> new ResourceNotFoundException("Employee Not found")));
        task.setTitle(request.getTitle());
        return taskRepository.save(task);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .toList();
    }
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getTaskByEmployeeId(Long employeeId) {
        return taskRepository.findByAssignedEmployeeId(employeeId);
    }

    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}