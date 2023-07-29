import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-medical-center-list',
  templateUrl: './medical-center-list.component.html',
  styleUrls: ['./medical-center-list.component.css'],
})
export class MedicalCenterListComponent implements OnInit {
  medicalCenters: any[] = [];

  constructor(private apiService: ApiService) {}

  ngOnInit() {
    this.getMedicalCenters();
  }

  getMedicalCenters() {
    const token = localStorage.getItem('token');
    if (token != null) {
      this.apiService.all('medicalCenters', token).subscribe(
        (data) => {
          this.medicalCenters = data;
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
}
