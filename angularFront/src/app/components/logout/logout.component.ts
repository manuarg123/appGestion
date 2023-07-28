import { Component } from '@angular/core';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {
  logout(){
    localStorage.setItem('token', "");
    window.location.href = "/login";
  }
}
