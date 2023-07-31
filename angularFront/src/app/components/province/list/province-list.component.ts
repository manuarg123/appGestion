import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';
import { ServiceProvinceService } from '../service-province.service';
import { ProvinceFormComponent } from '../form/province-form.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-province-list',
  templateUrl: './province-list.component.html',
  styleUrls: ['./province-list.component.css'],
})
export class ProvinceListComponent implements OnInit {
  provinces: any[] = [];
  currentPage: number = 1;
  pageSize: number = 20;
  totalItems: number = 0;

  constructor(
    private apiService: ApiService,
    private provinceDataService: ServiceProvinceService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.subscribeToProvinceAdded();
    this.getProvinces();
  }

  getProvinces() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService
        .paginated('provinces', token, this.currentPage, this.pageSize)
        .subscribe(
          (data) => {
            this.provinces = data.content;
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

  handleDelete(id: string): void {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService.delete('provinces', token, id).subscribe(
        (data) => {
          confirm('Desea eliminar el registro');
          this.getProvinces();
        },
        (error) => {
          console.log('Error al elminar', error);
        }
      );
    } else {
      console.log('No se encontro token');
    }
  }

  goToPage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.getProvinces();
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.totalItems / this.pageSize)) {
      this.currentPage++;
      this.getProvinces();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getProvinces();
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

  private subscribeToProvinceAdded() {
    this.provinceDataService.provinceAdded$.subscribe(() => {
      this.getProvinces();
    });
  }

  handleEdit(id: string): void {
    const token = localStorage.getItem('token');
    let data = {};
    if (token != null) {
      this.apiService.getByid('provinces', token, id).subscribe(
        (provinceData) => {
          const dialogRef = this.dialog.open(ProvinceFormComponent, {
            data: {...provinceData, id}
          });
        }
      );
      //this.apiService.put('provinces', token, data, id);
    }
  }
}
