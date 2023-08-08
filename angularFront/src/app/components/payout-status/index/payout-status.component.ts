import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PayoutStatusFormComponent } from '../form/payout-status-form.component';
@Component({
  selector: 'app-payout-status',
  templateUrl: './payout-status.component.html',
  styleUrls: ['./payout-status.component.css'],
})
export class PayoutStatusComponent {
  constructor(private dialog: MatDialog) { }

  openPayoutStatusForm(): void {
    this.dialog.open(PayoutStatusFormComponent, {
      width: '250px'
    });
  }
}