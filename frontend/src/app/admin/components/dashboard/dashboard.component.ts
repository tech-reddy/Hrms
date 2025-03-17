// dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { trigger, transition, style, animate, query, stagger } from '@angular/animations';
import { EChartsOption } from 'echarts';
import { DashboardService } from '../../../services/dashboard.service';
import { PayrollService } from '../../../services/payroll/payroll.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  standalone: false,
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
export class DashboardComponent implements OnInit {
  metrics: any[] = [];
  departmentChart: EChartsOption = {};
  attendanceChart: EChartsOption = {};

  constructor(private dashboardService: DashboardService, private payrollService: PayrollService) { }

  ngOnInit(): void {
    this.loadMetrics();
    this.initCharts();
  }

  // selectedPeriod = { start: '', end: '' };
  // allPayrolls: any[] = [];

  // generatePayroll() {
  //   this.payrollService.generatePayroll(this.selectedPeriod)
  //     .subscribe(() => this.loadPayrolls());
  // }

  // loadPayrolls() {
  //   this.payrollService.getAllPayrolls().subscribe((data: any) => {
  //     this.allPayrolls = data;
  //   });
  // }

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
  }
}