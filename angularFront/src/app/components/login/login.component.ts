import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: LoginForm = new LoginForm();

  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  onSubmit() {
    const urlAPI = 'http://localhost:8080/login';
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    const requestBody = {
      username: this.loginForm.username,
      password: this.loginForm.password,
    };

    this.http.post(urlAPI, requestBody, { headers }).subscribe(
      (response: any) => {
        if (response.data == 'Login Failed') {
          if (this.loginForm.username == '' && this.loginForm.password == '') {
            alert('Ingrese datos para iniciar sesión');
          } else if (this.loginForm.password == '') {
            alert('Ingrese una contraseña!');
          } else if (this.loginForm.username == '') {
            alert('Ingrese un usuario!');
          } else {
            alert('Contraseña Incorrecta!');
          }
        } else {
          const token = response.data.accessToken;
          const expiresIn = 3600;
          const expirationDate = new Date().getTime() + expiresIn * 1000;
          localStorage.setItem('token', token);
          localStorage.setItem('expirationDate', expirationDate.toString());
          window.location.href = '/';
        }
      },
      (error) => {
        console.error('Error en la solicitud:', error);
      }
    );
  }

  onCreateUser() {
    console.log(localStorage.getItem('token'));
    console.log(localStorage.getItem('expirationDate'));
  }
}
class LoginForm {
  username: string = '';
  password: string = '';
}
