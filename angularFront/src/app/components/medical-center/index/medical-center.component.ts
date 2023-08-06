import { Component, NgModule } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MedicalCenterFormComponent } from '../form/medical-center-form.component';
@Component({
  selector: 'app-medical-center',
  templateUrl: './medical-center.component.html',
  styleUrls: ['./medical-center.component.css']
})
export class MedicalCenterComponent {
  constructor(private dialog: MatDialog) { }

  openMedicalCenterForm(): void {
    this.dialog.open(MedicalCenterFormComponent, {
      width:'700px'
    });
  }
}
