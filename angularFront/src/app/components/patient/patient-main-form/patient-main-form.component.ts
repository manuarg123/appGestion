import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';
import { Router } from '@angular/router';
import { Phone } from '../../phone/phone.model';
import { Email } from '../../email/email.model';
import { Identification } from '../../identification/identification.model';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';
import { PhoneFormComponent } from '../../phone/phone-form/phone-form.component';
import { EmailFormComponent } from '../../email/email-form/email-form.component';
import { IdentificationFormComponent } from '../../identification/identification-form/identification-form.component';
import { EmergencyContactFormComponent } from '../../emergency-contact/emergency-contact-form/emergency-contact-form.component';
import { EmergencyContact } from '../../emergency-contact/emergency-contact.model';

@Component({
  selector: 'app-patient-main-form',
  templateUrl: './patient-main-form.component.html',
  styleUrls: ['./patient-main-form.component.css']
})
export class PatientMainFormComponent implements OnInit {
  phoneList: Phone[] = [];
  emailList: Email[] = [];
  identificationList: Identification[] = [];
  emergencyContactsList: EmergencyContact[] = [];
  patientId: any = "";
  patientData: any = {};
  locations: any[] = [];
  location: string = "";
  locationsIds: any[] = [];
  provinces: any[] = [];
  province: string = "";
  provincesIds: any[] = [];
  street: string = "";
  number: string = "";
  section: string = "";
  apartment: string = "";
  floor: string = "";
  zip: string = "";
  genders: any[] = [];
  gender: string = "";
  genderId: string = "";
  gendersIds: any[] = [];
  firstName: string = '';
  lastName: string = '';
  smoker: boolean = false;
  medicalHistory: string = "";

  constructor(private route: ActivatedRoute, private apiService: ApiService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.patientId = this.route.snapshot.paramMap.get('id');
    if (this.patientId != "new") {
      this.loadPatient(this.patientId);
    }
    this.loadLocations();
    this.loadProvinces();
    this.loadGenders();
  }

  //////////////////////////////HANDLE ADDRESS //////////////////////////////////////////////
  loadLocations(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('locations', token).subscribe(
        (response: any[]) => {
          response.forEach((location: any) => {
            this.locationsIds = response.map((location: any) => location.id);
          });
          this.locations = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
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
  //////////////////////////////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////HANDLE PHONE//////////////////////////////////
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

  handleEditPhone(id: any, number: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      if (id) {
        this.apiService.getByid('phones', token, id).subscribe(
          (phoneData) => {
            const dialogRef = this.dialog.open(PhoneFormComponent, {
              data: phoneData,
              width: '300px',
            });

            dialogRef.componentInstance.phoneId = id;

            dialogRef.afterClosed().subscribe((result: Phone) => {
              result.id = id;
              if (result) {
                const index = this.phoneList.findIndex((phone) => phone.id === result.id);
                if (index !== -1) {
                  this.phoneList[index] = result;
                }
              }
            });
          }
        );
      } else {
        const index = this.phoneList.findIndex((phone) => phone.number === number);
        if (index !== -1) {
          const phoneData = this.phoneList[index];
          const dialogRef = this.dialog.open(PhoneFormComponent, {
            data: phoneData,
            width: '300px',
          });

          dialogRef.componentInstance.phoneId = phoneData.id;

          dialogRef.afterClosed().subscribe((result: Phone) => {
            result.id = phoneData.id;
            if (result) {
              this.phoneList[index] = result;
            }
          });
        }
      }
    }
  }

  handleDeletePhone(id: any, number: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          if (id) {
            this.apiService.delete('phones', token, id).subscribe(
              (data) => {
                this.phoneList = this.phoneList.filter((phone) => phone.id !== id);
              },
              (error) => {
                console.log('Error al eliminar', error);
              }
            );
          } else {
            this.phoneList = this.phoneList.filter((phone) => phone.number !== number);
          }
        }
      });
    } else {
      console.log('No se encontró token');
    }
  }
  //////////////////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////HANDLE EMAIL//////////////////////////////////////////////////////////////////////////////

  openEmailFormDialog(): void {
    const dialogRef = this.dialog.open(EmailFormComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe((result: Email) => {
      this.emailList.push(result);
    });
  }

  handleEditEmail(id: any, value: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      if (id) {
        this.apiService.getByid('emails', token, id).subscribe(
          (emailData) => {
            const dialogRef = this.dialog.open(EmailFormComponent, {
              data: emailData,
              width: '300px',
            });

            dialogRef.componentInstance.emailId = id;

            dialogRef.afterClosed().subscribe((result: Email) => {
              result.id = id;
              if (result) {
                const index = this.emailList.findIndex((email) => email.id === result.id);
                if (index !== -1) {
                  this.emailList[index] = result;
                }
              }
            });
          }
        );
      } else {
        const index = this.emailList.findIndex((email) => email.value === value);
        if (index !== -1) {
          const emailData = this.emailList[index];
          const dialogRef = this.dialog.open(EmailFormComponent, {
            data: emailData,
            width: '300px',
          });

          dialogRef.componentInstance.emailId = emailData.id;

          dialogRef.afterClosed().subscribe((result: Email) => {
            result.id = emailData.id;
            if (result) {
              this.emailList[index] = result;
            }
          });
        }
      }
    }
  }

  handleDeleteEmail(id: any, value: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          if (id) {
            this.apiService.delete('emails', token, id).subscribe(
              (data) => {
                this.emailList = this.emailList.filter((email) => email.id !== id);
              },
              (error) => {
                console.log('Error al eliminar', error);
              }
            );
          } else {
            this.emailList = this.emailList.filter((email) => email.value !== value);
          }
        }
      });
    } else {
      console.log('No se encontró token');
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////HANDLE IDENTIFICATION//////////////////////////////////////////////////////////////////////////////

  openIdentificationFormDialog(): void {
    const dialogRef = this.dialog.open(IdentificationFormComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe((result: Identification) => {
      this.identificationList.push(result);
    });
  }

  prepareIdentificationList() {
    let listIdentification: { value: string; typeId: number }[] = [];

    this.identificationList.forEach(identification => {
      let identificationObject = {
        id: identification.id ? identification.id : null,
        value: identification.number,
        typeId: Number(identification.type.id)
      };

      listIdentification.push(identificationObject);
    });

    return listIdentification;
  }

  handleEditIdentification(id: any, number: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      if (id) {
        this.apiService.getByid('identifications', token, id).subscribe(
          (identificationData) => {
            const dialogRef = this.dialog.open(IdentificationFormComponent, {
              data: identificationData,
              width: '300px',
            });

            dialogRef.componentInstance.identificationId = id;

            dialogRef.afterClosed().subscribe((result: Identification) => {
              result.id = id;
              if (result) {
                const index = this.identificationList.findIndex((identification) => identification.id === result.id);
                if (index !== -1) {
                  this.identificationList[index] = result;
                }
              }
            });
          }
        );
      } else {
        const index = this.identificationList.findIndex((identification) => identification.number === number);
        if (index !== -1) {
          const identificationData = this.identificationList[index];
          const dialogRef = this.dialog.open(IdentificationFormComponent, {
            data: identificationData,
            width: '300px',
          });

          dialogRef.componentInstance.identificationId = identificationData.id;

          dialogRef.afterClosed().subscribe((result: Identification) => {
            result.id = identificationData.id;
            if (result) {
              this.identificationList[index] = result;
            }
          });
        }
      }

    }
  }

  handleDeleteIdentification(id: any, value: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          if (id) {
            this.apiService.delete('identifications', token, id).subscribe(
              (data) => {
                this.identificationList = this.identificationList.filter((identification) => identification.id !== id);
              },
              (error) => {
                console.log('Error al eliminar', error);
              }
            );
          } else {
            this.identificationList = this.identificationList.filter((identification) => identification.number !== value);
          }
        }
      });
    } else {
      console.log('No se encontró token');
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////HANDLE EMERGENCY CONTACT//////////////////////////////////////////////////////////////////////////////
  openEmergencyContactFormDialog(): void {
    const dialogRef = this.dialog.open(EmergencyContactFormComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe((result: EmergencyContact) => {
      if (result) {
        this.emergencyContactsList.push(result);
      }
    });
  }

  handleEditEmergencyContact(id: any, name: string, phoneNumber: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      if (id != null) {
        this.apiService.getByid('emergencyContacts', token, id).subscribe(
          (emergencyContactData) => {
            const dialogRef = this.dialog.open(EmergencyContactFormComponent, {
              data: emergencyContactData,
              width: '300px',
            });

            dialogRef.componentInstance.emergencyContactId = id;

            dialogRef.afterClosed().subscribe((result: EmergencyContact) => {
              result.id = id;
              if (result) {
                const index = this.emergencyContactsList.findIndex((emergencyContact) => emergencyContact.id === result.id);
                if (index !== -1) {
                  this.emergencyContactsList[index] = result;
                }
              }
            });
          }
        );
      } else {
        const index = this.emergencyContactsList.findIndex((emergencyContact) => emergencyContact.name === name && emergencyContact.phoneNumber === phoneNumber);
        if (index !== -1) {
          const emergencyContactData = this.emergencyContactsList[index];
          const dialogRef = this.dialog.open(EmergencyContactFormComponent, {
            data: emergencyContactData,
            width: '300px',
          });

          dialogRef.componentInstance.emergencyContactId = emergencyContactData.id;

          dialogRef.afterClosed().subscribe((result: EmergencyContact) => {
            result.id = emergencyContactData.id;
            if (result) {
              this.emergencyContactsList[index] = result;
            }
          });
        }
      }

    }
  }

  handleDeleteEmergencyContact(id: any, value: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          if (id) {
            this.apiService.delete('emergencyContacts', token, id).subscribe(
              (data) => {
                this.emergencyContactsList = this.emergencyContactsList.filter((emergencyContact) => emergencyContact.id !== id);
              },
              (error) => {
                console.log('Error al eliminar', error);
              }
            );
          } else {
            this.emergencyContactsList = this.emergencyContactsList.filter((emergencyContact) => emergencyContact.name !== value);
          }
        }
      });
    } else {
      console.log('No se encontró token');
    }
  }
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  loadPatient(patientId: any): void {
    const token = localStorage.getItem("token");
    if (token != null) {
      this.apiService.getByid('patients', token, patientId).subscribe(
        (patientData) => {
          this.patientData = patientData;
          this.location = this.patientData?.data?.addresses[0]?.locationId ?? "";
          this.province = this.patientData?.data?.addresses[0]?.provinceId ?? "";
          this.street = this.patientData?.data?.addresses[0]?.street ?? "";
          this.number = this.patientData?.data?.addresses[0]?.number ?? "";
          this.section = this.patientData?.data?.addresses[0]?.section ?? "";
          this.floor = this.patientData?.data?.addresses[0]?.floor ?? "";
          this.apartment = this.patientData?.data?.addresses[0]?.apartment ?? "";
          this.zip = this.patientData?.data?.addresses[0]?.zip ?? "";
          this.gender = this.patientData?.data?.genderId;
          this.smoker = this.patientData?.data?.isSmoker;
          this.medicalHistory = this.patientData?.data?.medicalHistory;
          this.phoneList = this.patientData?.data?.phones;
          this.emailList = this.patientData?.data?.emails;
          this.identificationList = this.patientData?.data?.identifications;
          this.emergencyContactsList = this.patientData?.data?.emergencyContacts;
        }
      );
    }
  }

  handleReturn() {
    this.router.navigate(['/patient']);
  }

  loadGenders(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('genders', token).subscribe(
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

  onSmokerClick(value: boolean): void {
    this.smoker = value;
  }
}
