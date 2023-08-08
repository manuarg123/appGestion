import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { IdentificationTypeFormComponent } from '../form/identification-type-form.component';
@Component({
  selector: 'app-identification-type',
  templateUrl: './identification-type.component.html',
  styleUrls: ['./identification-type.component.css'],
})
export class IdentificationTypeComponent {
  constructor(private dialog: MatDialog) { }

  openIdentificationTypeForm(): void {
    this.dialog.open(IdentificationTypeFormComponent, {
      width: '250px'
    });
  }
}