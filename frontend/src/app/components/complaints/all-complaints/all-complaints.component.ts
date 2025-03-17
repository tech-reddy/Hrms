import { Component, OnInit } from '@angular/core';
import { ComplaintResponse, ComplaintStatus, UpdateComplaintRequest } from '../../../models/employee';
import { ComplaintService } from '../../../services/complaint/complaint.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-all-complaints',
  standalone: false,
  templateUrl: './all-complaints.component.html',
  styleUrl: './all-complaints.component.scss'
})
export class AllComplaintsComponent implements OnInit {
  complaints: ComplaintResponse[] = [];
  selectedComplaint?: ComplaintResponse;
  updateRequest: UpdateComplaintRequest = {};
  complaintStatuses: string[] = Object.values(ComplaintStatus);

  constructor(
    private complaintService: ComplaintService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.loadComplaints();
  }

  private loadComplaints() {
    this.complaintService.getAllComplaints().subscribe({
      next: (data) => this.complaints = data,
      error: () => this.toastr.error('Failed to load complaints')
    });
  }

  selectComplaint(complaint: ComplaintResponse) {
    this.selectedComplaint = { ...complaint };
    this.updateRequest = {
      status: complaint.status,
      response: complaint.response
    };
  }

  updateComplaint() {
    if (!this.selectedComplaint) return;

    this.complaintService.updateComplaint(this.selectedComplaint.id, this.updateRequest)
      .subscribe({
        next: () => {
          this.toastr.success('Complaint updated successfully');
          this.loadComplaints();
          this.selectedComplaint = undefined;
        },
        error: () => this.toastr.error('Failed to update complaint')
      });
  }
}