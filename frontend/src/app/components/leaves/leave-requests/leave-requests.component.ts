import { Component } from '@angular/core';
import { LeaveRequestResponse } from '../../../models/employee';
import { LeaveService } from '../../../services/leave/leave.service';
import { EmployeesService } from '../../../services/employees.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-leave-requests',
  standalone: false,
  templateUrl: './leave-requests.component.html',
  styleUrl: './leave-requests.component.scss'
})
export class LeaveRequestsComponent {
  requests: LeaveRequestResponse[] = [];
  currentEmployeeId?: number;

  constructor(private leaveService: LeaveService, private employeeService: EmployeesService, private toastr: ToastrService) {}

  ngOnInit() {
    this.loadPendingRequests();
  }

  loadPendingRequests() {
    this.employeeService.getCurrentEmployee().subscribe(employee => {
      this.currentEmployeeId = employee.id;
      console.log(employee.department?.id);
      this.leaveService.getPendingRequestsInDepartment(employee.department?.id!).subscribe(requests => {
        this.requests = requests;
        console.log(requests);
    });
    });
  }

  updateStatus(requestId: number, status: string) {
    console.log(this.currentEmployeeId!)
    console.log(requestId)
    this.leaveService.updateRequestStatus(requestId, status, this.currentEmployeeId!).subscribe(() => {
      if(status === 'APPROVED') this.toastr.success('Leave request approved');
      else this.toastr.success('Leave request rejected');
      this.loadPendingRequests();
    });
  }

}
