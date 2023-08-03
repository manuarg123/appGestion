import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceMedicalCenterService {
  private medicalCenterAddedSource = new Subject<void>();

  medicalCenterAdded$ = this.medicalCenterAddedSource.asObservable();

  triggerMedicalCenterAdded() {
    this.medicalCenterAddedSource.next();
  }
}
