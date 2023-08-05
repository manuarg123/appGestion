import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';
import { MedicalCenterFormComponent } from '../form/medical-center-form.component';
import { ServiceMedicalCenterService } from '../service-medical-center.service';

@Component({
  selector: 'app-medical-center-list',
  templateUrl: './medical-center-list.component.html',
  styleUrls: ['./medical-center-list.component.css'],
})
export class MedicalCenterListComponent implements OnInit {
  medicalCenters: any[] = [];
  currentPage: number = 1;
  pageSize: number = 20;
  totalItems: number = 0;

  constructor(private apiService: ApiService, private dialog: MatDialog, private medicalCenterDataService: ServiceMedicalCenterService) { }

  ngOnInit() {
    this.getMedicalCenters();
    this.medicalCenterDataService.medicalCenterAdded$.subscribe(() => {
      this.getMedicalCenters();
    });
  }

  getMedicalCenters() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService
        .paginated('medicalCenters', token, this.currentPage, this.pageSize)
        .subscribe(
          (data) => {
            this.medicalCenters = data.content;
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
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          this.apiService.delete('medicalCenters', token, id).subscribe(
            (data) => {
              this.getMedicalCenters();
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

  handleEdit(id: string): void {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService.getByid('medicalCenters', token, id).subscribe(
        (medicalCenterData) => {
          const dialogRef = this.dialog.open(MedicalCenterFormComponent, {
            data: { ...medicalCenterData, id },
            width: '700px',
          });
        }
      );
    }
  }

  goToPage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.getMedicalCenters();
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.totalItems / this.pageSize)) {
      this.currentPage++;
      this.getMedicalCenters();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getMedicalCenters();
    }
  }
  getTotalPages() {
    return Math.ceil(this.totalItems / this.pageSize);
  }

  getPagesArray() {
    const totalPages = this.getTotalPages();
    const pagesArray = [];

    const maxPagesToShow = 5;
    const halfMaxPagesToShow = Math.floor(maxPagesToShow / 2);

    let startPage = Math.max(1, this.currentPage - halfMaxPagesToShow);
    const endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

    if (endPage - startPage + 1 < maxPagesToShow) {
      startPage = Math.max(1, endPage - maxPagesToShow + 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      pagesArray.push(i);
    }

    return pagesArray;
  }

  getLoopArray() {
    const loopArray = [];
    const maxPageSize = this.pageSize;

    for (let i = 0; i < maxPageSize; i++) {
      loopArray.push(i);
    }

    return loopArray;
  }
}
