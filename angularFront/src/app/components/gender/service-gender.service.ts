import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceGenderService {
  private genderAddedSource = new Subject<void>();

  genderAdded$ = this.genderAddedSource.asObservable();

  triggerGenderAdded() {
    this.genderAddedSource.next();
  }
}