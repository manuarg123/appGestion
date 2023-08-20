import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ValidationsService } from 'src/app/service/validations.service';
import { MedicalCenter } from './medical-center.model';

@Component({
  selector: 'app-medical-center-form-min',
  templateUrl: './medical-center-form-min.component.html',
  styleUrls: ['./medical-center-form-min.component.css']
})
export class MedicalCenterFormMinComponent implements OnInit {
  medicalCenterName: string = '';
  medicalCenterId: any = "";
  medicalCentersIds: any[] = [];
  medicalCenters: any[] = [];
  selectedMedicalCenter: any = {};


  constructor(
    private dialogRef: MatDialogRef<MedicalCenterFormMinComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private validate: ValidationsService,
  ) {
    if (data) {
      this.medicalCenterId = data.data.id;
      this.medicalCenterName = data.data.name;
    }
  }

  ngOnInit(): void {
    this.loadMedicalCenters();
  }

  loadMedicalCenters(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('medicalCenters', token).subscribe(
        (response: any[]) => {
          response.forEach((medicalCenter: any) => {
            this.medicalCentersIds = response.map((medicalCenter: any) => medicalCenter.id);
          });
          this.medicalCenters = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  saveMedicalCenter(): void {
      const token = localStorage.getItem("token");
      const selectedMedicalCenter = this.medicalCentersIds.find(id => id == this.medicalCenterName);
      let data = null;

      if(token) {
        this.apiService.getMedicalCenterDTOById('medicalCenters', token, this.medicalCenterName).subscribe(
          (medicalCenter: any) => {
            this.selectedMedicalCenter = medicalCenter;
            let id = medicalCenter.id;
            let name = medicalCenter.name;   
            let complete_address = medicalCenter.direction;

            const medicalCenterData: MedicalCenter = {
              id: id,
              name: name,
              addresses: [
                {
                  complete_address: complete_address
                }
              ]
            }
            this.dialogRef.close(medicalCenterData);
          },
          (error) => {
            console.error(error);
          }
        );
      }
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }
}