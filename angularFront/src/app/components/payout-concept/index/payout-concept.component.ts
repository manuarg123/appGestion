import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PayoutConceptFormComponent } from '../form/payout-concept-form.component';
@Component({
  selector: 'app-payout-concept',
  templateUrl: './payout-concept.component.html',
  styleUrls: ['./payout-concept.component.css'],
})
export class PayoutConceptComponent {
  constructor(private dialog: MatDialog) { }

  openPayoutConceptForm(): void {
    this.dialog.open(PayoutConceptFormComponent, {
      width: '250px'
    });
  }
}