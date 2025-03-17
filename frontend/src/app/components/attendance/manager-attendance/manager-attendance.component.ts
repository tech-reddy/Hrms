import { Component, OnInit } from '@angular/core';
import { AttendanceService } from '../../../services/attendance/attendance.service';
import { ToastrService } from 'ngx-toastr';
import { fadeInOut } from '../animation';

@Component({
  selector: 'app-manager-attendance',
  standalone: false,
  templateUrl: './manager-attendance.component.html',
  styleUrl: './manager-attendance.component.scss',
  animations: [fadeInOut]
})
export class ManagerAttendanceComponent implements OnInit {
  allAttendances: any[] = [];
  filteredAttendances: any[] = [];
  searchDate!: string;

  constructor(
    private attendanceService: AttendanceService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.loadAllAttendances();
  }

  loadAllAttendances() {
    this.attendanceService.getAllAttendances().subscribe({
      next: (res) => {
        this.allAttendances = res;
        this.filteredAttendances = [...res];
      },
      error: () => this.toastr.error('Failed to load attendance records')
    });
  }

  filterByDate() {
    if (!this.searchDate) {
      this.filteredAttendances = [...this.allAttendances];
      return;
    }
    
    const searchDate = new Date(this.searchDate);
    this.filteredAttendances = this.allAttendances.filter(att => {
      const attDate = new Date(att.date);
      return attDate.toDateString() === searchDate.toDateString();
    });
  }

  getTimeAsDate(timeString: string): Date | null {

    if (!timeString) {
      // Return null or a default date if timeString is undefined
      return null;
    }
    const parts = timeString.split(':');
    if (parts.length < 3) {
      // Handle invalid time string format
      return null;
    }
    // For example, use today's date with the provided time
    const today = new Date();
    const [hours, minutes, secondsWithMs] = timeString.split(':');
    const [seconds, ms] = secondsWithMs.split('.');
    today.setHours(+hours, +minutes, +seconds, +ms);
    return today;
  }
}
