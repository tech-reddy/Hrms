import { Component, OnInit } from '@angular/core';
import { trigger, transition, style, animate, state } from '@angular/animations';

interface Announcement {
  title: string;
  date: Date;
  content: string;
}

interface Holiday {
  name: string;
  date: Date;
}

@Component({
  selector: 'app-employee-dashboard',
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css'],
  standalone: false,
  animations: [
    trigger('cardAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(20px)' }),
        animate('0.5s ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ]),
    trigger('slideInLeft', [
      transition(':enter', [
        style({ transform: 'translateX(-100%)', opacity: 0 }),
        animate('0.5s ease-out', style({ transform: 'translateX(0)', opacity: 1 }))
      ])
    ]),
    trigger('slideInRight', [
      transition(':enter', [
        style({ transform: 'translateX(100%)', opacity: 0 }),
        animate('0.5s ease-out', style({ transform: 'translateX(0)', opacity: 1 }))
      ])
    ])
  ]
})
export class EmployeeDashboardComponent implements OnInit {
  quickStats: any[] = [];
  announcements: Announcement[] = [];
  upcomingHolidays: Holiday[] = [];

  ngOnInit() {
    this.initQuickStats();
    this.initAnnouncements();
    this.initHolidays();
  }

  private initQuickStats() {
    this.quickStats = [
      {
        title: 'Attendance',
        value: '95%',
        subtitle: 'Current Month',
        icon: 'fas fa-calendar-check fa-2x'
      },
      {
        title: 'Leave Balance',
        value: '12 Days',
        subtitle: 'Remaining',
        icon: 'fas fa-umbrella-beach fa-2x'
      },
      {
        title: 'Recent Payroll',
        value: '₹4,500',
        subtitle: 'Last Month',
        icon: 'fas fa-wallet fa-2x'
      }
    ];
  }

  private initAnnouncements() {
    this.announcements = [
      {
        title: 'Office Reopening Update',
        date: new Date(),
        content: 'Office will reopen from 1st November with full capacity'
      },
      {
        title: 'New HR Policy',
        date: new Date(),
        content: 'New work from home policy implemented starting next week'
      }
    ];
  }

  private initHolidays() {
    this.upcomingHolidays = [
      {
        name: 'Thanksgiving',
        date: new Date('2023-11-23')
      },
      {
        name: 'Christmas',
        date: new Date('2023-12-25')
      },
      {
        name: 'New Year',
        date: new Date('2024-01-01')
      }
    ];
  }
}