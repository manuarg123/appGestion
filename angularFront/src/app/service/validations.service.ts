import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ValidationsService {

  constructor() { }

  /**
   * Verifica que sea numeros
   * @param value 
   * @returns 
   */
  isNumber(value: string): boolean {
    return  /^\d+$/.test(value);
  }
}
