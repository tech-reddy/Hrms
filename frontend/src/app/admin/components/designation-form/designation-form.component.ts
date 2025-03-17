import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Designation } from '../../../models/employee';
import { DesignationService } from '../../../services/designation/designation.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-designation-form',
  templateUrl: './designation-form.component.html',
  styleUrls: ['./designation-form.component.css'],
  standalone: false
})
export class DesignationFormComponent implements OnInit {
  @Input() designation?: Designation;
  form: FormGroup;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private designationService: DesignationService,
    private toastr: ToastrService
  ) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      grade: ['', [Validators.required, Validators.pattern(/^L[1-9]$/)]]
    });
  }

  ngOnInit(): void {
    if (this.designation) {
      this.form.patchValue(this.designation);
    }
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const designationData = this.form.value;
    const operation = this.designation 
      ? this.designationService.updateDesignation(this.designation.id, designationData)
      : this.designationService.createDesignation(designationData);

    operation.subscribe({
      next: () => {this.activeModal.close('saved');
      this.toastr.success('Designation saved', 'Success')
      },
      error: (err) => {
        this.toastr.error('Failed to save designation')
        console.error(err)
      }
    });
  }
}