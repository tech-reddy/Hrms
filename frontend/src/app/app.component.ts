import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Role } from './models/employee';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'front-end-2';
  role?: Role;
  constructor(private authService: AuthService) {}
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe({
      next: (user) => {
        this.role = user.role;
      },
      error: (error) => {
        console.error('Error fetching user', error);
      }
    })
  }
}
