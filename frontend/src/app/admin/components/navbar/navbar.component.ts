import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  currentUser: string | null = null;
  userRole: string | null = null;
  navItems: any[] = [];

  adminNav = [
    {
      title: 'Dashboard',
      link: '/admin/dashboard',
      icon: 'fas fa-tachometer-alt',
    },
    { title: 'Employees', link: '/employees', icon: 'fas fa-users' },
    { title: 'Departments', link: '/departments', icon: 'fas fa-building' },
    { title: 'Designations', link: '/designations', icon: 'fas fa-user-tag' },
    {
      title: 'Leave Management',
      link: '/admin/leave-management',
      icon: 'fas fa-calendar-alt',
    },
    {
      title: 'Complaints',
      link: 'complaints',
      icon: 'fas fa-exclamation-triangle',
    }
  ];

  managerNav = [
    {
      title: 'Dashboard',
      link: '/manager/dashboard',
      icon: 'fas fa-tachometer-alt',
    },
    { title: 'Employees', link: '/employees', icon: 'fas fa-users' },
    { title: 'Departments', link: '/departments', icon: 'fas fa-building' },
    { title: 'Designations', link: '/designations', icon: 'fas fa-user-tag' },
    { title: 'Payroll', link: '/payroll', icon: 'fas fa-wallet' },
    {
      title: 'Team Attendance',
      link: '/manager-attendance',
      icon: 'fas fa-calendar-check',
    },
    {
      title: 'Leave Requests',
      link: '/manager/leave-requests',
      icon: 'fas fa-calendar-alt',
    },
    {
      title: 'Projects',
      link: '/projects', icon: 'fas fa-project-diagram'
    },
    {
      title: 'Complaints',
      link: 'complaints',
      icon: 'fas fa-exclamation-triangle',
    }
  ];

  employeeNav = [
    {
      title: 'Dashboard',
      link: '/employee-dashboard',
      icon: 'fas fa-tachometer-alt',
    },
    {
      title: 'Projects',
      link: '/employee-projects',
      icon: 'fas fa-project-diagram',

    },
    {
      title: 'My Attendance',
      link: '/employee-attendance',
      icon: 'fas fa-calendar-check',
    },
    {
      title: 'Apply Leave',
      link: '/employee/leave',
      icon: 'fas fa-calendar-alt',
    },
    {
      title: 'My Payroll',
      link: '/my-payroll',
      icon: 'fas fa-wallet',
    },
    {
      title: 'Complaints',
      link: 'my-complaints',
      icon: 'fas fa-exclamation-triangle',
    },
  ];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.authService.getCurrentUser().subscribe({
      next: (user) => {
        this.userRole = user.role;
        console.log(this.userRole);

        if (this.userRole === 'ADMIN') {
          this.currentUser = 'Admin User';
          this.navItems = this.adminNav;
          console.log(this.currentUser);
        } else if (this.userRole === 'MANAGER') {
          this.currentUser = 'Manager User';
          this.navItems = this.managerNav;
        } else if (this.userRole === 'EMPLOYEE') {
          this.currentUser = 'Employee User';
          this.navItems = this.employeeNav;
        }
      },
      error: (error) => {
        console.error('Error fetching user', error);
      },
    });
  }

  logout(): void {
    sessionStorage.removeItem('access_token');
    this.router.navigate(['/login']);
  }
}
