import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard {
  constructor(private readonly router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    const expirationDate = localStorage.getItem('expirationDate');

    if (token && expirationDate) {
      const currentTime = new Date().getTime();
      const expirationTime = parseInt(expirationDate, 10);
      if (currentTime < expirationTime) {
        return true; // El token es válido, permitir el acceso a la página
      }
    }

    // El token ha expirado o no se encontró el token o la fecha de expiración en el localStorage
    // Redirigir al usuario a la página de login
    this.router.navigate(['/login']);
    return false;
  }
}

export const canActivateRoute: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  return inject(AuthGuard).canActivate();
};
 // const token = localStorage.getItem('token');
  // const expirationDate = localStorage.getItem('expirationDate');

  // if (token && expirationDate) {
  //   const currentTime = new Date().getTime();
  //   const expirationTime = parseInt(expirationDate, 10);
  //   if (currentTime < expirationTime) {
  //     return true; // El token es válido, permitir el acceso a la página
  //   }
  // }

  // // El token ha expirado o no se encontró el token o la fecha de expiración en el localStorage
  // // Redirigir al usuario a la página de login
  // window.location.href = "/login";
  // return false;