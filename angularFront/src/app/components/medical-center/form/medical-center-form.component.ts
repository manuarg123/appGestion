import { Component, Input, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServiceMedicalCenterService } from '../service-medical-center.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PhoneFormComponent } from '../../phone/phone-form/phone-form.component';
import { Phone } from '../../phone/phone.model';

@Component({
  selector: 'app-medical-center-form',
  templateUrl: './medical-center-form.component.html',
  styleUrls: ['./medical-center-form.component.css']
})
export class MedicalCenterFormComponent {
  @Input() show: boolean = false;
  name: string = '';
  id: string = '';
  phoneList: Phone[] = [];

  constructor(
    private dialogRef: MatDialogRef<MedicalCenterFormComponent>,
    private apiService: ApiService,
    private medicalCenterDataService: ServiceMedicalCenterService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog,
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

  openPhoneFormDialog(): void {
    const dialogRef = this.dialog.open(PhoneFormComponent, {
      width: '300px',
    });

    dialogRef.afterClosed().subscribe((result: Phone) => {
      if (result) {
        // Agregar el teléfono ingresado a la lista de teléfonos
        this.phoneList.push(result);
      }
    });
  }

  handleSubmit(): void {
    const token = localStorage.getItem('token');
    const data = {
      name: this.name,
      phones: this.preparePhoneList()
    };
    let id = this.id;

    if (token != null) {

      if (id == "") {
        this.apiService.post('medicalCenters', token, data).subscribe(
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
        this.apiService.put('medicalCenters', token, data, id).subscribe(
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

  preparePhoneList() {
    let listPhone: { value: string; typeId: number }[] = [];

    this.phoneList.forEach(phone => {
      let phoneObject = {
        value: phone.number,
        typeId: Number(phone.id)
      };

      listPhone.push(phoneObject);
    });

    return listPhone;
  }
}
