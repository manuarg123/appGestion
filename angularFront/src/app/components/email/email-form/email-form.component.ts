import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ValidationsService } from 'src/app/service/validations.service';
import { Email } from '../email.model';

@Component({
  selector: 'app-email-form',
  templateUrl: './email-form.component.html',
  styleUrls: ['./email-form.component.css']
})
export class EmailFormComponent implements OnInit {
  emailValue: string = '';
  emailId: any = "";
  emailTypes: any[] = [];
  emailTypesIds: any[] = [];
  emailType: string = '';

  constructor(
    private dialogRef: MatDialogRef<EmailFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private validate: ValidationsService,
  ) {
    if (data) {
      if (data.data) {
        this.emailId = data.data.id;
        this.emailValue = data.data.value;
        this.emailType = data.data.type.id;
      } else {
        this.emailId = data.id;
        this.emailValue = data.value;
        this.emailType = data.type.id;
      }
    }
  }

  ngOnInit(): void {
    this.loadEmailTypes();
  }

  loadEmailTypes(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('emailTypes', token).subscribe(
        (response: any[]) => {
          response.forEach((type: any) => {
            this.emailTypesIds = response.map((type: any) => type.id);
          });
          this.emailTypes = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  saveEmail(): void {
    if (this.validate.isEmail(this.emailValue)) {
      const selectedType = this.emailTypesIds.find(id => id == this.emailType);
      let typeName = "";
      let typeId = "";

      this.emailTypes.forEach(type => {
        if (type.id == this.emailType) {
          typeName = type.name;
          typeId = type.id;
        }
      });

      if (selectedType) {
        const emailData: Email = {
          id: null,
          value: this.emailValue,
          type: {
            id: typeId,
            name: typeName
          }
        };

        this.dialogRef.close(emailData);
      }
    } else {
      this.openSnackBar('Porfavor, ingrese un email con formato correcto ', 'Aceptar');
    }
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }
}