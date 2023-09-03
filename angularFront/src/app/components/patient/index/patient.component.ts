import { Component, NgModule } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PatientFormComponent } from '../form/patient-form.component';
import { Router } from '@angular/router';
@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {
  constructor(private dialog: MatDialog, private router: Router) { }

  openPatientForm(): void {
    this.router.navigate(['/patient/new']);
  }
}
