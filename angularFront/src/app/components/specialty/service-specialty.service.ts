import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceSpecialtyService {
  private specialtyAddedSource = new Subject<void>();

  specialtyAdded$ = this.specialtyAddedSource.asObservable();

  triggerSpecialtyAdded() {
    this.specialtyAddedSource.next();
  }
}
