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

  /**
   * Verifica que sea un email correcto
   */
  isEmail(value: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(value);
  }
}
