import { HttpClient } from "@angular/common/http";
import { CreateProjectRequest, Project, UpdateProjectRequest } from "../../models/employee";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

// project.service.ts
@Injectable({ providedIn: 'root' })
export class ProjectService {
  private apiUrl = 'http://localhost:8090/api/projects';

  constructor(private http: HttpClient) {}

  getAllProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.apiUrl);
  }

  getProject(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.apiUrl}/${id}`);
  }
  getProjectsByEmployee(employeeId: number): Observable<Project[]> {
    return this.http.get<Project[]>(`${this.apiUrl}/employee/${employeeId}`);
  }

  createProject(project: CreateProjectRequest): Observable<Project> {
    return this.http.post<Project>(this.apiUrl, project);
  }

  updateProject(id: number, update: UpdateProjectRequest): Observable<Project> {
    return this.http.put<Project>(`${this.apiUrl}/${id}`, update);
  }

  deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}