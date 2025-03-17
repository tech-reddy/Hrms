// attendance.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {
  private baseUrl = 'http://localhost:8090/api/attendance';

  constructor(private http: HttpClient, private toastr: ToastrService) { }

  checkIn(employeeId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/check-in?employeeId=${employeeId}`, {});
  }

  checkOut(employeeId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/check-out?employeeId=${employeeId}`, {});
  }

  getTodayAttendance(employeeId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/today/${employeeId}`);
  }

  getAttendanceHistory(employeeId: number, startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/history?employeeId=${employeeId}&startDate=${startDate}&endDate=${endDate}`);
  }

  getAllAttendances(): Observable<any> {
    return this.http.get(this.baseUrl);
  }
}