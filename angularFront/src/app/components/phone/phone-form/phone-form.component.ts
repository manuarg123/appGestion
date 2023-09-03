import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ValidationsService } from 'src/app/service/validations.service';
import { Phone } from '../phone.model';

@Component({
  selector: 'app-phone-form',
  templateUrl: './phone-form.component.html',
  styleUrls: ['./phone-form.component.css']
})
export class PhoneFormComponent implements OnInit {
  phoneNumber: string = '';
  phoneId: any = "";
  phoneTypes: any[] = [];
  phoneTypesIds: any[] = [];
  phoneType: string = '';

  constructor(
    private dialogRef: MatDialogRef<PhoneFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private validate: ValidationsService,
  ) {
    if (data) {
      if (data.data) {
        this.phoneId = data.data.id;
        this.phoneNumber = data.data.number;
        this.phoneType = data.data.type.id;
      } else {
        this.phoneId = data.id;
        this.phoneNumber = data.number;
        this.phoneType = data.type.id;
      }   
    }
  }

  ngOnInit(): void {
    this.loadPhoneTypes();
  }

  loadPhoneTypes(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('phoneTypes', token).subscribe(
        (response: any[]) => {
          response.forEach((type: any) => {
            this.phoneTypesIds = response.map((type: any) => type.id);
          });
          this.phoneTypes = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  savePhone(): void {
    if (this.validate.isNumber(this.phoneNumber)) {
      const selectedType = this.phoneTypesIds.find(id => id == this.phoneType);
      let typeName = "";
      let typeId = "";

      this.phoneTypes.forEach(type => {
        if (type.id == this.phoneType) {
          typeName = type.name;
          typeId = type.id;
        }
      });

      if (selectedType) {
        const phoneData: Phone = {
          id: null,
          number: this.phoneNumber,
          type: {
            id: typeId,
            name: typeName
          }
        };

        this.dialogRef.close(phoneData);
      }
    } else {
      this.openSnackBar('Porfavor, ingrese solo números y sin "-" ', 'Aceptar');
    }
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }
}