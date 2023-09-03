import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ValidationsService } from 'src/app/service/validations.service';
import { EmergencyContact } from '../emergency-contact.model';

@Component({
  selector: 'app-emergency-contact-form',
  templateUrl: './emergency-contact-form.component.html',
  styleUrls: ['./emergency-contact-form.component.css']
})
export class EmergencyContactFormComponent implements OnInit {
  emergencyContactName: string = '';
  emergencyContactPhoneNumber: string = '';
  emergencyContactId: any = null;

  constructor(
    private dialogRef: MatDialogRef<EmergencyContactFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private validate: ValidationsService,
  ) {
    if (data) {
      if (data.data) {
        this.emergencyContactId = data.data.id ? data.data.id : null;
        this.emergencyContactName = data.data.name;
        this.emergencyContactPhoneNumber = data.data.phoneNumber;
      } else {
        this.emergencyContactId = data.id ? data.id : null;
        this.emergencyContactName = data.name;
        this.emergencyContactPhoneNumber = data.phoneNumber;
      }
    }
  }

  ngOnInit(): void {
  }


  saveEmergencyContact(): void {
    const emergencyContactData: EmergencyContact = {
      id: null,
      name: this.emergencyContactName,
      phoneNumber: this.emergencyContactPhoneNumber
    };

    this.dialogRef.close(emergencyContactData);
  }

  handleClose(): void {
    const emergencyContactData: EmergencyContact = {
      id: null,
      name: this.emergencyContactName,
      phoneNumber: this.emergencyContactPhoneNumber
    };

    this.dialogRef.close(emergencyContactData);
  }
}