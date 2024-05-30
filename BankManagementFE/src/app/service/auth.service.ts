import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8888/api/v1/auth';
  private token: string | null = null;
  private isLoggin: boolean = true;
  static getRole: any;

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/login`, { username, password })
      .pipe(
        tap(response => {
          if (response && response.token) {
            this.token = response.token;
            sessionStorage.setItem('authToken', this.token!);
            sessionStorage.setItem('isLoggin', String(this.isLoggin));
          }
        })
      );
  }

  getToken(): string | null {
    if (!this.token) {
        this.token = sessionStorage.getItem('authToken');
    }
    return this.token;
  }

  logout(token: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/logout`, { token })
      .pipe(
        tap(response => {
          if (response) {
            this.token = null;
            sessionStorage.removeItem('authToken');
            sessionStorage.removeItem('isLoggin');
            sessionStorage.removeItem('currentAccount');
          }
        })
      );
  }

  refreshToken(token: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/refresh`, { token })
      .pipe(
        tap(response => {
          if (response && response.token) {
            this.token = response.token;
            sessionStorage.setItem('authToken', this.token!);
          }
        })
      );
  }

  getRole(): string | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken?.scope;
    }
    return null;
  }

  getUserId(): number | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      return decodedToken?.userId;
    }
    return null;
  }

  getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.getToken()}`
    });
  }
}
