import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServicePayoutStatusService {
  private payoutStatusAddedSource = new Subject<void>();

  payoutStatusAdded$ = this.payoutStatusAddedSource.asObservable();

  triggerPayoutStatusAdded() {
    this.payoutStatusAddedSource.next();
  }
}
