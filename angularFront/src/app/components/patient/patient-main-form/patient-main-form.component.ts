import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from 'src/app/service/api.service';
import { Router } from '@angular/router';
import { Phone } from '../../phone/phone.model';
import { Email } from '../../email/email.model';
import { Identification } from '../../identification/identification.model';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';
import { PhoneFormComponent } from '../../phone/phone-form/phone-form.component';
import { EmailFormComponent } from '../../email/email-form/email-form.component';
import { IdentificationFormComponent } from '../../identification/identification-form/identification-form.component';
import { EmergencyContactFormComponent } from '../../emergency-contact/emergency-contact-form/emergency-contact-form.component';
import { EmergencyContact } from '../../emergency-contact/emergency-contact.model';
import { PlanSocialWorkFormComponent } from '../plan-form/plan-social-work-form.component';
import { Plan } from '../plan-form/plan.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ServicePatientService } from '../service-patient.service';


@Component({
  selector: 'app-patient-main-form',
  templateUrl: './patient-main-form.component.html',
  styleUrls: ['./patient-main-form.component.css']
})
export class PatientMainFormComponent implements OnInit {
  id: any = "";
  phoneList: Phone[] = [];
  emailList: Email[] = [];
  identificationList: Identification[] = [];
  emergencyContactsList: EmergencyContact[] = [];
  planSocialWorksList: Plan[] = [];
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
  occupation: string = "";
  addressId: any = "";

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private router: Router,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private patientDataService: ServicePatientService) { }

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
  //////////////////////////////////////////////////////////////////HANDLE SOCIAL WOrK PLAN//////////////////////////////////////////////////////////////////////////////
  openPlanFormDialog(): void {
    const dialogRef = this.dialog.open(PlanSocialWorkFormComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe((result: Plan) => {
      if (result) {
        this.planSocialWorksList.push(result);
      }
    });
  }

  handleDeletePlanSocialWork(code: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          if (code) {
            this.planSocialWorksList = this.planSocialWorksList.filter((planSocialWork) => planSocialWork.code !== code);
          }
        }
      });
    } else {
      console.log("No se encontró token");
    }
  }

  handleEditPlanSocialWork(code: string) {
    const token = localStorage.getItem('token');
    if (token != null) {
      console.log(code)
      console.log(this.planSocialWorksList)
      if (code) {
        const index = this.planSocialWorksList.findIndex((planSocialWork) => planSocialWork.code === code);

        if (index !== -1) {
          const planSocialWork = this.planSocialWorksList[index];
          const dialogRef = this.dialog.open(PlanSocialWorkFormComponent, {
            data: planSocialWork,
            width: '300px',
          });

          dialogRef.componentInstance.socialWork = planSocialWork.id;

          dialogRef.afterClosed().subscribe((result: Plan) => {
            result.id = planSocialWork.id;
            if (result) {
              this.planSocialWorksList[index] = result;
            }
          });
        }
      }
    } else {
      console.log("No se encontró token");
    }
  }
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  loadPatient(patientId: any): void {
    const token = localStorage.getItem("token");
    if (token != null) {
      this.apiService.getByid('patients', token, patientId).subscribe(
        (patientData) => {
          this.patientData = patientData;
          this.firstName = this.patientData?.data?.firstName;
          this.lastName = this.patientData?.data?.lastName;
          this.occupation = this.patientData?.data?.occupation;
          this.addressId = this.patientData?.data?.addresses[0]?.id ?? "";
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
          this.planSocialWorksList = this.patientData?.data?.planSocialWorkDTOS;
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

  handleSubmit() {
    const token = localStorage.getItem('token');
    let addressObject = this.getAddressObject();

    let id = this.patientId != "new" ? this.patientId : "";

    const data = {
      firstName: this.firstName,
      lastName: this.lastName,
      genderId: this.gender,
      isSmoker: this.smoker,
      occupation: this.occupation,
      medicalHistory: this.medicalHistory,
      birthday: null,
      addresses: this.getAddressObject(),
      phones: this.preparePhoneList(),
      emails: this.prepareEmailList(),
      identifications: this.prepareIdentificationList(),
      socialWorksIds: this.preparePlanSocialWorkList()[1],
      plansIds: this.preparePlanSocialWorkList()[0],
      emergencyContacts: id != "" ? this.prepareEmergencyContactsList(id) : this.prepareEmergencyContactsList(),
      clinicHistories: []
    }
    if (token != null) {
      if (id == "") {
        this.apiService.post("patients", token, data).subscribe(
          (response) => {
            this.patientDataService.triggerPatientAdded;
            this.openSnackBar("Registro agregado con éxito, seras redirigido a la lista de pacientes", "Aceptar");

            setTimeout(() => {
              let redirectUrl = window.location.origin + '/patient';
              window.location.href = redirectUrl;
            }, 3000);
          },
          (error) => {
            console.log("ocurrio un error")
          }
        );
      } else {
        this.apiService.put("patients", token, data, id).subscribe(
          (response) => {
            this.patientDataService.triggerPatientAdded;
            this.openSnackBar("Registro editado con éxito", "Aceptar");
          },
          (error) => {
            console.log("ocurrio un error")
          }
        );
      }
    } else {
      console.log('No se encontró token');
    }
  }

  preparePhoneList() {
    let listPhone: { value: string; typeId: number }[] = [];

    this.phoneList.forEach(phone => {
      let phoneObject = {
        id: phone.id ? phone.id : null,
        value: phone.number,
        typeId: Number(phone.type.id)
      };

      listPhone.push(phoneObject);
    });

    return listPhone;
  }

  prepareEmailList() {
    let listEmail: { value: string; typeId: number }[] = [];

    this.emailList.forEach(email => {
      let emailObject = {
        id: email.id ? email.id : null,
        value: email.value,
        typeId: Number(email.type.id)
      };

      listEmail.push(emailObject);
    });

    return listEmail;
  }

  getAddressObject() {
    let addressObject = {
      id: this.addressId ? this.addressId : null,
      street: this.street ? this.street : null,
      section: this.section ? this.section : null,
      number: this.number ? this.number : null,
      floor: this.floor ? this.floor : null,
      apartment: this.apartment ? this.apartment : null,
      zip: this.zip ? this.zip : null,
      locationId: this.location ? this.location : null,
      provinceId: this.province ? this.province : null
    }

    let allNull = true;

    if (addressObject.street) {
      allNull = false;
    }

    if (addressObject.section) {
      allNull = false;
    }

    if (addressObject.number) {
      allNull = false;
    }

    if (addressObject.floor) {
      allNull = false;
    }

    if (addressObject.apartment) {
      allNull = false;
    }

    if (addressObject.zip) {
      allNull = false;
    }

    if (addressObject.locationId) {
      allNull = false;
    }

    if (addressObject.provinceId) {
      allNull = false;
    }

    return !allNull ? [addressObject] : [];
  }

  prepareEmergencyContactsList(person_id: any = null) {
    let listEmergencyContacts: { id: any; name: string; phoneNumber: string; personId: any }[] = [];

    if (this.emergencyContactsList.length != 0) {
      this.emergencyContactsList.forEach(emergencyContact => {
        let emergencyContactObject = {
          id: emergencyContact.id ? emergencyContact.id : null,
          name: emergencyContact.name ? emergencyContact.name : "",
          phoneNumber: emergencyContact.phoneNumber ? emergencyContact.phoneNumber : "",
          personId: person_id != "" ? person_id : null
        };

        listEmergencyContacts.push(emergencyContactObject);
      });
    }

    return listEmergencyContacts;
  }

  preparePlanSocialWorkList() {
    let listPlans: any[] = [];
    let listSocialWorks: any[] = [];
    let listPlanSocialWorkIds: any[] = [];

    if (this.planSocialWorksList.length != 0) {
      this.planSocialWorksList.forEach(planSocialWork => {
        if (planSocialWork.id) {
          listPlans.push(Number(planSocialWork.id));
        } else {
          listSocialWorks.push(Number(planSocialWork.socialWork.id));
        }
      })
    }

    listPlanSocialWorkIds.push(listPlans);
    listPlanSocialWorkIds.push(listSocialWorks);
    return listPlanSocialWorkIds;
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }
}
