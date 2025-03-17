import { Component } from '@angular/core';
import {
  LeaveBalance,
  LeaveRequestResponse,
  LeaveType,
} from '../../../models/employee';
import { LeaveService } from '../../../services/leave/leave.service';
import { EmployeesService } from '../../../services/employees.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-apply-leave',
  standalone: false,
  templateUrl: './apply-leave.component.html',
  styleUrl: './apply-leave.component.scss',
})
export class ApplyLeaveComponent {
  leaveRequest = {
    employeeId: null,
    leaveTypeId: null,
    startDate: '',
    endDate: '',
    reason: '',
  };
  leaveTypes: LeaveType[] = [];
  requests: LeaveRequestResponse[] = [];
  currentEmployeeId: number | null = null;
  leaveBalances: LeaveBalance[] = [];
  showBalanceTable: boolean = false; // ✅ Store employee ID

  constructor(
    private leaveService: LeaveService,
    private employeeService: EmployeesService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.getCurrentEmployee(); // ✅ Fetch and store employee ID
    this.loadLeaveTypes();
    this.loadRequests();
  }

  getCurrentEmployee() {
    this.employeeService.getCurrentEmployee().subscribe(
      (employee) => {
        this.currentEmployeeId = employee.id; // ✅ Store employee ID
        this.loadRequests(); // ✅ Load requests after employee ID is set
      },
      (error) => {
        console.error('Error fetching employee:', error);
      }
    );
  }

  loadLeaveTypes() {
    this.leaveService.getLeaveTypes().subscribe((types) => {
      this.leaveTypes = types;
    });
  }

  loadRequests() {
    if (this.currentEmployeeId) {
      // ✅ Wait for employee ID to be set
      this.leaveService
        .getEmployeeRequests(this.currentEmployeeId)
        .subscribe((requests) => {
          this.requests = requests;
          console.log(requests);
        });
    }
  }

  onSubmit() {
    if (!this.currentEmployeeId) {
      console.error('Error: Employee ID is null');
      return;
    }

    const request = {
      ...this.leaveRequest,
      employeeId: this.currentEmployeeId, // ✅ Attach the correct employee ID
      startDate: new Date(this.leaveRequest.startDate),
      endDate: new Date(this.leaveRequest.endDate),
    };

    this.leaveService.applyLeave(request).subscribe({
      next: () => {
        this.toastr.success('Leave request submitted');
        this.loadRequests();
        this.leaveRequest = {
          employeeId: null,
          leaveTypeId: null,
          startDate: '',
          endDate: '',
          reason: '',
        };
      },
      error: (err) => {
        console.error(err);
        this.toastr.error('Failed to submit leave request');
      },
    });
  }

  loadLeaveBalance(): void {
    this.leaveService.getLeaveBalance(this.currentEmployeeId!).subscribe({
      next: (data) => {
        this.leaveBalances = data;
        this.showBalanceTable = true; // Show table after loading data
      },
      error: (err) => console.error('Error fetching leave balance', err),
    });
  }

  toggleLeaveBalance(): void {
    this.showBalanceTable = !this.showBalanceTable;
    if (this.showBalanceTable) {
      this.loadLeaveBalance();
    }
  }
}
