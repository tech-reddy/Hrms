package com.reddy.controller;

import com.reddy.dto.project.EmployeeSimpleResponse;
import com.reddy.dto.project.ProjectSimpleResponse;
import com.reddy.dto.task.CreateTaskRequest;
import com.reddy.dto.task.TaskResponse;
import com.reddy.dto.task.UpdateTaskRequest;
import com.reddy.enums.TaskStatus;
import com.reddy.model.Task;
import com.reddy.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {
    private TaskService taskService;
    private ModelMapper modelMapper;

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(convertToTaskResponse(task));
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskResponse>> getTaskByEmployeeId(@PathVariable Long employeeId) {
        List<Task> task = taskService.getTaskByEmployeeId(employeeId);
        return ResponseEntity.ok(task.stream().map(this::convertToTaskResponse).toList());
    }


    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks= taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(request);
        return ResponseEntity.ok(convertToTaskResponse(task));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskRequest request) {
        Task task = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(convertToTaskResponse(task));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody TaskStatus status) {
        Task task = taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok(convertToTaskResponse(task));
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    private TaskResponse convertToTaskResponse(Task task) {
        TaskResponse response = modelMapper.map(task, TaskResponse.class);
        response.setAssignedEmployee(modelMapper.map(task.getAssignedEmployee(), EmployeeSimpleResponse.class));
        response.setProject(modelMapper.map(task.getProject(), ProjectSimpleResponse.class));
        return response;
    }
}