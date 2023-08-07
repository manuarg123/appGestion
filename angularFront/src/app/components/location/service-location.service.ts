import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceLocationService {
  private locationAddedSource = new Subject<void>();

  locationAdded$ = this.locationAddedSource.asObservable();

  triggerLocationAdded() {
    this.locationAddedSource.next();
  }
}
