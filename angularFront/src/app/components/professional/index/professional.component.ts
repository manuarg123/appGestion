import { Component, NgModule } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ProfessionalFormComponent } from '../form/professional-form.component';
@Component({
  selector: 'app-professional',
  templateUrl: './professional.component.html',
  styleUrls: ['./professional.component.css']
})
export class ProfessionalComponent {
  constructor(private dialog: MatDialog) { }

  openProfessionalForm(): void {
    this.dialog.open(ProfessionalFormComponent, {
      width:'700px'
    });
  }
}
