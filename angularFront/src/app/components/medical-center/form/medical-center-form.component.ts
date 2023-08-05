import { Component, Input, Inject, OnInit, OnDestroy } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServiceMedicalCenterService } from '../service-medical-center.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PersonFormComponent } from '../../person/form/person-form.component';

@Component({
  selector: 'app-medical-center-form',
  templateUrl: './medical-center-form.component.html',
  styleUrls: ['./medical-center-form.component.css']
})
export class MedicalCenterFormComponent extends PersonFormComponent {
  @Input() show: boolean = false;
  name: string = '';
  id: string = '';

  constructor(
    private dialogRef: MatDialogRef<MedicalCenterFormComponent>,
    private medicalCenterDataService: ServiceMedicalCenterService,
    private serviceApi: ApiService,
    private snackBar: MatSnackBar,
    private dialogMat: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    super(serviceApi, dialogMat);
    if (data) {         
      this.name = data.data.name;
      this.id = data.id;
      this.phoneList = data.data.phones;
      this.emailList = data.data.emails;
    } else {
      this.name = '';
      this.id = '';
    }
  }

  handleSubmit(): void {
    const token = localStorage.getItem('token');
    const data = {
      name: this.name,
      phones: this.preparePhoneList(),
      emails: this.prepareEmailList()
    };
    let id = this.id;

    if (token != null) {

      if (id == "") {
        this.serviceApi.post('medicalCenters', token, data).subscribe(
          (response) => {
            this.medicalCenterDataService.triggerMedicalCenterAdded();
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
        this.serviceApi.put('medicalCenters', token, data, id).subscribe(
          (response) => {
            this.medicalCenterDataService.triggerMedicalCenterAdded();
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

  ///////////////////////Handle PHONE//////////////////////////////

}


///////////////////////////////Handle EMAIL///////////////////////////////////////