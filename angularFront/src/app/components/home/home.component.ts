import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/service/api.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  data: any = {};

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    const expirationDate = localStorage.getItem('expirationDate');
    if (token && expirationDate) {
      const currentTime = new Date().getTime();
      const expirationTime = parseInt(expirationDate, 10);
      if (currentTime < expirationTime) {
        this.llenar();
      } else {
        window.location.href = '/login';
      }
    } else {
      window.location.href = '/login';
    }
  }

  llenar() {
    this.apiService.getData().subscribe((data) => {
      this.data = data;
      console.log(this.data);
    });
  }
}
