import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './admin/components/login/login.component';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { DashboardComponent } from './admin/components/dashboard/dashboard.component';
import { EmployeeListComponent } from './admin/components/employee-list/employee-list.component';
import { DepartmentListComponent } from './admin/components/department-list/department-list.component';
import { DesignationListComponent } from './admin/components/designation-list/designation-list.component';
import { ForgetPasswordComponent } from './components/forget-password/forget-password.component';
import { ManagerDashboardComponent } from './manager/components/manager-dashboard/manager-dashboard.component';
import { LeaveRequestsComponent } from './components/leaves/leave-requests/leave-requests.component';
import { ApplyLeaveComponent } from './components/leaves/apply-leave/apply-leave.component';
import { LeaveTypeManagementComponent } from './components/leaves/leave-type-management/leave-type-management.component';
import { ProjectListComponent } from './components/project-task/project-list/project-list.component';
import { ProjectFormComponent } from './components/project-task/project-form/project-form.component';
import { ProjectDetailComponent } from './components/project-task/project-detail/project-detail.component';
import { TaskDetailComponent } from './components/project-task/task-detail/task-detail.component';
import { TaskFormComponent } from './components/project-task/task-form/task-form.component';
import { TaskListComponent } from './components/project-task/task-list/task-list.component';
import { AuthGuard } from './core/guard/auth.guard';
import { AllComplaintsComponent } from './components/complaints/all-complaints/all-complaints.component';
import { CreateComplaintComponent } from './components/complaints/create-complaint/create-complaint.component';
import { MyComplaintsComponent } from './components/complaints/my-complaints/my-complaints.component';
import { EmployeeAttendanceComponent } from './components/attendance/employee-attendance/employee-attendance.component';
import { ManagerAttendanceComponent } from './components/attendance/manager-attendance/manager-attendance.component';
import { PayrollListComponent } from './components/payroll/payroll-list/payroll-list.component';
import { EmployeePayrollComponent } from './components/payroll/employee-payroll/employee-payroll.component';
import { PayrollDetailComponent } from './components/payroll/payroll-detail/payroll-detail.component';
import { PayrollGenerateComponent } from './components/payroll/payroll-generate/payroll-generate.component';
import { EmployeeProjectComponent } from './components/project-task/employee-project/employee-project.component';
import { EmployeeTaskComponent } from './components/project-task/employee-task/employee-task.component';
import { EmployeeDashboardComponent } from './employee/components/employee-dashboard/employee-dashboard.component';

const routes: Routes = [
  {
    path: '', component: LoginComponent
  },
  {
    path: 'login', component: LoginComponent,
    //canActivate: [AuthGuard]
  },
  {
    path: 'reset-password', component: ForgetPasswordComponent
  },
  { path: 'admin/dashboard', component: DashboardComponent },
  {
    path: 'reset-password', component: ForgetPasswordComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'manager/dashboard', component: ManagerDashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'employee-dashboard', component: EmployeeDashboardComponent,
    canActivate: [AuthGuard]
  },

  {
    path: 'employees', component: EmployeeListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'departments', component: DepartmentListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'designations', component: DesignationListComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'admin/leave-management', // ✅ Added Leave Management Route
    component: LeaveTypeManagementComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'admin/leave-types',
    component: LeaveTypeManagementComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'employee/leave',
    component: ApplyLeaveComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'manager/leave-requests',
    component: LeaveRequestsComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'projects',
    component: ProjectListComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'projects/new',
    component: ProjectFormComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'projects/:id',
    component: ProjectDetailComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'tasks',
    component: TaskListComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'tasks/:id',
    component: TaskDetailComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'tasks/new',
    component: TaskFormComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'tasks/:id/edit',
    component: TaskFormComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'complaints',
    component: AllComplaintsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'create-complaint',
    component: CreateComplaintComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'my-complaints',
    component: MyComplaintsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'employee-attendance',
    component: EmployeeAttendanceComponent
  },
  {
    path: 'manager-attendance',
    component: ManagerAttendanceComponent
  },
  {
    path: 'payroll',
    children: [
      { path: '', component: PayrollListComponent, canActivate: [AuthGuard] },
      { path: 'generate', component: PayrollGenerateComponent, canActivate: [AuthGuard] },
      { path: ':id', component: PayrollDetailComponent, canActivate: [AuthGuard] }
    ]
  },
  {
    path: 'my-payroll',
    component: EmployeePayrollComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'employee-projects',
    children: [
      { path: '', component: EmployeeProjectComponent },
      { path: 'employee-projects', component: EmployeeProjectComponent },
      { path: 'employee-tasks', component: EmployeeTaskComponent }
    ],
    canActivate: [AuthGuard]
  },
  
  {
    path: '**', component: PagenotfoundComponent
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
