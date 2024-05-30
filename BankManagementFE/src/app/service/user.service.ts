import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class UserService {
    url = 'http://localhost:8888/api/v1/user';

    constructor(private http: HttpClient, private authService: AuthService) {}

    token = sessionStorage.getItem('authToken');

    getAllUserActive(): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.get(this.url.concat('/ad/active'), {headers});
    }

    getUserById(id: any): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.get<User>(`${this.url}/${id}`, {headers});
    }

    addUserByAdmin(data: any): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.post<any>(this.url.concat('/ad'), data, {headers});
    }

    editUser(id: any, data: any): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.put<any>(`${this.url}/${id}`, data, {headers});
    }

    disableUser(id: any): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.delete(`${this.url}/${id}`, {headers});
    }

    registration(data: any): Observable<any>{
      return this.http.post<any>(`${this.url}`.concat('/registration'), data);
    }
}