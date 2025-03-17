import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeFormComponent } from '../employee-form/employee-form.component';
import { EmployeesService } from '../../../services/employees.service';
import { animate, style, transition, trigger } from '@angular/animations';
import { Employee } from '../../../models/employee';
import { AuthService } from '../../../services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css'],
  standalone: false,
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms', style({ opacity: 1 })),
      ]),
    ]),
  ],
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  isLoading = true;
  userRole: string | null = null;

  constructor(
    private employeeService: EmployeesService,
    private modalService: NgbModal,
    private authService: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
    this.authService.getCurrentUser().subscribe((user) => {
      this.userRole = user.role; // assume user.role is a string like 'ADMIN', 'MANAGER', or 'EMPLOYEE'
    });
  }

  loadEmployees(): void {
    this.employeeService.getEmployees().subscribe((employees) => {
      this.employees = employees.content;
      this.isLoading = false;
    });
  }

  openEmployeeForm(employee?: Employee): void {
    const modalRef = this.modalService.open(EmployeeFormComponent, {
      size: 'lg',
      centered: true,
      animation: true,
    });
    modalRef.componentInstance.employee = employee;

    modalRef.result.then((result) => {
      if (result === 'saved') this.loadEmployees();
    });
  }

  deleteEmployee(id: number): void {
    if (confirm('Are you sure you want to delete this employee?')) {
      this.employeeService.deleteEmployee(id).subscribe({
        next: () => {
          this.loadEmployees();
          this.toastr.success('Employee deleted successfully');

        },
        error: (err) => {
          this.toastr.error('Failed to delete employee');
          console.error('Error deleting employee:', err);
        },
      });
    }
  }
}
