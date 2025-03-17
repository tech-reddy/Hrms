import { Component, Input, OnInit } from '@angular/core';
import { CreateTaskRequest, Task, TaskStatus } from '../../../models/employee';
import { NgModel } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TaskFormComponent } from '../task-form/task-form.component';
import { TaskService } from '../../../services/project-tasks/task.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-task-list',
  standalone: false,
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.scss'
})
export class TaskListComponent implements OnInit {
  @Input() tasks: Task[] = [];
  @Input() showActions = true;
  constructor(
    private modalService: NgbModal,
    private taskService: TaskService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  // Load tasks from the backend via TaskService
  loadTasks(): void {
    this.taskService.getAllTasks().subscribe({
      next: (data: Task[]) => {
        this.tasks = data;
      },
      error: (error) => {
        console.error('Error loading tasks:', error);
      }
    });
  }

  // Return a Bootstrap badge class based on task priority
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

  // Return a Bootstrap badge class based on task status
  statusBadgeClass(status: string): string {
    switch (status.toUpperCase()) {
      case 'OPEN':
        return 'bg-secondary';
      case 'IN_PROGRESS':
        return 'bg-info';
      case 'COMPLETED':
        return 'bg-success';
      case 'BLOCKED':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }

  // Open the Edit Task modal
  openEditModal(task: Task): void {
    const modalRef = this.modalService.open(TaskFormComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.task = task; // Pass the task to the modal
    modalRef.result.then((result) => {
      if (result === 'saved') {
        this.loadTasks(); // Reload tasks after successful edit
      }
    }).catch((error) => {
      console.error('Edit modal dismissed:', error);
    });
  }

   // Opens the modal for creating a new task
   openNewTaskModal(): void {
    const modalRef = this.modalService.open(TaskFormComponent, { size: 'lg', backdrop: 'static' });
    // Set create mode by not providing an existing task and setting isEditMode to false
    modalRef.componentInstance.isEditMode = false;
    modalRef.result.then((result) => {
      if (result === 'saved') {
        this.loadTasks(); // Reload tasks after successful creation
      }
    }).catch((error) => {
      console.error('New task modal dismissed:', error);
    });
  }

  // Delete the task after confirming with the user
  deleteTask(task: Task): void {
    if (confirm(`Are you sure you want to delete task "${task.title}"?`)) {
      this.toastr.info(`Deleting task "${task.title}"...`, 'Deleting Task', { timeOut: 3000 });
      this.taskService.deleteTask(task.id).subscribe({
        next: () => this.loadTasks(),
        error: (error) => console.error('Error deleting task:', error)
      });
    }
  }
}
