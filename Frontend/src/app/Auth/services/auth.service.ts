import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of} from 'rxjs';
import {LoginForm} from '../models/login-form';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(loginForm: LoginForm): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/login`,
      loginForm
    ).pipe(
      catchError(err => {
        console.error('Erreur API capturée dans le service', err);
        return of(null);
      })
    );
  }
}
