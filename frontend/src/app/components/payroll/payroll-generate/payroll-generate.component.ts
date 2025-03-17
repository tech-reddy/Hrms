import { Component } from '@angular/core';
import { PayrollService } from '../../../services/payroll/payroll.service';

@Component({
  selector: 'app-payroll-generate',
  standalone: false,
  templateUrl: './payroll-generate.component.html',
  styleUrl: './payroll-generate.component.scss'
})
export class PayrollGenerateComponent {
  payrollRequest = {
    payPeriodStart: '',
    payPeriodEnd: ''
  };

  constructor(private payrollService: PayrollService) {}

  generatePayroll() {
    const request = {
      payPeriodStart: new Date(this.payrollRequest.payPeriodStart),
      payPeriodEnd: new Date(this.payrollRequest.payPeriodEnd)
    };
    
    this.payrollService.generatePayroll(request).subscribe(() => {
      this.payrollRequest = { payPeriodStart: '', payPeriodEnd: '' };
    });
  }
}
