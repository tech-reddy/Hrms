// leave.service.ts
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LeaveBalance, LeaveRequest, LeaveRequestResponse, LeaveType } from '../../models/employee';

@Injectable({
  providedIn: 'root'
})
export class LeaveService {
  private apiUrl = 'http://localhost:8090/api/leaves';

  constructor(private http: HttpClient) { }

  createLeaveType(leaveType: LeaveType): Observable<LeaveType> {
    return this.http.post<LeaveType>(`${this.apiUrl}/types`, leaveType);
  }

  getLeaveTypes(): Observable<LeaveType[]> {
    return this.http.get<LeaveType[]>(`${this.apiUrl}/types`);
  }

  applyLeave(request: LeaveRequest): Observable<LeaveRequest> {
    return this.http.post<LeaveRequest>(`${this.apiUrl}`, request);
  }

  getEmployeeRequests(employeeId: number): Observable<LeaveRequestResponse[]> {
    console.log(employeeId);
    return this.http.get<LeaveRequestResponse[]>(`${this.apiUrl}/pending/${employeeId}`
      //, {params: { status: 'APPROVED' }}
  );
  }

  getPendingRequests(): Observable<LeaveRequestResponse[]> {
    return this.http.get<LeaveRequestResponse[]>(`${this.apiUrl}/pending`);
  }
  getPendingRequestsInDepartment(departmentId: number): Observable<LeaveRequestResponse[]> {
    return this.http.get<LeaveRequestResponse[]>(`${this.apiUrl}/pending/department`, {
      params: { departmentId: departmentId, status: 'PENDING' }
    });
  }

  getLeaveBalance(employeeId: number): Observable<LeaveBalance[]> {
    return this.http.get<LeaveBalance[]>(`${this.apiUrl}/balances/${employeeId}`);
  }

  updateRequestStatus(requestId: number, status: string, approverId: number): Observable<LeaveRequestResponse> {
    const params = new HttpParams()
    .set('approverId', approverId.toString())
    .set('status', status);
    return this.http.put<LeaveRequestResponse>(
      `${this.apiUrl}/${requestId}/approve`,null,
      {params}
      );
  }
}