import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../../../services/attendance/attendance.service';
import { ToastrService } from 'ngx-toastr';
import { fadeInOut } from '../animation';
import { EmployeesService } from '../../../services/employees.service';

@Component({
  selector: 'app-employee-attendance',
  standalone: false,
  templateUrl: './employee-attendance.component.html',
  styleUrl: './employee-attendance.component.scss',
  animations: [fadeInOut]
})
export class EmployeeAttendanceComponent implements OnInit {
  currentAttendance: any;
  history: any[] = [];
  startDate!: string;
  endDate!: string;
  isLoading = false;
  currentEmployeeId?: number;

  constructor(
    public attendanceService: AttendanceService,
    private toastr: ToastrService,
    private employeeService: EmployeesService
  ) { }

  ngOnInit() {
    this.getCurrentEmployee();

  }
  getCurrentEmployee() {
    this.employeeService.getCurrentEmployee().subscribe(
      (employee) => {
        this.currentEmployeeId = employee.id;
        this.loadTodayAttendance(); // ✅ Store employee ID
      },
      (error) => {
        console.error('Error fetching employee:', error);
      }
    );
  }
  loadTodayAttendance() {
    this.attendanceService.getTodayAttendance(this.currentEmployeeId!).subscribe({
      next: (res) => this.currentAttendance = res,
      error: () => this.toastr.error('Failed to load attendance data')
    });
  }

  onCheckIn() {
    this.attendanceService.checkIn(this.currentEmployeeId!).subscribe({
      next: (res) => {
        this.currentAttendance = res;
        this.toastr.success('Checked in successfully!');
      },
      error: (err) => this.toastr.error(err.error || 'Check-in failed')
    });
  }

  onCheckOut() {
    this.attendanceService.checkOut(this.currentEmployeeId!).subscribe({
      next: (res) => {
        this.currentAttendance = res;
        this.toastr.success('Checked out successfully!');
      },
      error: (err) => this.toastr.error(err.error || 'Check-out failed')
    });
  }

  loadHistory() {
    if (!this.startDate || !this.endDate) return;
    
    this.isLoading = true;
    this.attendanceService.getAttendanceHistory(this.currentEmployeeId!, this.startDate, this.endDate)
      .subscribe({
        next: (res) => {
          this.history = res;
          this.isLoading = false;
        },
        error: () => {
          this.toastr.error('Failed to load history');
          this.isLoading = false;
        }
      });
  }
}