import { Component, OnInit } from '@angular/core';
import { Employee, Project, Task, TaskStatus } from '../../../models/employee';
import { ProjectService } from '../../../services/project-tasks/project.service';
import { TaskService } from '../../../services/project-tasks/task.service';
import { EmployeesService } from '../../../services/employees.service';

@Component({
  selector: 'app-employee-project',
  standalone: false,
  templateUrl: './employee-project.component.html',
  styleUrl: './employee-project.component.scss',
})
export class EmployeeProjectComponent implements OnInit {
  assignedProjects: Project[] = [];
  assignedTasks: Task[] = [];
  isLoading = true;
  employeeId: number | null = null;
  TaskStatus = TaskStatus;

  constructor(
    private projectService: ProjectService,
    private taskService: TaskService,
    private employeeService: EmployeesService
  ) {}

  ngOnInit() {
    this.employeeService.getCurrentEmployee().subscribe((employee) => {
      this.employeeId = employee.id;
      if (this.employeeId) {
        this.loadAssignedProjects(this.employeeId);
        this.loadAssignedTasks(this.employeeId);
      }
    });
  }

  loadAssignedProjects(employeeId: number) {
    this.projectService
      .getProjectsByEmployee(employeeId)
      .subscribe((projects) => {
        this.assignedProjects = projects;
        this.isLoading = false;
      });
  }

  loadAssignedTasks(employeeId: number) {
    this.taskService.getTasksByEmployee(employeeId).subscribe((tasks) => {
      this.assignedTasks = tasks;
      this.isLoading = false;
    });
  }

  updateTaskStatus(taskId: number, status: TaskStatus) {
    this.taskService
      .updateTaskStatus(taskId, status)
      .subscribe((updatedTask) => {
        const taskIndex = this.assignedTasks.findIndex((t) => t.id === taskId);
        if (taskIndex !== -1) {
          this.assignedTasks[taskIndex] = updatedTask;
        }
      });
  }

  priorityBadgeClass(priority: string | undefined): string {
    if (!priority) {
      return 'bg-secondary'; // fallback for undefined priority
    }
    switch (priority.toUpperCase()) {
      case 'HIGH':
        return 'bg-danger';
      case 'MEDIUM':
        return 'bg-warning';
      case 'LOW':
        return 'bg-success';
      default:
        return 'bg-secondary';
    }
  }

  statusBadgeClass(status: string | undefined): string {
    if (!status) {
      return 'bg-secondary'; // fallback color for undefined
    }
    switch (status) {
      case 'OPEN':
        return 'bg-primary';
      case 'IN_PROGRESS':
        return 'bg-info';
      case 'COMPLETED':
        return 'bg-success';
      case 'ON_HOLD':
        return 'bg-warning';
      case 'CANCELLED':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }
}
