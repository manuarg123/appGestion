import { Component, Input, Inject, OnInit, OnDestroy } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServiceProfessionalService } from '../service-professional.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PersonFormComponent } from '../../person/form/person-form.component';
import { MedicalCenterFormMinComponent } from '../../medical-center/formMin/medical-center-form-min.component';
import { MedicalCenter } from '../../medical-center/formMin/medical-center.model';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-professional-form',
  templateUrl: './professional-form.component.html',
  styleUrls: ['./professional-form.component.css']
})
export class ProfessionalFormComponent extends PersonFormComponent implements OnInit {
  @Input() show: boolean = false;
  firstName: string = '';
  lastName: string = '';
  mp: string = '';
  id: string = '';
  genders: any[] = [];
  gender: string = "";
  genderId: string = "";
  gendersIds: any[] = [];
  medicalCenters: any[] = [];
  medicalCenter: string = "";
  medicalCenterId: string = "";
  medicalCentersIds: any[] = [];
  medicalCenterList: MedicalCenter[] = [];
  specialties: any[] = [];
  specialty: string = "";
  specialtyId: string = "";
  specialtiesIds: any[] = [];

  constructor(
    private dialogRef: MatDialogRef<ProfessionalFormComponent>,
    private professionalDataService: ServiceProfessionalService,
    private serviceApi: ApiService,
    private snackBar: MatSnackBar,
    private dialogMat: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    super(serviceApi, dialogMat, data);
    if (data) {
      this.firstName = data.data.firstName;
      this.lastName = data.data.lastName;
      this.gender = data.data.gender.id;
      this.specialty = data.data.speciality.id;
      this.medicalCenterList = data.data.medicalCenters;
      this.mp = data.data.mp;
      this.id = data.id;
    } else {
      this.firstName = '';
      this.lastName = '';
      this.gender = '';
      this.specialty = '';
      this.id = '';
      this.mp = '';
    }
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.loadGenders();
    this.loadSpecialties();
  }

  openMedicalCenterFormMinDialog(): void {
    const dialogRef = this.dialogMat.open(MedicalCenterFormMinComponent, {
      width: '300px',
    });

    dialogRef.afterClosed().subscribe((result: MedicalCenter) => {
      if (result) {
        console.log(result)
        this.medicalCenterList.push(result);
      }
    });
  }

  prepareMedicalCenterList() {
    let listMedicalCenter: any[] = [];
    this.medicalCenterList.forEach(medicalCenter => {
        listMedicalCenter.push(medicalCenter.id);
    });
    
    return listMedicalCenter;
  }

  handleDeleteMedicalCenter(id: any){
    const dialogRef = this.dialogMat.open(ConfirmationDialogComponent, {
    });

    dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          this.medicalCenterList = this.medicalCenterList.filter((medicalCenter) => medicalCenter.id !== id);
        }
    });
  }

  handleSubmit(): void {
    const token = localStorage.getItem('token');

    let addressObject = this.getAddressObject();

    const data = {
      firstName: this.firstName,
      mp: this.mp,
      lastName: this.lastName,
      genderId: this.gender,
      specialityId: this.specialty,
      phones: this.preparePhoneList(),
      emails: this.prepareEmailList(),
      identifications: this.prepareIdentificationList(),
      medicalCenterIds: this.prepareMedicalCenterList(),
      addresses: [addressObject]
    };
    
    let id = this.id;

    if (token != null) {

      if (id == "") {
        this.serviceApi.post('professionals', token, data).subscribe(
          (response) => {
            this.professionalDataService.triggerProfessionalAdded();
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
        this.serviceApi.put('professionals', token, data, id).subscribe(
          (response) => {
            this.professionalDataService.triggerProfessionalAdded();
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

  loadSpecialties(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.serviceApi.all('specialities', token).subscribe(
        (response: any[]) => {
          response.forEach((specialty: any) => {
            this.specialtiesIds = response.map((specialty: any) => specialty.id);
          });
          this.specialties = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }
}