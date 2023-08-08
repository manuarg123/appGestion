import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { GenderFormComponent } from '../form/gender-form.component';
@Component({
  selector: 'app-gender',
  templateUrl: './gender.component.html',
  styleUrls: ['./gender.component.css'],
})
export class GenderComponent {
  constructor(private dialog: MatDialog) { }

  openGenderForm(): void {
    this.dialog.open(GenderFormComponent, {
      width: '250px'
    });
  }
}