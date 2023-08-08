import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpecialtyFormComponent } from '../form/specialty-form.component';
@Component({
  selector: 'app-specialty',
  templateUrl: './specialty.component.html',
  styleUrls: ['./specialty.component.css'],
})
export class SpecialtyComponent {
  constructor(private dialog: MatDialog) { }

  openSpecialtyForm(): void {
    this.dialog.open(SpecialtyFormComponent, {
      width: '250px'
    });
  }
}