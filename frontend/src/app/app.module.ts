import { DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './core/interceptor/auth.interceptor';
import { NgxEchartsModule } from 'ngx-echarts';
import { ToastrModule } from 'ngx-toastr';
import { ForgetPasswordComponent } from './components/forget-password/forget-password.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './admin/components/login/login.component';
import { HomepageComponent } from './admin/components/homepage/homepage.component';
import { NavbarComponent } from './admin/components/navbar/navbar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { DashboardComponent } from './admin/components/dashboard/dashboard.component';
import { EmployeeListComponent } from './admin/components/employee-list/employee-list.component';
import { EmployeeFormComponent } from './admin/components/employee-form/employee-form.component';
import { DepartmentFormComponent } from './admin/components/department-form/department-form.component';
import { DepartmentListComponent } from './admin/components/department-list/department-list.component';
import { DesignationFormComponent } from './admin/components/designation-form/designation-form.component';
import { DesignationListComponent } from './admin/components/designation-list/designation-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EmployeeDashboardComponent } from './employee/components/employee-dashboard/employee-dashboard.component';
import { ManagerDashboardComponent } from './manager/components/manager-dashboard/manager-dashboard.component';
import { LeaveTypeManagementComponent } from './components/leaves/leave-type-management/leave-type-management.component';
import { ApplyLeaveComponent } from './components/leaves/apply-leave/apply-leave.component';
import { LeaveRequestsComponent } from './components/leaves/leave-requests/leave-requests.component';
import { ProjectListComponent } from './components/project-task/project-list/project-list.component';
import { ProjectDetailComponent } from './components/project-task/project-detail/project-detail.component';
import { TaskListComponent } from './components/project-task/task-list/task-list.component';
import { TaskFormComponent } from './components/project-task/task-form/task-form.component';
import { TaskDetailComponent } from './components/project-task/task-detail/task-detail.component';
import { ProjectFormComponent } from './components/project-task/project-form/project-form.component';
import { AllComplaintsComponent } from './components/complaints/all-complaints/all-complaints.component';
import { CreateComplaintComponent } from './components/complaints/create-complaint/create-complaint.component';
import { MyComplaintsComponent } from './components/complaints/my-complaints/my-complaints.component';
import { EmployeeAttendanceComponent } from './components/attendance/employee-attendance/employee-attendance.component';
import { ManagerAttendanceComponent } from './components/attendance/manager-attendance/manager-attendance.component';
import { PayrollListComponent } from './components/payroll/payroll-list/payroll-list.component';
import { PayrollGenerateComponent } from './components/payroll/payroll-generate/payroll-generate.component';
import { PayrollDetailComponent } from './components/payroll/payroll-detail/payroll-detail.component';
import { EmployeePayrollComponent } from './components/payroll/employee-payroll/employee-payroll.component';
import { FilterPipe } from './components/payroll/filter.pipe';
import { EmployeeProjectComponent } from './components/project-task/employee-project/employee-project.component';
import { EmployeeTaskComponent } from './components/project-task/employee-task/employee-task.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomepageComponent,
    NavbarComponent,
    PagenotfoundComponent,
    DashboardComponent,
    EmployeeListComponent,
    EmployeeFormComponent,
    DepartmentFormComponent,
    DepartmentListComponent,
    DesignationFormComponent,
    DesignationListComponent,
    ForgetPasswordComponent,
    ManagerDashboardComponent,
    EmployeeDashboardComponent,
    LeaveTypeManagementComponent,
    ApplyLeaveComponent,
    LeaveRequestsComponent,
    ProjectListComponent,
    ProjectDetailComponent,
    TaskListComponent,
    TaskFormComponent,
    TaskDetailComponent,
    ProjectFormComponent,
    CreateComplaintComponent,
    MyComplaintsComponent,
    AllComplaintsComponent,
    EmployeeAttendanceComponent,
    ManagerAttendanceComponent,
    PayrollListComponent,
    PayrollGenerateComponent,
    PayrollDetailComponent,
    EmployeePayrollComponent,
    FilterPipe,
    EmployeeProjectComponent,
    EmployeeTaskComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    NgxEchartsModule.forRoot({
        echarts: () => import('echarts')
      }),
    BrowserAnimationsModule,
    ToastrModule.forRoot({ timeOut: 3000, positionClass: 'toast-top-center' })
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: LOCALE_ID, useValue: 'en-IN' },
    { provide: DEFAULT_CURRENCY_CODE, useValue: 'INR' } 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
