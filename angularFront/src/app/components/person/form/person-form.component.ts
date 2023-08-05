import { PhoneFormComponent } from "../../phone/phone-form/phone-form.component";
import { EmailFormComponent } from "../../email/email-form/email-form.component";
import { IdentificationFormComponent } from "../../identification/identification-form/identification-form.component";
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { Phone } from '../../phone/phone.model';
import { Email } from "../../email/email.model";
import { Identification } from "../../identification/identification.model";
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';

export class PersonFormComponent {
    phoneList: Phone[] = [];
    emailList: Email[] = [];
    identificationList: Identification[] = [];

    constructor(
        private apiService: ApiService,
        private dialog: MatDialog,
    ) {

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
}