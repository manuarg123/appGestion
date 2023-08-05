import { PhoneFormComponent } from "../../phone/phone-form/phone-form.component";
import { MatDialog } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { Phone } from '../../phone/phone.model';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';

export class PersonFormComponent {
    phoneList: Phone[] = [];

    constructor(
        private apiService: ApiService,
        private dialog: MatDialog,
    ) {

    }

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
}