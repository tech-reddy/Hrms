import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateComplaintRequest } from '../../../models/employee';
import { ComplaintService } from '../../../services/complaint/complaint.service';


@Component({
  selector: 'app-create-complaint',
  standalone: false,
  templateUrl: './create-complaint.component.html',
  styleUrl: './create-complaint.component.scss'
})
export class CreateComplaintComponent {
  complaintRequest: CreateComplaintRequest = { description: '' };

  constructor(
    private complaintService: ComplaintService,
    private toastr: ToastrService,
    private activeModal: NgbActiveModal
  ) { }

  onSubmit() {
    this.complaintService.createComplaint(this.complaintRequest).subscribe({
      next: () => {
        this.toastr.success('Complaint submitted successfully');
        this.complaintRequest.description = '';
        this.activeModal.close('saved');
      },
      error: () => this.toastr.error('Failed to submit complaint')
    });
  }
}
