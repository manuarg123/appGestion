import { PhoneFormComponent } from "../../phone/phone-form/phone-form.component";
import { EmailFormComponent } from "../../email/email-form/email-form.component";
import { IdentificationFormComponent } from "../../identification/identification-form/identification-form.component";
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { Phone } from '../../phone/phone.model';
import { Email } from "../../email/email.model";
import { Identification } from "../../identification/identification.model";
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';
import { Address } from "../../address/address.model";
import { Component, OnInit, Inject } from '@angular/core';

@Component({
    selector: 'app-person-form',
    templateUrl: './person-form.component.html',
    styleUrls: ['./person-form.component.css']
})
export class PersonFormComponent implements OnInit {
    phoneList: Phone[] = [];
    emailList: Email[] = [];
    identificationList: Identification[] = [];
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
    addressId: any = "";
    constructor(
        private apiService: ApiService,
        private dialog: MatDialog,
        @Inject(MAT_DIALOG_DATA) public dataPerson: any
    ) {
        if (dataPerson) {
            this.location = dataPerson?.data?.addresses?.[0]?.location?.id ?? "";
            this.province = dataPerson?.data?.addresses?.[0]?.province?.id ?? "";
            this.street = dataPerson?.data?.addresses?.[0]?.street ?? "";
            this.number = dataPerson?.data?.addresses?.[0]?.number ?? "";
            this.apartment = dataPerson?.data?.addresses?.[0]?.apartment ?? "";
            this.section = dataPerson?.data?.addresses?.[0]?.section ?? "";
            this.floor = dataPerson?.data?.addresses?.[0]?.floor ?? "";
            this.zip = dataPerson?.data?.addresses?.[0]?.zip ?? "";
            this.addressId = dataPerson?.data?.addresses?.[0]?.id ?? "";
            console.log(dataPerson.data)
            this.phoneList = dataPerson.data.phones;
            this.emailList = dataPerson.data.emails;
            this.identificationList = dataPerson.data.identifications;
        }
    }

    ngOnInit(): void {
        this.loadLocations();
        this.loadProvinces();
    }

    ///////////////////////////////////////////////HANDLE PHONE/////////////////////////////////////////////////////////////////////////////////

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

    handleEditPhone(id: any) {
        const token = localStorage.getItem('token');
        if (token != null) {
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

    //////////////////////////////////////////////////////////////////HANDLE EMAIL//////////////////////////////////////////////////////////////////////////////

    openEmailFormDialog(): void {
        const dialogRef = this.dialog.open(EmailFormComponent, {
            width: '300px'
        });

        dialogRef.afterClosed().subscribe((result: Email) => {
            this.emailList.push(result);
        });
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

    handleEditEmail(id: any) {
        const token = localStorage.getItem('token');
        if (token != null) {
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

    handleEditIdentification(id: any) {
        const token = localStorage.getItem('token');
        if (token != null) {
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

    //////////////////////////////////////////////Handle ADDRESS/////////////////////////////////////////////////////////////////

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

        return addressObject;
    }
}