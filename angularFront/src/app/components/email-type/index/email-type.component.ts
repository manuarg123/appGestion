import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmailTypeFormComponent } from '../form/email-type-form.component';
@Component({
  selector: 'app-email-type',
  templateUrl: './email-type.component.html',
  styleUrls: ['./email-type.component.css'],
})
export class EmailTypeComponent {
  constructor(private dialog: MatDialog) { }

  openEmailTypeForm(): void {
    this.dialog.open(EmailTypeFormComponent, {
      width: '250px'
    });
  }
}