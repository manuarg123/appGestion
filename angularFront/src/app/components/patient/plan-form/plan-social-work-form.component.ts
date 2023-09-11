import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiService } from 'src/app/service/api.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ValidationsService } from 'src/app/service/validations.service';
import { Plan } from './plan.model';

@Component({
  selector: 'app-plan-social-work-form',
  templateUrl: './plan-social-work-form.component.html',
  styleUrls: ['./plan-form.component.css']
})
export class PlanSocialWorkFormComponent implements OnInit {
  selectedSocialWorkId: number = 0;
  selectedPlanId: number = 0;
  socialWorks: any[] = [];
  socialWorksIds: any[] = [];
  socialWork: string = '';
  plans: any[] = [];
  plansIds: any[] = [];
  plan: string = '';

  constructor(
    private dialogRef: MatDialogRef<PlanSocialWorkFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private validate: ValidationsService,
  ) {
    if (data) {
      if (data.id) {
        this.selectedPlanId = data.id;
        this.selectedSocialWorkId = data.socialWork.id;
      } else {
        this.selectedSocialWorkId = data.socialWork.id;
      }
    }
  }

  ngOnInit(): void {
    this.loadSocialWorks();
    this.loadPlans();
    this.loadPlansBySocialWork();
  }

  loadPlans(): Promise<void> {
    return new Promise((resolve, reject) => {
      const token = localStorage.getItem("token");
      if (token) {
        this.apiService.all('plans', token).subscribe(
          (response: any[]) => {
            response.forEach((plan: any) => {
              this.plansIds = response.map((plan: any) => plan.id);
            });
            this.plans = response;
            resolve(); // Resuelve la promesa cuando se hayan cargado los planes
          },
          (error) => {
            console.error(error);
            reject(); // Rechaza la promesa en caso de error
          }
        );
      }
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

  savePlan(): void {
    if (this.selectedSocialWorkId) {
      let selectedSocialWork = this.socialWorks.find(socialWork => socialWork.id == this.selectedSocialWorkId);
      const selectedSocialWorkName  = selectedSocialWork.fullName 
      
      if (this.selectedPlanId) {
        const selectedPlan = this.plans.find(plan => plan.id == this.selectedPlanId);
        const selectedPlanName = selectedPlan.name;

        const planData: Plan = {
          id: this.selectedPlanId,
          name: selectedPlanName,
          socialWork: {
            id: this.selectedSocialWorkId,
            name: selectedSocialWorkName
          },
          code:"Plan" + selectedPlanName + selectedSocialWorkName + this.selectedPlanId + this.selectedSocialWorkId
        }

        this.dialogRef.close(planData);
      } else {
        const planData: Plan = {
          id: null,
          name: "",
          socialWork: {
            id: this.selectedSocialWorkId,
            name: selectedSocialWorkName
          },
          code:"NotPlan" + selectedSocialWorkName + this.selectedSocialWorkId
        }
        this.dialogRef.close(planData);
      }
    }
  }

  loadPlansBySocialWork(): void {
    this.loadPlans().then(() => {
      const selectedSocialWorkId = parseInt(this.selectedSocialWorkId.toString());
      this.plans = this.plans.filter(plan => plan.socialWorkId === selectedSocialWorkId);
    });
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }
}