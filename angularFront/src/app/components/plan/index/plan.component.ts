import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PlanFormComponent } from '../form/plan-form.component';
@Component({
  selector: 'app-plan',
  templateUrl: './plan.component.html',
  styleUrls: ['./plan.component.css'],
})
export class PlanComponent {
  constructor(private dialog: MatDialog) { }

  openPlanForm(): void {
    this.dialog.open(PlanFormComponent, {
      width: '250px'
    });
  }
}