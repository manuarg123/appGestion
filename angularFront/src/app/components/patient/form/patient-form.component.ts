import { Component, Input, Inject, OnInit, OnDestroy } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServicePatientService } from '../service-patient.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PersonFormComponent } from '../../person/form/person-form.component';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent extends PersonFormComponent implements OnInit {
  @Input() show: boolean = false;
  firstName: string = '';
  lastName: string = '';
  id: string = '';
  genders: any[] = [];
  gender: string = "";
  genderId: string = "";
  gendersIds: any[] = [];

  constructor(
    private dialogRef: MatDialogRef<PatientFormComponent>,
    private patientDataService: ServicePatientService,
    private serviceApi: ApiService,
    private snackBar: MatSnackBar,
    private dialogMat: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    super(serviceApi, dialogMat, data);
    if (data) {
      this.firstName = data.data.firstName;
      this.lastName = data.data.lastName;
      this.gender = data.data.genderId;
      this.id = data.id;
    } else {
      this.firstName = '';
      this.lastName = '';
      this.gender = '';
      this.id = '';
    }
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.loadGenders();
  }

  handleSubmit(): void {
    const token = localStorage.getItem('token');

    let addressObject = this.getAddressObject();

    const data = {
      firstName: this.firstName,
      lastName: this.lastName,
      genderId: this.gender,
      phones: this.preparePhoneList(),
      emails: this.prepareEmailList(),
      identifications: this.prepareIdentificationList(),
      addresses: [addressObject]
    };
    
    let id = this.id;

    if (token != null) {

      if (id == "") {
        this.serviceApi.post('patients', token, data).subscribe(
          (response) => {
            this.patientDataService.triggerPatientAdded();
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
        this.serviceApi.put('patients', token, data, id).subscribe(
          (response) => {
            this.patientDataService.triggerPatientAdded();
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

  loadGenders(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.serviceApi.all('genders', token).subscribe(
        (response: any[]) => {
          response.forEach((gender: any) => {
            this.gendersIds = response.map((gender: any) => gender.id);
          });
          this.genders = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }
}