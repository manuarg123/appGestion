import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServicePhoneTypeService {
  private phoneTypeAddedSource = new Subject<void>();

  phoneTypeAdded$ = this.phoneTypeAddedSource.asObservable();

  triggerPhoneTypeAdded() {
    this.phoneTypeAddedSource.next();
  }
}
