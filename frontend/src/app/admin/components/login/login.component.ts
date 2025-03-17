import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { UserResponse } from '../../../models/employee';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginForm: FormGroup;
  showPassword: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  // admin-login.component.ts (excerpt)
  onSubmit(): void {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username, password).subscribe({
        next: (response) => {
          if (response.requiresPasswordReset) {
            this.router.navigate(['/reset-password']);
          }
          this.authService.getCurrentUser().subscribe({
            next: (user) => {
              if (user.role === 'ADMIN') {
                this.router.navigate(['/admin/dashboard']);
              } else if (user.role === 'MANAGER') {
                this.router.navigate(['/manager/dashboard']);
              } else this.router.navigate(['/employee-dashboard']);
            },
          });
          this.toastr.success('Login successful', 'Success');
        },
        error: (err: HttpErrorResponse) => {
          this.toastr.error('Invalid username or password', 'Login failed');
          console.error('Login error', err);
        },
      });
    }
  }
}
