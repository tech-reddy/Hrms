// payroll.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentStatus, PayrollRequest, PayrollResponse } from '../../models/employee';

@Injectable({ providedIn: 'root' })
export class PayrollService {
  private apiUrl = 'http://localhost:8090/api/payroll';

  constructor(private http: HttpClient) { }

  generatePayroll(request: PayrollRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}`, request);
  }

  getAllPayrolls(): Observable<PayrollResponse[]> {
    return this.http.get<PayrollResponse[]>(this.apiUrl);
  }

  getEmployeePayroll(employeeId: number): Observable<PayrollResponse[]> {
    return this.http.get<PayrollResponse[]>(`${this.apiUrl}/employee/${employeeId}`);
  }

  updatePaymentStatus(payrollId: number, status: PaymentStatus): Observable<PayrollResponse> {
    return this.http.put<PayrollResponse>(
      `${this.apiUrl}/${payrollId}/status`,
      {},
      { params: { status } }
    );
  }

  getPayrollById(id: number): Observable<PayrollResponse> {
    return this.http.get<PayrollResponse>(`${this.apiUrl}/${id}`);
  }
}
