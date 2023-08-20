import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

/**
 * Manejo de solicitudes HTTP con la API
 */
export class ApiService {
  private urlAPI = 'http://localhost:8080/api/';

  constructor(private http: HttpClient) { }

  /**
   * Obtiene todos los registros
   * @param endpoint 
   * @param token 
   * @returns 
   */
  public all(endpoint: string, token: string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<any>(this.urlAPI + endpoint, { headers });
  }

  /**
   * Paginado
   * @param endpoint 
   * @param token 
   * @param currentPage 
   * @param pageSize 
   * @returns 
   */
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

  /**
   * Nuevos Registros
   * @param endpoint 
   * @param token 
   * @param data 
   * @returns 
   */
  public post(endpoint: string, token: string, data: object) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.post<any>(this.urlAPI + endpoint + '/new', data, {
      headers,
    });
  }

  /**
   * Edición de registros
   * @param endpoint 
   * @param token 
   * @param data 
   * @param id 
   * @returns 
   */
  public put(endpoint: string, token: string, data: object, id: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put<any>(this.urlAPI + endpoint + '/edit/' + id, data, {
      headers,
    });
  }

  /**
   * Obtención por id
   * @param endpoint 
   * @param token 
   * @param id 
   * @returns 
   */
  public getByid(endpoint: string, token: string, id: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<any>(this.urlAPI + endpoint + '/show/' + id, { headers });
  }

  /**
   * Eliminación de registros
   * @param endpoint 
   * @param token 
   * @param id 
   * @returns 
   */
  public delete(endpoint: string, token: string, id: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.delete<any>(this.urlAPI + endpoint + '/delete/' + id, {
      headers,
    });
  }

  /**
   * Obtengo DTO de medicalCenter
   */
  public getMedicalCenterDTOById(endpoint: string, token:string, id:string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<any>(this.urlAPI + endpoint + '/minDto/' + id, {
      headers,
    });
  }
}
