import { Component, NgModule } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PatientFormComponent } from '../form/patient-form.component';
@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {
  constructor(private dialog: MatDialog) { }

  openPatientForm(): void {
    this.dialog.open(PatientFormComponent, {
      width:'700px'
    });
  }
}
