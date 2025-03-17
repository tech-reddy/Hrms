package com.reddy.service;

import com.reddy.dto.project.CreateProjectRequest;
import com.reddy.dto.project.ProjectResponse;
import com.reddy.dto.project.UpdateProjectRequest;
import com.reddy.enums.ProjectStatus;
import com.reddy.exceptions.ResourceNotFoundException;
import com.reddy.model.Employee;
import com.reddy.model.Project;
import com.reddy.repository.EmployeeRepository;
import com.reddy.repository.ProjectRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<Project> getAllProjects() { return projectRepository.findAll(); }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    public Project createProject(@Valid CreateProjectRequest request) {
        Employee manager = employeeRepository.findById(request.getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
        Set<Employee> assignedEmployees = new HashSet<>(employeeRepository.findAllById(request.getAssignedEmployeeIds()));

        //Project project = modelMapper.map(request, Project.class);
        Project project = convertCreateProjectRequestToProject(request);
        project.setAssignedEmployees(assignedEmployees);
        project.setManager(manager);
        // Set fields from request and relationships
        return projectRepository.save(project);
    }

    public ProjectResponse updateProject(Long projectId, UpdateProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setEndDate(request.getEndDate());
        project.setStatus(request.getStatus());
        //project.setAssignedEmployees(new HashSet<>(employeeRepository.findAllById(request.getAssignedEmployeeIds())));
        return modelMapper.map(projectRepository.save(project), ProjectResponse.class);
    }

    public void deleteProject(Long projectId) {
        projectRepository.delete(projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found")));
    }

    private Project convertCreateProjectRequestToProject(CreateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        //Set default status if not provided; adjust as needed
        project.setStatus(ProjectStatus.PLANNING);
        project.setManager(employeeRepository.findById(request.getManagerId()).orElseThrow(
                () -> new ResourceNotFoundException("Manager not found")
        ) );
        project.setAssignedEmployees(new HashSet<>(employeeRepository.findAllById(request.getAssignedEmployeeIds())));
        // Tasks list is empty on creation
        project.setTasks(null); // or new ArrayList<>(), depending on your design

        return project;
    }

    public List<Project> getProjectByEmployeeId(Long employeeId) {
        return projectRepository.findByAssignedEmployeesId(employeeId);
    }
}