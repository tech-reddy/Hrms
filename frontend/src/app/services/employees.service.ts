import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../models/employee';
import { PaginatedResponse } from '../models/paginated.response';

@Injectable({
  providedIn: 'root'
})
export class EmployeesService {
  private apiUrl = 'http://localhost:8090/api/employees';

  constructor(private http: HttpClient) { }

  getEmployees(): Observable<PaginatedResponse<Employee>> {
    return this.http.get<PaginatedResponse<Employee>>(this.apiUrl);
  }
  getCurrentEmployee(): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/current`);
  }

  getEmployee(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.apiUrl}/${id}`);
  }

  createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.apiUrl, employee);
  }

  updateEmployee(id: number, employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.apiUrl}/${id}`, employee);
  }

  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  getEmployeeCount(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count`);
  }
}
