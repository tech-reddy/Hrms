import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Designation } from '../../../models/employee';
import { DesignationService } from '../../../services/designation/designation.service';
import { DesignationFormComponent } from '../designation-form/designation-form.component';
import { AuthService } from '../../../services/auth.service';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-department-list',
  templateUrl: './designation-list.component.html',
  styleUrls: ['./designation-list.component.css'],
  standalone: false
})
export class DesignationListComponent implements OnInit {
  designations: Designation[] = [];
  isLoading = true;
  userRole: string | null = null;

  constructor(
    private designationService: DesignationService,
    private modalService: NgbModal,
    private authService: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loadDesignations();
    this.authService.getCurrentUser().subscribe(user => {
      this.userRole = user.role; // assume user.role is a string like 'ADMIN', 'MANAGER', or 'EMPLOYEE'
    });
  }

  loadDesignations(): void {
    this.designationService.getDesignations().subscribe(designations => {
      this.designations = designations;
      this.isLoading = false;
    });
  }

  openDesignationForm(designation?: Designation): void {
    const modalRef = this.modalService.open(DesignationFormComponent, {
      size: 'lg',
      centered: true
    });
    modalRef.componentInstance.description = designation;

    modalRef.result.then((result) => {
      if (result === 'saved') this.loadDesignations();
    });
  }

  deleteDesignation(id: number): void {
    if (confirm('Are you sure you want to delete this designation?')) {
      this.designationService.deleteDesignation(id).subscribe({
        next:() => {
        this.loadDesignations();
        this.toastr.success('Designation deleted successfully');
      },
      error: () => {
        console.error('Failed to delete designation');
        this.toastr.error('Failed to delete designation');
      }
    });
    }
  }
}