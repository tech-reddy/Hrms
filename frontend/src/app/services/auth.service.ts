import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Route, Router } from '@angular/router';
import { User } from '../models/employee';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API_URL = 'http://localhost:8090/api/auth';
  isAuthenticated = new BehaviorSubject<boolean>(false);
  private currentUserSubject = new BehaviorSubject<User | null>(null); // Adjust URL as needed

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string): Observable<any> {
    console.log(username, password);
    sessionStorage.removeItem('access_token');
    return this.http.post<any>(`${this.API_URL}/login`, { username, password })
      .pipe(
        tap(response => {
          // Save JWT token from the backend response
          sessionStorage.setItem('access_token', response.token);
          this.getCurrentUser().subscribe(user => {
            this.currentUserSubject.next(user);
          });
        })
      );
  }

  logout() {
    sessionStorage.removeItem('access_token');
    this.isAuthenticated.next(false);
    this.router.navigate(['/login']);
  }

  get isLoggedIn(): boolean {
    return sessionStorage.getItem('access_token') !== null;
  }

  getCurrentUser(): Observable<any> {
    return this.http.get<any>(`${this.API_URL}/me`);
  }

  changePassword(credentials: {oldPassword: string, newPassword: string}): Observable<any> {
    return this.http.post<any>(`${this.API_URL}/change-password`, credentials);
  }
}
