import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PaymentTypeFormComponent } from '../form/payment-type-form.component';
@Component({
  selector: 'app-payment-type',
  templateUrl: './payment-type.component.html',
  styleUrls: ['./payment-type.component.css'],
})
export class PaymentTypeComponent {
  constructor(private dialog: MatDialog) { }

  openPaymentTypeForm(): void {
    this.dialog.open(PaymentTypeFormComponent, {
      width: '250px'
    });
  }
}