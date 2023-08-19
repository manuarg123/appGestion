import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceProfessionalService {
  private professionalAddedSource = new Subject<void>();

  professionalAdded$ = this.professionalAddedSource.asObservable();

  triggerProfessionalAdded() {
    this.professionalAddedSource.next();
  }
}