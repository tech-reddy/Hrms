import { Component, OnInit } from '@angular/core';
import { Task, TaskStatus } from '../../../models/employee';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../../../services/project-tasks/task.service';


@Component({
  selector: 'app-task-detail',
  standalone: false,
  templateUrl: './task-detail.component.html',
  styleUrl: './task-detail.component.scss'
})
export class TaskDetailComponent implements OnInit {
  task!: Task;
  isEditing = false;
  
  constructor(
    private route: ActivatedRoute,
    private taskService: TaskService
  ) {}

  taskStatuses = Object.values(TaskStatus);

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.loadTask(params['id']);
    });
  }

  loadTask(taskId: number) {
    this.taskService.getTask(taskId).subscribe(task => {
      this.task = task;
    });
  }

  updateStatus(newStatus: TaskStatus) {
    this.taskService.updateTask(this.task.id, { status: newStatus })
      .subscribe(updatedTask => {
        this.task = updatedTask;
      });
  }

  statusBadgeClass(status: string): string {
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

  priorityBadgeClass(priority: string): string {
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
}
