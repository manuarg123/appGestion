import { Component, Input, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { ServicePlanService } from '../service-plan.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-plan-form',
  templateUrl: './plan-form.component.html',
  styleUrls: ['./plan-form.component.css'],
})
export class PlanFormComponent implements OnInit {
  @Input() show: boolean = false;
  name: string = '';
  id: string = '';
  socialWorks: any[] = [];
  socialWork: string = "";
  socialWorkId: string = "";
  socialWorksIds: any[] = [];

  constructor(
    private dialogRef: MatDialogRef<PlanFormComponent>,
    private apiService: ApiService,
    private planDataService: ServicePlanService,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (data) {
      this.name = data.data.name;
      this.socialWork = data.data.socialWork.id;
      this.id = data.id;
    } else {
      this.name = '';
      this.id = '';
      this.socialWork = "";
    }
  }

  ngOnInit(): void {
    this.loadSocialWorks();
  }

  handleSubmit(): void {
    const token = localStorage.getItem('token');
    let data = { name: '', socialWorkId: this.socialWork };
    let id = this.id;

    data.name = this.name;

    if (token != null) {
      if (id == "") {
        this.apiService.post('plans', token, data).subscribe(
          (response) => {
            this.planDataService.triggerPlanAdded();
            this.dialogRef.close();
          },
          (error) => {
            if (typeof error.error.error === 'string') {
              if (error.error.error == "Record with the same name already exists.") {
                this.openSnackBar('Ya existe un registro con ese nombre, ingrese otro diferente', 'Aceptar');
              }
            } else {
              if (error.error.error[0] == "Name cannot be blank") {
                this.openSnackBar('No puede dejar en blanco el nombre, ingrese un registro', 'Aceptar');
              }
            }
          }
        );
      } else {
        this.apiService.put('plans', token, data, id).subscribe(
          (response) => {
            this.planDataService.triggerPlanAdded();
            this.dialogRef.close();
          },
          (error) => {
            if (typeof error.error.error === 'string') {
              if (error.error.error == "Record with the same name already exists.") {
                this.openSnackBar('Ya existe un registro con ese nombre, ingrese otro diferente', 'Aceptar');
              }
            } else {
              if (error.error.error[0] == "Name cannot be blank") {
                this.openSnackBar('No puede dejar en blanco el nombre, ingrese un registro', 'Aceptar');
              }
            }
          }
        );
      }
    } else {
      console.log('No se encontró token');
    }
  }

  handleClose(): void {
    this.dialogRef.close();
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }

  loadSocialWorks(): void {
    const token = localStorage.getItem("token");
    if (token) {
      this.apiService.all('socialWorks', token).subscribe(
        (response: any[]) => {
          response.forEach((socialWork: any) => {
            this.socialWorksIds = response.map((socialWork: any) => socialWork.id);
          });
          this.socialWorks = response;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }
}
