import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    if (sessionStorage.getItem('access_token')) {
      return true;
    } else {
      // Redirect to login if not authenticated
      this.router.navigate(['/login']);
      return false;
    }
  }
}
