import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DepartmentFormComponent } from '../department-form/department-form.component';
import { Department } from '../../../models/employee';
import { DepartmentService } from '../../../services/department/department.service';
import { AuthService } from '../../../services/auth.service';
@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css'],
  standalone: false
})
export class DepartmentListComponent implements OnInit {
  departments: Department[] = [];
  isLoading = true;
  userRole: string | null = null;

  constructor(
    private departmentService: DepartmentService,
    private modalService: NgbModal,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadDepartments();
    this.authService.getCurrentUser().subscribe(user => {
      this.userRole = user.role; // assume user.role is a string like 'ADMIN', 'MANAGER', or 'EMPLOYEE'
    });
  }

  loadDepartments(): void {
    this.departmentService.getDepartments().subscribe(departments => {
      this.departments = departments.content;
      this.isLoading = false;
    });
  }

  openDepartmentForm(department?: Department): void {
    const modalRef = this.modalService.open(DepartmentFormComponent, {
      size: 'lg',
      centered: true
    });
    modalRef.componentInstance.department = department;

    modalRef.result.then((result) => {
      if (result === 'saved') this.loadDepartments();
    });
  }

  deleteDepartment(id: number): void {
    if (confirm('Are you sure you want to delete this department?')) {
      this.departmentService.deleteDepartment(id).subscribe(() => {
        this.loadDepartments();
      });
    }
  }
}