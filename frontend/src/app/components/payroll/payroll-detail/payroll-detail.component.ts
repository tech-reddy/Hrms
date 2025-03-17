import { Component } from '@angular/core';
import { PayrollResponse } from '../../../models/employee';
import { ActivatedRoute } from '@angular/router';
import { PayrollService } from '../../../services/payroll/payroll.service';
import { FilterPipe } from '../filter.pipe';

@Component({
  selector: 'app-payroll-detail',
  standalone: false,
  templateUrl: './payroll-detail.component.html',
  styleUrl: './payroll-detail.component.scss'
})
export class PayrollDetailComponent {
  payroll!: PayrollResponse;
  earningsTotal = 0;
  deductionsTotal = 0;

  constructor(
    private route: ActivatedRoute,
    private payrollService: PayrollService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.payrollService.getPayrollById(id).subscribe(payroll => {
      this.payroll = payroll;
      console.log(payroll);
      this.calculateTotals();
    });
  }

  calculateTotals() {
    this.earningsTotal = this.payroll.details
      .filter(d => d.componentType === 'EARNING')
      .reduce((sum, current) => sum + current.amount, 0);
      
    this.deductionsTotal = this.payroll.details
      .filter(d => d.componentType === 'DEDUCTION')
      .reduce((sum, current) => sum + current.amount, 0);
  }

  statusBadgeClass(status: string | undefined): string{
    if (!status) {
      return 'bg-secondary'; // fallback color for undefined status
    }
    switch (status.toUpperCase()) {
      case 'PENDING':
        return 'bg-warning';
      case 'APPROVED':
        return 'bg-success';
      case 'REJECTED':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }
}
