import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServiceProvinceService {
  private provinceAddedSource = new Subject<void>();

  provinceAdded$ = this.provinceAddedSource.asObservable();

  triggerProvinceAdded() {
    this.provinceAddedSource.next();
  }
}
