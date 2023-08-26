import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServicePatientService {
  private patientAddedSource = new Subject<void>();

  patientAdded$ = this.patientAddedSource.asObservable();

  triggerPatientAdded() {
    this.patientAddedSource.next();
  }
}