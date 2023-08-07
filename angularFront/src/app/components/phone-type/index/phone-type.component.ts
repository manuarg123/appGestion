import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PhoneTypeFormComponent } from '../form/phone-type-form.component';
@Component({
  selector: 'app-phone-type',
  templateUrl: './phone-type.component.html',
  styleUrls: ['./phone-type.component.css'],
})
export class PhoneTypeComponent {
  constructor(private dialog: MatDialog) { }

  openPhoneTypeForm(): void {
    this.dialog.open(PhoneTypeFormComponent, {
      width: '250px'
    });
  }
}