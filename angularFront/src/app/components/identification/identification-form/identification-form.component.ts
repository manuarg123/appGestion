import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ValidationsService } from 'src/app/service/validations.service';
import { Identification } from '../identification.model';

@Component({
  selector: 'app-identification-form',
  templateUrl: './identification-form.component.html',
  styleUrls: ['./identification-form.component.css']
})
export class IdentificationFormComponent implements OnInit {
  identificationValue: string = '';
  identificationId: any = "";
  identificationTypes: any[] = [];
  identificationTypesIds: any[] = [];
  identificationType: string = '';

  constructor(
    private dialogRef: MatDialogRef<IdentificationFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private validate: ValidationsService,
  ) {
    if (data) {
      if (data.data) {
        this.identificationId = data.data.id;
        this.identificationValue = data.data.number;
        this.identificationType = data.data.type.id;
      } else {
        this.identificationId = data.id;
        this.identificationValue = data.number;
        this.identificationType = data.type.id;
      }

    }
  }

  ngOnInit(): void {
    this.loadIdentificationTypes();
  }

  loadIdentificationTypes(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('identificationTypes', token).subscribe(
        (response: any[]) => {
          response.forEach((type: any) => {
            this.identificationTypesIds = response.map((type: any) => type.id);
          });
          this.identificationTypes = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  saveIdentification(): void {
    if (this.identificationValue != "") {
      const selectedType = this.identificationTypesIds.find(id => id == this.identificationType);
      let typeName = "";
      let typeId = "";

      this.identificationTypes.forEach(type => {
        if (type.id == this.identificationType) {
          typeName = type.name;
          typeId = type.id;
        }
      });

      if (selectedType) {
        const identificationData: Identification = {
          id: null,
          number: this.identificationValue,
          type: {
            id: typeId,
            name: typeName
          }
        };

        this.dialogRef.close(identificationData);
      }
    } else {
      this.openSnackBar('Porfavor, ingrese un número de identificacion ', 'Aceptar');
    }
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }
}