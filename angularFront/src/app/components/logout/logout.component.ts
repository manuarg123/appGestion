import { Component } from '@angular/core';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {
  logout(){
    localStorage.setItem('token', "");
    localStorage.setItem('expirationDate', "");
    window.location.href = "/login";
  }
}
