import { Component, Input, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServiceLocationService } from '../service-location.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-location-form',
  templateUrl: './location-form.component.html',
  styleUrls: ['./location-form.component.css'],
})
export class LocationFormComponent implements OnInit {
  @Input() show: boolean = false;
  name: string = '';
  id: string = '';
  provinces: any[] = [];
  province: string = "";
  provinceId: string = "";
  provincesIds: any[] = [];

  constructor(
    private dialogRef: MatDialogRef<LocationFormComponent>,
    private apiService: ApiService,
    private locationDataService: ServiceLocationService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (data) {
      this.name = data.data.name;
      this.province = data.data.province.id;
      this.id = data.id;
    } else {
      this.name = '';
      this.id = '';
      this.province = "";
    }
  }

  ngOnInit(): void {
    this.loadProvinces();
  }

  handleSubmit(): void {
    const token = localStorage.getItem('token');
    let data = { name: '', provinceId: this.province };
    let id = this.id;

    data.name = this.name;

    if (token != null) {
      if (id == "") {
        this.apiService.post('locations', token, data).subscribe(
          (response) => {
            this.locationDataService.triggerLocationAdded();
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
        this.apiService.put('locations', token, data, id).subscribe(
          (response) => {
            this.locationDataService.triggerLocationAdded();
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

  loadProvinces(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('provinces', token).subscribe(
        (response: any[]) => {
          response.forEach((province: any) => {
            this.provincesIds = response.map((province: any) => province.id);
          });
          this.provinces = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }
}
