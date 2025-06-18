import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {AuthStore} from '../stores/auth.store';
import {catchError, throwError} from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authStore : AuthStore = inject(AuthStore);
  const router : Router = inject(Router);
  const token = localStorage.getItem('auth_token');

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req).pipe(
    catchError(error => {
      if (error.status === 401) {
        authStore.clearToken();
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};
