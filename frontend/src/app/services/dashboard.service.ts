// dashboard.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { forkJoin, Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { EmployeesService } from './employees.service';

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private apiUrl = 'http://localhost:8090/api';
  constructor(private http: HttpClient, private employeeService: EmployeesService) {}



  getDashboardMetrics1(): Observable<any> {
    // Simulated API response

    return of({
      totalEmployees: 142,//this.http.get<number>(`${this.apiUrl}/count`),
      presentEmployees: 118,
      pendingLeaves: 9,
      activeComplaints: 3
    }).pipe(delay(500));
  }

  getDashboardMetrics(): Observable<any> {
    return forkJoin({
      totalEmployees: this.http.get<number>(`${this.apiUrl}/employees/count`),
      presentEmployees: this.http.get<number>(`${this.apiUrl}/attendance/present`),
      pendingLeaves: this.http.get<number>(`${this.apiUrl}/leaves/count/pending`),
      activeComplaints: this.http.get<number>(`${this.apiUrl}/complaints/count`)
    });
  }
}