import { Component, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-province-form',
  templateUrl: './province-form.component.html',
  styleUrls: ['./province-form.component.css'],
})
export class ProvinceFormComponent {
  @Input() show: boolean = false;
  name: string = '';

  constructor(private dialogRef: MatDialogRef<ProvinceFormComponent>) {}

  handleSubmit(): void {
    // Aquí puedes realizar el envío de datos o cualquier acción que necesites.
    this.dialogRef.close();
  }

  handleClose(): void {
    this.dialogRef.close();
  }
}
