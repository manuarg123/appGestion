import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceSocialWorkService {
  private socialWorkAddedSource = new Subject<void>();

  socialWorkAdded$ = this.socialWorkAddedSource.asObservable();

  triggerSocialWorkAdded() {
    this.socialWorkAddedSource.next();
  }
}
