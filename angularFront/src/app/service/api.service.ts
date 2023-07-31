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

  public paginated(
    endpoint: string,
    token: string,
    currentPage: number,
    pageSize: number
  ): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<any>(
      this.urlAPI +
        endpoint +
        '/paginated?currentPage=' +
        currentPage +
        '&pageSize=' +
        pageSize,
      { headers }
    );
  }

  public post(endpoint: string, token: string, data: object) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.post<any>(this.urlAPI + endpoint + '/new', data, {
      headers,
    });
  }

  public put(endpoint: string, token: string, data: object, id: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put<any>(this.urlAPI + endpoint + '/edit/' + id, data, {
      headers,
    });
  }

  public getByid(endpoint: string, token: string, id: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<any>(this.urlAPI + endpoint + '/show/' + id, { headers });
  }

  public delete(endpoint: string, token: string, id: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.delete<any>(this.urlAPI + endpoint + '/delete/' + id, {
      headers,
    });
  }
}
