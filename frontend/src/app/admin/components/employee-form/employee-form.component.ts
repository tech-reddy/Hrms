import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Department, Designation, Employee } from '../../../models/employee';
import { EmployeesService } from '../../../services/employees.service';
import { DepartmentService } from '../../../services/department/department.service';
import { DesignationService } from '../../../services/designation/designation.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-employee-form',
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.css'],
  standalone: false
})
export class EmployeeFormComponent implements OnInit {
  @Input() employee?: Employee;
  form: FormGroup;
  departments: Department[] = [];
  designations: Designation[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private employeeService: EmployeesService,
    private departmentService: DepartmentService,
    private designationService: DesignationService,
    private toastr: ToastrService
  ) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: [''],
      hireDate: [''],
      salary: [''],
      departmentId: [''],
      designationId: [''],
      username: ['', Validators.required],
      role: ['EMPLOYEE', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadDropdowns();
    if (this.employee) {
      this.form.patchValue({
        ...this.employee,
        departmentId: this.employee.department?.id,
        designationId: this.employee.designation?.id
      });
      this.form.get('user.password')?.clearValidators();
    }
  }

  loadDropdowns(): void {
    this.departmentService.getDepartments().subscribe(departments => {
      this.departments = departments.content;
    });
    
    this.designationService.getDesignations().subscribe(designations => {
      this.designations = designations;
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const employeeData = this.form.value;
    const operation = this.employee 
      ? this.employeeService.updateEmployee(this.employee.id, employeeData)
      : this.employeeService.createEmployee(employeeData);

    operation.subscribe({
      next: () => {
        this.activeModal.close('saved')
        this.toastr.success('Employee saved', 'Success')
      } ,
      error: (err) => {
        this.toastr.error('Failed to save employee')
        console.error(err)
      }
    });
  }
}
