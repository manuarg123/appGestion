import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';
import { ServicePayoutStatusService } from '../service-payout-status.service';
import { PayoutStatusFormComponent } from '../form/payout-status-form.component';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-payout-status-list',
  templateUrl: './payout-status-list.component.html',
  styleUrls: ['./payout-status-list.component.css'],
})
export class PayoutStatusListComponent implements OnInit {
  payoutStatus: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10;
  totalItems: number = 0;

  constructor(
    private apiService: ApiService,
    private payoutStatusDataService: ServicePayoutStatusService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.subscribeToPayoutStatusAdded();
    this.getPayoutStatus();
  }

  getPayoutStatus() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService
        .paginated('payoutStatuses', token, this.currentPage, this.pageSize)
        .subscribe(
          (data) => {
            this.payoutStatus = data.content;
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
      this.apiService.getByid('payoutStatuses', token, id).subscribe(
        (payoutStatusData) => {
          const dialogRef = this.dialog.open(PayoutStatusFormComponent, {
            data: { ...payoutStatusData, id },
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
          this.apiService.delete('payoutStatuses', token, id).subscribe(
            (data) => {
              this.getPayoutStatus();
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

  private subscribeToPayoutStatusAdded() {
    this.payoutStatusDataService.payoutStatusAdded$.subscribe(() => {
      this.getPayoutStatus();
    });
  }

  goToPage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.getPayoutStatus();
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.totalItems / this.pageSize)) {
      this.currentPage++;
      this.getPayoutStatus();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getPayoutStatus();
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