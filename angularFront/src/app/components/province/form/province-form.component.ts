import { Component, Input, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServiceProvinceService } from '../service-province.service';
import { MatSnackBar } from '@angular/material/snack-bar';

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
    private snackBar: MatSnackBar,
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
            this.provinceDataService.triggerProvinceAdded();
            this.dialogRef.close();
          },
          (error) => {
            if (typeof error.error.error === 'string') {
              if (error.error.error == "Record with the same name already exists.") {
                this.openSnackBar('Ya existe un registro con ese nombre, ingrese otro diferente', 'Aceptar');
              }
            } else {
              if (error.error.error[0] == "Name cannot be blank") {
                this.openSnackBar('No puede dejar en blanco el nombre, ingrese un registro', 'Aceptar');
              }
            }
          }
        );
      } else {
        this.apiService.put('provinces', token, data, id).subscribe(
          (response) => {
            this.provinceDataService.triggerProvinceAdded();
            this.dialogRef.close();
          },
          (error) => {
            if (typeof error.error.error === 'string') {
              if (error.error.error == "Record with the same name already exists.") {
                this.openSnackBar('Ya existe un registro con ese nombre, ingrese otro diferente', 'Aceptar');
              }
            } else {
              if (error.error.error[0] == "Name cannot be blank") {
                this.openSnackBar('No puede dejar en blanco el nombre, ingrese un registro', 'Aceptar');
              }
            }
          }
        );
      }
    } else {
      console.log('No se encontró token');
    }
  }

  handleClose(): void {
    this.dialogRef.close();
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }
}
