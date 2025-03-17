// complaint.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ComplaintResponse, CreateComplaintRequest, UpdateComplaintRequest } from '../../models/employee';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  private baseUrl = 'http://localhost:8090/api/complaints';

  constructor(private http: HttpClient) { }

  createComplaint(request: CreateComplaintRequest): Observable<ComplaintResponse> {
    return this.http.post<ComplaintResponse>(this.baseUrl, request);
  }

  getMyComplaints(): Observable<ComplaintResponse[]> {
    return this.http.get<ComplaintResponse[]>(`${this.baseUrl}/my-complaints`);
  }

  getAllComplaints(): Observable<ComplaintResponse[]> {
    return this.http.get<ComplaintResponse[]>(this.baseUrl);
  }

  updateComplaint(id: number, request: UpdateComplaintRequest): Observable<ComplaintResponse> {
    return this.http.put<ComplaintResponse>(`${this.baseUrl}/${id}`, request);
  }
}