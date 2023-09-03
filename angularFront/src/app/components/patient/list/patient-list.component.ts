import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../common/confirmation-dialog/confirmation-dialog.component';
import { PatientFormComponent } from '../form/patient-form.component';
import { ServicePatientService } from '../service-patient.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css'],
})
export class PatientListComponent implements OnInit {
  patients: any[] = [];
  currentPage: number = 1;
  pageSize: number = 20;
  totalItems: number = 0;

  constructor(private apiService: ApiService, private dialog: MatDialog, private patientDataService: ServicePatientService, private router: Router) { }

  ngOnInit() {
    this.getPatients();
    this.patientDataService.patientAdded$.subscribe(() => {
      this.getPatients();
    });
  }

  getPatients() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService
        .paginated('patients', token, this.currentPage, this.pageSize)
        .subscribe(
          (data) => {
            this.patients = data.content;
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
          this.apiService.delete('patients', token, id).subscribe(
            (data) => {
              this.getPatients();
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

  // handleEdit(id: string): void {
  //   const token = localStorage.getItem('token');
  //   if (token != null) {
  //     this.apiService.getByid('patients', token, id).subscribe(
  //       (patientData) => {
  //         const dialogRef = this.dialog.open(PatientFormComponent, {
  //           data: { ...patientData, id },
  //           width: '700px',
  //         });
  //       }
  //     );
  //   }
  // } 

  handleEdit(id: string): void {
    this.router.navigate(['patient', id]);
  }

  goToPage(pageNumber: number) {
    this.currentPage = pageNumber;
    this.getPatients();
  }

  nextPage() {
    if (this.currentPage < Math.ceil(this.totalItems / this.pageSize)) {
      this.currentPage++;
      this.getPatients();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getPatients();
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
