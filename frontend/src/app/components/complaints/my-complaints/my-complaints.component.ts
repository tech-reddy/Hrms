import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CreateComplaintComponent } from '../create-complaint/create-complaint.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ComplaintResponse, CreateComplaintRequest } from '../../../models/employee';
import { ComplaintService } from '../../../services/complaint/complaint.service';

@Component({
  selector: 'app-my-complaints',
  standalone: false,
  templateUrl: './my-complaints.component.html',
  styleUrl: './my-complaints.component.scss'
})
export class MyComplaintsComponent implements OnInit {
  complaints: ComplaintResponse[] = [];

  constructor(
    private complaintService: ComplaintService,
    private modalService: NgbModal,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.loadComplaints();
  }

  private loadComplaints() {
    this.complaintService.getMyComplaints().subscribe({
      next: (data) => this.complaints = data,
      error: () => this.toastr.error('Failed to load complaints')
    });
  }

    openCreateComplaint(complaint?: CreateComplaintRequest): void {
      const modalRef = this.modalService.open(CreateComplaintComponent, {
        size: 'lg',
        centered: true
      });
      modalRef.componentInstance.complaint = complaint;
  
      modalRef.result.then((result) => {
        if (result === 'saved') this.loadComplaints();
      });
    }
}
