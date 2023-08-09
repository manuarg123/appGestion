import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServicePaymentTypeService {
  private paymentTypeAddedSource = new Subject<void>();

  paymentTypeAdded$ = this.paymentTypeAddedSource.asObservable();

  triggerPaymentTypeAdded() {
    this.paymentTypeAddedSource.next();
  }
}
