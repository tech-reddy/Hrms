import { Component } from '@angular/core';
import { PayrollResponse } from '../../../models/employee';
import { PayrollService } from '../../../services/payroll/payroll.service';
import { AuthService } from '../../../services/auth.service';
import { EmployeesService } from '../../../services/employees.service';

@Component({
  selector: 'app-employee-payroll',
  standalone: false,
  templateUrl: './employee-payroll.component.html',
  styleUrl: './employee-payroll.component.scss'
})
export class EmployeePayrollComponent {
  payrolls: PayrollResponse[] = [];
  employeeId: number | null = null;

  constructor(
    private payrollService: PayrollService,
    private employeeService: EmployeesService
  ) {}

  ngOnInit() {
    this.employeeService.getCurrentEmployee().subscribe(employee => {
      this.employeeId = employee.id;
      if(this.employeeId) {
        this.payrollService.getEmployeePayroll(this.employeeId).subscribe(data => {
          this.payrolls = data;
        });
      }
    });
    
  }

  statusBadgeClass(status: string | undefined): string{
    if (!status) {
      return 'bg-secondary'; // fallback color for undefined status
    }
    switch (status.toUpperCase()) {
      case 'PENDING':
        return 'bg-warning';
      case 'APPROVED':
        return 'bg-success';
      case 'REJECTED':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }
}
