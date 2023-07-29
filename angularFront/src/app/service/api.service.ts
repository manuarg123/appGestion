import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private urlAPI = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) {}

  public all(endpoint: string, token: string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    
    return this.http.get<any>(this.urlAPI + endpoint, { headers });
  }
}