import { Component, OnInit } from '@angular/core';
import { Project, ProjectStatus } from '../../../models/employee';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from '../../../services/project-tasks/project.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-project-detail',
  standalone: false,
  templateUrl: './project-detail.component.html',
  styleUrl: './project-detail.component.scss'
})
export class ProjectDetailComponent implements OnInit {
  project!: Project;
  isEditing = false;
  editForm!: FormGroup;

  projectStatuses: string[] = Object.values(ProjectStatus);

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.route.params.pipe(
      switchMap(params => this.projectService.getProject(+params['id']))
    ).subscribe(project => {
      this.project = project;
      this.initForm();
    });
  }

  private initForm() {
    this.editForm = this.fb.group({
      name: [this.project.name, Validators.required],
      description: [this.project.description],
      endDate: [this.project.endDate, Validators.required],
      status: [this.project.status, Validators.required]
    });
  }

  saveChanges() {
    if (this.editForm.valid) {
      const update = this.editForm.value;
      this.projectService.updateProject(this.project.id, update).subscribe(updated => {
        this.project = updated;
        this.isEditing = false;
      });
    }
  }

  statusBadgeClass(status: string): string {
    switch (status) {
      case 'PLANNED':
        return 'bg-secondary';
      case 'IN_PROGRESS':
        return 'bg-info';
      case 'COMPLETED':
        return 'bg-success';
      case 'ON_HOLD':
        return 'bg-warning';
      case 'CANCELLED':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }
}
