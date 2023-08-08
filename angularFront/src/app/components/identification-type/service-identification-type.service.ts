import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceIdentificationTypeService {
  private identificationTypeAddedSource = new Subject<void>();

  identificationTypeAdded$ = this.identificationTypeAddedSource.asObservable();

  triggerIdentificationTypeAdded() {
    this.identificationTypeAddedSource.next();
  }
}
