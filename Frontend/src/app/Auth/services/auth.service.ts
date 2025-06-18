import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, tap} from 'rxjs';
import {LoginForm} from '../models/login-form';
import {AuthStore} from '../stores/auth.store';
import {Router} from '@angular/router';

interface AuthResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,
    private authStore: AuthStore,
    private router: Router
  ) {}

  login(loginForm: LoginForm): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.baseUrl}/login`,
      loginForm
    ).pipe(
      tap(response => {
        this.authStore.setToken(response.token);
      }),
      catchError(err => {
        console.error('Erreur API capturée dans le service', err);
        return of(null as any);
      })
    );
  }

  logout(): void {
    this.authStore.clearToken();
    this.router.navigate(['/login']);
  }
}
