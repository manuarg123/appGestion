import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ProvinceFormComponent } from '../form/province-form.component';
@Component({
  selector: 'app-province',
  templateUrl: './province.component.html',
  styleUrls: ['./province.component.css'],
})
export class ProvinceComponent {
  constructor(private dialog: MatDialog) {}

  openProvinceForm(): void {
    this.dialog.open(ProvinceFormComponent, {
      width: '400px', // Puedes ajustar el ancho del cuadro de diálogo según tus necesidades
    });
  }
}
