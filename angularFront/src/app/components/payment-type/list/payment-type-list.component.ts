import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';
import { ServicePaymentTypeService } from '../service-payment-type.service';
import { PaymentTypeFormComponent } from '../form/payment-type-form.component';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-payment-type-list',
  templateUrl: './payment-type-list.component.html',
  styleUrls: ['./payment-type-list.component.css'],
})
export class PaymentTypeListComponent implements OnInit {
  paymentTypes: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10;
  totalItems: number = 0;

  constructor(
    private apiService: ApiService,
    private paymentTypeDataService: ServicePaymentTypeService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.subscribeToPaymentTypeAdded();
    this.getPaymentTypes();
  }

  getPaymentTypes() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService
        .paginated('paymentTypes', token, this.currentPage, this.pageSize)
        .subscribe(
          (data) => {
            this.paymentTypes = data.content;
            this.totalItems = data.totalElements;
          },
          (error) => {
            console.log(
              'Error al obtener los datos de los centros médicos:',
              error
            );
          }
        );
    } else {
      console.log('No se encontro token');
    }
  }

  handleEdit(id: string): void {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService.getByid('paymentTypes', token, id).subscribe(
        (paymentTypeData) => {
          const dialogRef = this.dialog.open(PaymentTypeFormComponent, {
            data: { ...paymentTypeData, id },
            width: '250px',
          });
        }
      );
    }
  }

  handleDelete(id: string): void {
    const token = localStorage.getItem('token');
    if (token != null) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          this.apiService.delete('paymentTypes', token, id).subscribe(
            (data) => {
              this.getPaymentTypes();
            },
            (error) => {
              console.log('Error al eliminar', error);
            }
          );
        }
      });
    } else {
      console.log('No se encontró token');
    }
  }

  private subscribeToPaymentTypeAdded() {
    this.paymentTypeDataService.paymentTypeAdded$.subscribe(() => {
      this.getPaymentTypes();
    });
  }

  goToPage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.getPaymentTypes();
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.totalItems / this.pageSize)) {
      this.currentPage++;
      this.getPaymentTypes();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getPaymentTypes();
    }
  }

  getTotalPages() {
    return Math.ceil(this.totalItems / this.pageSize);
  }

  getPagesArray() {
    const totalPages = this.getTotalPages();
    const pagesArray = [];

    // Limitar el número de enlaces de página que se mostrarán en el paginador
    const maxPagesToShow = 5;
    const halfMaxPagesToShow = Math.floor(maxPagesToShow / 2);

    let startPage = Math.max(1, this.currentPage - halfMaxPagesToShow);
    const endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

    // Ajustar el rango de páginas para no exceder el número total de páginas
    if (endPage - startPage + 1 < maxPagesToShow) {
      startPage = Math.max(1, endPage - maxPagesToShow + 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      pagesArray.push(i);
    }

    return pagesArray;
  }

  //Establece limite de iteración en el array de la vista para que complete siempre los especios que quedan vacios y la tabla tiene el mismo tamaño siempre
  getLoopArray() {
    const loopArray = [];
    const maxPageSize = this.pageSize;

    for (let i = 0; i < maxPageSize; i++) {
      loopArray.push(i);
    }

    return loopArray;
  }
}