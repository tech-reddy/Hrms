import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Designation } from '../../models/employee';
import { PaginatedResponse } from '../../models/paginated.response';

@Injectable({
  providedIn: 'root'
})
export class DesignationService {
  private apiUrl = 'http://localhost:8090/api/designations';

  constructor(private http: HttpClient) { }

  getDesignations(): Observable<Designation[]> {
    return this.http.get<Designation[]>(this.apiUrl);
  }

  getDesignation(id: number): Observable<Designation> {
    return this.http.get<Designation>(`${this.apiUrl}/${id}`);
  }

  createDesignation(designation: Designation): Observable<Designation> {
    return this.http.post<Designation>(this.apiUrl, designation);
  }

  updateDesignation(id: number, designation: Designation): Observable<Designation> {
    return this.http.put<Designation>(`${this.apiUrl}/${id}`, designation);
  }

  deleteDesignation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}