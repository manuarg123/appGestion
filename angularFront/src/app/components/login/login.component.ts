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
        console.log(response.data.accessToken);
        const token = response.data.accessToken;
        localStorage.setItem('token', token);
        window.location.href = '/';
      },
      (error) => {
        console.error('Error en la solicitud:', error);
      }
    );
  }

  onCreateUser() {
    console.log(localStorage.getItem('token'));
  }
}
class LoginForm {
  username: string = '';
  password: string = '';
}
