import { Component, Input, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServiceProvinceService } from '../service-province.service';
import { error } from 'jquery';

@Component({
  selector: 'app-province-form',
  templateUrl: './province-form.component.html',
  styleUrls: ['./province-form.component.css'],
})
export class ProvinceFormComponent {
  @Input() show: boolean = false;
  name: string = '';
  id: string = '';

  constructor(
    private dialogRef: MatDialogRef<ProvinceFormComponent>,
    private apiService: ApiService,
    private provinceDataService: ServiceProvinceService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (data) {
      this.name = data.data.name;
      this.id = data.id;
    } else {
      this.name = '';
      this.id = '';
    }
  }

  handleSubmit(): void {
    const token = localStorage.getItem('token');
    let data = { name: '' };
    let id = this.id;

    data.name = this.name;

    if (token != null) {
      if (id == "") {
        this.apiService.post('provinces', token, data).subscribe(
          (response) => {
            alert('Se agrego el registro');
            this.provinceDataService.triggerProvinceAdded();
          },
          (error) => {
            console.log('Error al agregar el registro', error);
          }
        );
      } else {
        this.apiService.put('provinces', token, data, id).subscribe(
          (response) => {
            alert('Se edito el registro');
            this.provinceDataService.triggerProvinceAdded();
          },
          (error) => {
            console.log('Error al editar el registro', error);
          }
        );
      }

      this.dialogRef.close();
    } else {
      console.log('No se encontró token');
    }
  }

  handleClose(): void {
    this.dialogRef.close();
  }
}
