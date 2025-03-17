import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Task, TaskStatus } from '../../../models/employee';
import { TaskService } from '../../../services/project-tasks/task.service';


@Component({
  selector: 'app-employee-task',
  standalone: false,
  templateUrl: './employee-task.component.html',
  styleUrl: './employee-task.component.scss'
})
export class EmployeeTaskComponent implements OnInit {
  task!: Task;
  isUpdatingStatus = false;
  taskStatuses = Object.values(TaskStatus);


  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService
  ) {}

  ngOnInit() {
    const taskId = this.route.snapshot.params['id'];
    this.loadTask(taskId);
  }

  loadTask(taskId: number) {
    this.taskService.getTask(taskId).subscribe(task => {
      this.task = task;
    });
  }

  updateStatus(newStatus: TaskStatus) {
    this.isUpdatingStatus = true;
    this.taskService.updateTaskStatus(this.task.id, newStatus).subscribe(updatedTask => {
      this.task = updatedTask;
      this.isUpdatingStatus = false;
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
