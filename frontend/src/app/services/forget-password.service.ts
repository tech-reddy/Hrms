// forgot-password.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {
  private baseUrl = 'http://localhost:8090/api/forget-password'; // Update with your backend URL

  constructor(private http: HttpClient) { }

  sendOtp(identifier: string): Observable<any> {
    console.log(identifier);
    return this.http.post(`${this.baseUrl}/send-otp`, null, {
      params: { identifier }
    });
  }

  verifyOtp(email: string, otp: string): Observable<any> {
    console.log(email, otp);
    return this.http.post(`${this.baseUrl}/verify-otp`, null, {
      params: { email, otp }
    });
  }

  resetPassword(email: string, newPassword: string): Observable<any> {
    console.log(email, newPassword);
    return this.http.post(`${this.baseUrl}/reset-password`, null, {
      params: { email, newPassword }
    });
  }
}