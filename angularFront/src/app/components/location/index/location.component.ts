import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LocationFormComponent } from '../form/location-form.component';
@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css'],
})
export class LocationComponent {
  constructor(private dialog: MatDialog) { }

  openLocationForm(): void {
    this.dialog.open(LocationFormComponent, {
      width: '250px'
    });
  }
}