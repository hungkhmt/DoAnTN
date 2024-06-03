import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { User } from '../model/user';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class AccountService {
    url = 'http://localhost:8888/api/v1/account';

    constructor(private http: HttpClient, private authService: AuthService) {}

    getAllUserWithRole(): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.get(this.url.concat('/ad/role'));
    }

    getUserById(id: any): Observable<any>{
      const headers = this.authService.getAuthHeaders();
        return this.http.get<User>(`${this.url}/${id}`, {headers});
    }

    getAllAcount(): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.get(this.url.concat('/ad'), {headers});
    }

    getAllAccountByUserId(userId: any): Observable<any[]>{
      const headers = this.authService.getAuthHeaders();
      let params = new HttpParams().set('userId', userId.toString());
      return this.http.get<any[]>(`${this.url.concat('/accountInfo')}`, {headers, params})
        // .pipe(
        //   tap(response => {
        //     if(response) {
        //       sessionStorage.setItem('currentAccount', String(response[0].accountId));
        //     }
        //   })
        // );
    }

    getAllAccountByMonth(month: any): Observable<any> {
      const headers = this.authService.getAuthHeaders();
      let params = new HttpParams().set('month', month);
      return this.http.get(this.url.concat('/month'), {params, headers});
    }

    getUserByAccId(accId: any): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      return this.http.get<any>(`${this.url.concat('/user_infor')}/${accId}`, {headers})
    }

    getAccountByAccId(accId: any): Observable<any>{
      const headers = this.authService.getAuthHeaders();
      let params = new HttpParams().set('accountId', accId.toString());
      return this.http.get(this.url.concat('/fetch'), {params, headers});
    }
}