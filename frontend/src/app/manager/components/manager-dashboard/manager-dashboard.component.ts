import { Component, OnInit } from '@angular/core';
import { EChartsOption } from 'echarts';
import { DashboardService } from '../../../services/dashboard.service';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { animate, query, stagger, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-manager-dashboard',
  standalone: false,
  templateUrl: './manager-dashboard.component.html',
  styleUrl: './manager-dashboard.component.scss',
    animations: [
      trigger('cardAnimation', [
        transition('* => *', [
          query(':enter', [
            style({ opacity: 0, transform: 'translateY(-20px)' }),
            stagger(100, [
              animate('300ms ease-out', 
                style({ opacity: 1, transform: 'translateY(0)' }))
            ])
          ], { optional: true })
        ])
      ]),
      trigger('fadeIn', [
        transition(':enter', [
          style({ opacity: 0 }),
          animate('500ms 300ms ease-in', 
            style({ opacity: 1 }))
        ])
      ])
    ]
  
})
export class ManagerDashboardComponent implements OnInit {
  metrics: any[] = [];
  departmentChart: EChartsOption = {};
  attendanceChart: EChartsOption = {};
  currentUser = 'Manager User';
  
  // navItems = [
  //   { title: 'Employees', link: '/employees', icon: 'fas fa-users' },
  //   { title: 'Departments', link: '/departments', icon: 'fas fa-building' },
  //   { title: 'Desgignation', link: '/designations', icon: 'fas fa-users' },
  //   { title: 'Payroll', link: '/payroll', icon: 'fas fa-wallet' },
  //   { title: 'Attendance', link: '/attendance', icon: 'fas fa-calendar-check' }
  // ];

  constructor(private dashboardService: DashboardService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.loadMetrics();
    this.initCharts();
  }

  private loadMetrics(): void {
    this.dashboardService.getDashboardMetrics().subscribe({
      next: (data) => {
        this.metrics = [
          { 
            title: 'Total Employees', 
            value: data.totalEmployees,
            type: 'primary',
            icon: 'fas fa-users',
            progress: 100,
            description: 'All registered employees'
          },
          { 
            title: 'Present Today', 
            value: data.presentEmployees,
            type: 'success',
            icon: 'fas fa-user-check',
            progress: (data.presentEmployees / data.totalEmployees) * 100,
            description: 'Currently present'
          },
          { 
            title: 'Leave Requests', 
            value: data.pendingLeaves,
            type: 'warning',
            icon: 'fas fa-calendar-times',
            progress: (data.pendingLeaves / data.totalEmployees) * 100,
            description: 'Pending approval'
          },
          { 
            title: 'Active Complaints', 
            value: data.activeComplaints,
            type: 'danger',
            icon: 'fas fa-exclamation-triangle',
            progress: (data.activeComplaints / data.totalEmployees) * 100,
            description: 'Needing attention'
          }
        ];
      },
      error: (err) => console.error('Failed to load metrics:', err)
    });
  }

  private initCharts(): void {
    // Department Distribution Chart
    this.departmentChart = {
      tooltip: { trigger: 'item' },
      legend: { top: 'bottom' },
      series: [{
        name: 'Department Distribution',
        type: 'pie',
        radius: ['40%', '70%'],
        data: [
          { value: 35, name: 'Engineering' },
          { value: 25, name: 'Marketing' },
          { value: 20, name: 'Sales' },
          { value: 20, name: 'HR' }
        ],
        emphasis: { itemStyle: { shadowBlur: 10 } }
      }]
    };

    // Attendance Trend Chart
    this.attendanceChart = {
      xAxis: { type: 'category', data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'] },
      yAxis: { type: 'value' },
      series: [{
        data: [82, 93, 89, 97, 85],
        type: 'line',
        smooth: true,
        areaStyle: {}
      }],
      tooltip: { trigger: 'axis' }
    };
  }

  logout(): void {
    // Implement logout logic
    this.authService.logout();
    this.router.navigate(['/manager/login']);
    
  }

}
