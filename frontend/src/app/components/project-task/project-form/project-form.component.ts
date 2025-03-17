import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreateProjectRequest, Employee } from '../../../models/employee';
import { ProjectService } from '../../../services/project-tasks/project.service';
import { EmployeesService } from '../../../services/employees.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-project-form',
  standalone: false,
  templateUrl: './project-form.component.html',
  styleUrl: './project-form.component.scss'
})
export class ProjectFormComponent implements OnInit {
  projectForm: FormGroup;
  employees: Employee[] = [];
  isEditMode = false;
  currentProjectId?: number;

  constructor(
    private fb: FormBuilder,
    private projectService: ProjectService,
    private employeeService: EmployeesService,
    private toastr: ToastrService
  ) {
    this.projectForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      managerId: ['', Validators.required],
      assignedEmployeeIds: [[]]
    });
  }

  ngOnInit() {
    this.loadEmployees();
    // Add logic to populate form if in edit mode
  }

  loadEmployees() {
    this.employeeService.getEmployees().subscribe(employees => {
      this.employees = employees.content;
    });
  }

  onSubmit() {
    if (this.projectForm.valid) {
      const formValue = this.projectForm.value;
      const projectRequest: CreateProjectRequest = {
        ...formValue,
        startDate: new Date(formValue.startDate),
        endDate: new Date(formValue.endDate)
      };

      if (this.isEditMode && this.currentProjectId) {
        this.projectService.updateProject(this.currentProjectId, projectRequest)
          .subscribe({
            next:() => {this.toastr.success('Project updated', 'Success')},
            error: (err) => {this.toastr.error('Failed to update project', 'Error')}
      });
      } else {
        this.projectService.createProject(projectRequest)
          .subscribe({
            next: () =>  {this.toastr.success('Project created', 'Success')},
            error: (err) => {this.toastr.error('Failed to create project', 'Error')}
      });
      }
    }
  }
}
