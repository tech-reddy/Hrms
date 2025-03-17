import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Department, Employee } from '../../../models/employee';
import { DepartmentService } from '../../../services/department/department.service';
import { EmployeesService } from '../../../services/employees.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-department-form',
  templateUrl: './department-form.component.html',
  styleUrls: ['./department-form.component.css'],
  standalone: false
})
export class DepartmentFormComponent implements OnInit {
  @Input() department?: Department;
  form: FormGroup;
  employees: Employee[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private departmentService: DepartmentService,
    private employeeService: EmployeesService,
    private toastr: ToastrService
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      code: ['', [Validators.required, Validators.pattern(/^[A-Z]{2,5}$/)]],
      managerId: [null]
    });
  }

  ngOnInit(): void {
    this.loadEmployees();
    if (this.department) {
      this.form.patchValue({
        ...this.department,
        managerId: this.department.manager?.id
      });
    }
  }

  loadEmployees(): void {
    this.employeeService.getEmployees().subscribe(employees => {
      this.employees = employees.content;
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const departmentData = this.form.value;
    const operation = this.department 
      ? this.departmentService.updateDepartment(this.department.id, departmentData)
      : this.departmentService.createDepartment(departmentData);

    operation.subscribe({
      next: () => {
        this.activeModal.close('saved');
        this.toastr.success('Department saved', 'Success');
      } ,
      error: (err) => {
        console.error(err)
        this.toastr.error('Failed to save department');
      }
      
    });
  }
}