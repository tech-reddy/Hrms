import { Component } from '@angular/core';
import { LeaveType } from '../../../models/employee';
import { LeaveService } from '../../../services/leave/leave.service';

@Component({
  selector: 'app-leave-type-management',
  standalone: false,
  templateUrl: './leave-type-management.component.html',
  styleUrl: './leave-type-management.component.scss'
})
export class LeaveTypeManagementComponent {
  newLeaveType: LeaveType = {
    name: '',
    description: '',
    maxDays: 1,
    carryForwardAllowed: false
  };
  leaveTypes: LeaveType[] = [];

  constructor(private leaveService: LeaveService) {}

  ngOnInit() {
    this.loadLeaveTypes();
  }

  loadLeaveTypes() {
    this.leaveService.getLeaveTypes().subscribe(types => {
      this.leaveTypes = types;
    });
  }

  createLeaveType() {
    this.leaveService.createLeaveType(this.newLeaveType).subscribe(() => {
      this.loadLeaveTypes();
      this.newLeaveType = { name: '',description:'', maxDays: 1, carryForwardAllowed: false };
    });
  }

}
