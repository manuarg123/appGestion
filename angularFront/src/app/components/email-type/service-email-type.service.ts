import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceEmailTypeService {
  private emailTypeAddedSource = new Subject<void>();

  emailTypeAdded$ = this.emailTypeAddedSource.asObservable();

  triggerEmailTypeAdded() {
    this.emailTypeAddedSource.next();
  }
}
