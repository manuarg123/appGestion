import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServicePlanService {
  private planAddedSource = new Subject<void>();

  planAdded$ = this.planAddedSource.asObservable();

  triggerPlanAdded() {
    this.planAddedSource.next();
  }
}
