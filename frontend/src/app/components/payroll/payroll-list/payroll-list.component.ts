import { Component } from '@angular/core';
import { PaymentStatus, PayrollResponse } from '../../../models/employee';
import { PayrollService } from '../../../services/payroll/payroll.service';

@Component({
  selector: 'app-payroll-list',
  standalone: false,
  templateUrl: './payroll-list.component.html',
  styleUrl: './payroll-list.component.scss'
})
export class PayrollListComponent {
  payrolls: PayrollResponse[] = [];
  filteredPayrolls: PayrollResponse[] = [];
  searchTerm = '';
  statusFilter = '';
  PaymentStatus = PaymentStatus;
  paymentStatuses: string[] = Object.values(PaymentStatus);

  constructor(private payrollService: PayrollService) {}

  ngOnInit() {
    this.loadPayrolls();
  }

  loadPayrolls() {
    this.payrollService.getAllPayrolls().subscribe(data => {
      this.payrolls = data;
      this.filteredPayrolls = data;
    });
  }

  applyFilters() {
    this.filteredPayrolls = this.payrolls.filter(p => {
      const matchesSearch = p.firstName.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesStatus = this.statusFilter ? p.paymentStatus === this.statusFilter : true;
      return matchesSearch && matchesStatus;
    });
  }

  updateStatus(payrollId: number, status: PaymentStatus) {
    this.payrollService.updatePaymentStatus(payrollId, status).subscribe(() => {
      this.loadPayrolls();
    });
  }

  statusBadgeClass(status: string): string {
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
