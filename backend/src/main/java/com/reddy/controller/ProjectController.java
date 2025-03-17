package com.reddy.controller;

import com.reddy.dto.project.CreateProjectRequest;
import com.reddy.dto.project.EmployeeSimpleResponse;
import com.reddy.dto.project.ProjectResponse;
import com.reddy.dto.project.UpdateProjectRequest;
import com.reddy.model.Project;
import com.reddy.service.ProjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
    private ProjectService projectService;
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects()
                .stream()
                .map(this::convertToProjectResponse)
                .collect(Collectors.toList()));
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(convertToProjectResponse(project));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ProjectResponse>> getProjectByEmployeeId(@PathVariable Long employeeId) {
        List<Project> project = projectService.getProjectByEmployeeId(employeeId);
        return ResponseEntity.ok(project.stream().map(this::convertToProjectResponse).toList());
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest request) {
        Project project = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToProjectResponse(project));
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(Long projectId) {
        projectService.deleteProject(projectId);
    }


    @PutMapping("/{projectId}")
    public ProjectResponse updateProject(@PathVariable Long projectId, @RequestBody UpdateProjectRequest request) {
        System.out.println(projectId);
        return projectService.updateProject(projectId, request);
    }

    private ProjectResponse convertToProjectResponse(Project project) {
        ProjectResponse response = modelMapper.map(project, ProjectResponse.class);
        response.setManager(modelMapper.map(project.getManager(), EmployeeSimpleResponse.class));
        response.setAssignedEmployees(project.getAssignedEmployees().stream()
                .map(employee -> modelMapper.map(employee, EmployeeSimpleResponse.class))
                .collect(Collectors.toList()));
        return response;
    }
}