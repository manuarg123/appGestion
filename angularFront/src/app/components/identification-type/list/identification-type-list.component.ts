import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';
import { ServiceIdentificationTypeService } from '../service-identification-type.service';
import { IdentificationTypeFormComponent } from '../form/identification-type-form.component';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-identification-type-list',
  templateUrl: './identification-type-list.component.html',
  styleUrls: ['./identification-type-list.component.css'],
})
export class IdentificationTypeListComponent implements OnInit {
  identificationTypes: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10;
  totalItems: number = 0;

  constructor(
    private apiService: ApiService,
    private identificationTypeDataService: ServiceIdentificationTypeService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    this.subscribeToIdentificationTypeAdded();
    this.getIdentificationTypes();
  }

  getIdentificationTypes() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService
        .paginated('identificationTypes', token, this.currentPage, this.pageSize)
        .subscribe(
          (data) => {
            this.identificationTypes = data.content;
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
      this.apiService.getByid('identificationTypes', token, id).subscribe(
        (identificationTypeData) => {
          const dialogRef = this.dialog.open(IdentificationTypeFormComponent, {
            data: { ...identificationTypeData, id },
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
          this.apiService.delete('identificationTypes', token, id).subscribe(
            (data) => {
              this.getIdentificationTypes();
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

  private subscribeToIdentificationTypeAdded() {
    this.identificationTypeDataService.identificationTypeAdded$.subscribe(() => {
      this.getIdentificationTypes();
    });
  }

  goToPage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.getIdentificationTypes();
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.totalItems / this.pageSize)) {
      this.currentPage++;
      this.getIdentificationTypes();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getIdentificationTypes();
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