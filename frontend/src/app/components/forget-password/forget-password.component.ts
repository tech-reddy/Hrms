import { Component } from '@angular/core';
import { ForgotPasswordService } from '../../services/forget-password.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forget-password',
  standalone: false,
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.scss'
})
export class ForgetPasswordComponent {
  currentStep: number = 1;
  identifier: string = '';
  email: string = '';
  otp: string = '';
  newPassword: string = '';
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private forgotPasswordService: ForgotPasswordService, private toastr: ToastrService, private router:Router) { }

  sendOtp() {
    this.errorMessage = '';
    this.forgotPasswordService.sendOtp(this.identifier).subscribe({
      next: (res) => {
        console.log('OTP sent successfully');
        this.toastr.success(res.message, 'Success');
        //this.successMessage = 'OTP has been sent to your registered email';
        this.currentStep = 2;
      },
      error: (err) => {
        this.toastr.error('Failed to send OTP', 'Error');
        this.errorMessage = err.error || 'Failed to send OTP';
      }
    });
  }

  verifyOtp() {
    this.errorMessage = '';
    this.forgotPasswordService.verifyOtp(this.email, this.otp).subscribe({
      next: () => {
        this.successMessage = 'OTP verified successfully';
        this.currentStep = 3;
      },
      error: (err) => {
        this.errorMessage = err.error || 'Invalid OTP';
      }
    });
  }

  resetPassword() {
    this.errorMessage = '';
    this.forgotPasswordService.resetPassword(this.email, this.newPassword).subscribe({
      next: () => {
        this.toastr.success('Password reset successful', 'Success');
        this.successMessage = 'Password reset successful! You can now login with your new password';
        this.router.navigate(['/login']);
        this.resetForm();
      },
      error: (err) => {
        this.errorMessage = err.error || 'Password reset failed';
      }
    });
  }

  private resetForm() {
    this.currentStep = 1;
    this.identifier = '';
    this.email = '';
    this.otp = '';
    this.newPassword = '';
  }
}