import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServicePayoutConceptService {
  private payoutConceptAddedSource = new Subject<void>();

  payoutConceptAdded$ = this.payoutConceptAddedSource.asObservable();

  triggerPayoutConceptAdded() {
    this.payoutConceptAddedSource.next();
  }
}
