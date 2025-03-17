import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CreateTaskRequest, Task, UpdateTaskRequest } from "../../models/employee";
import { Observable } from "rxjs";

// task.service.ts
@Injectable({ providedIn: 'root' })
export class TaskService {
  private apiUrl = 'http://localhost:8090/api/tasks';

  constructor(private http: HttpClient) {}

  getTask(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.apiUrl}/${id}`);
  }
  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }
  getTasksByEmployee(employeeId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/employee/${employeeId}`);
  }
  updateTask(id: number, update: UpdateTaskRequest): Observable<Task> {
    return this.http.put<Task>(`${this.apiUrl}/${id}`, update);
  }
  updateTaskStatus(id: number, status: string): Observable<Task> {
    return this.http.put<Task>(`${this.apiUrl}/${id}/status`, JSON.stringify(status), {
      headers: { 'Content-Type': 'application/json' }
    });
  }
  
  createTask(task: CreateTaskRequest): Observable<Task> {  
    return this.http.post<Task>(this.apiUrl, task);
  }

  deleteTask(id: number): Observable<void> {
    console.log('deleteTask', id);
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}