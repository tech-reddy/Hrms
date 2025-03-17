import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  CreateTaskRequest,
  Employee,
  Project,
  Task,
} from '../../../models/employee';
import { TaskService } from '../../../services/project-tasks/task.service';
import { EmployeesService } from '../../../services/employees.service';
import { ProjectService } from '../../../services/project-tasks/project.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-task-form',
  standalone: false,
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.scss',
})
export class TaskFormComponent implements OnInit {
  @Input() task!: Task;
  taskForm: FormGroup;
  employees: Employee[] = [];
  projects: Project[] = [];
  isEditMode = false;

  // Define the available priorities
  taskPriorities: string[] = ['LOW', 'MEDIUM', 'HIGH'];

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private employeeService: EmployeesService,
    private projectService: ProjectService,
    public activeModal: NgbActiveModal,
    private toastr: ToastrService
  ) {
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      priority: ['MEDIUM', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      assignedEmployeeId: ['', Validators.required],
      projectId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadEmployees();
    this.loadProjects();
    if (this.task) {
      this.taskForm.patchValue({
        ...this.task,
        startDate: new Date(this.task.startDate).toISOString().split('T')[0],
        endDate: new Date(this.task.endDate).toISOString().split('T')[0],
      });
    }
  }

  loadEmployees(): void {
    this.employeeService.getEmployees().subscribe((employees) => {
      // Assuming employees.content contains an array of Employee objects.
      this.employees = employees.content;
    });
  }

  loadProjects(): void {
    this.projectService.getAllProjects().subscribe((projects) => {
      this.projects = projects;
    });
  }

  onSubmit(): void {
    if (this.taskForm.valid) {
      const formValue = this.taskForm.value;
      const taskRequest: CreateTaskRequest = {
        ...formValue,
        startDate: new Date(formValue.startDate),
        endDate: new Date(formValue.endDate),
      };

      if (this.isEditMode) {
        this.taskService.updateTask(this.task.id, taskRequest).subscribe({
          next: () => {
            this.activeModal.close('saved');
            this.toastr.success('Task updated successfully', 'Success');
            console.log('Task updated successfully');
          },
          error: (err) => {
            console.error(err);
            this.toastr.error('Failed to update task', 'Error');
            console.log('Failed to update task');
          },
        });
      } else {
        this.taskService.createTask(taskRequest).subscribe({
          next: () => {
            // Handle success, e.g. show a success message, reset the form, etc.
            this.activeModal.close('saved');
            this.toastr.success('Task created successfully', 'Success');
            console.log('Task created successfully');
            this.taskForm.reset();
          },
          error: (err) => {
            // Handle error, e.g. show an error message
            this.toastr.error('Failed to create task');
            console.error(err);
            console.log('Failed to create task');
          },
        });
      }
    }
  }
}
