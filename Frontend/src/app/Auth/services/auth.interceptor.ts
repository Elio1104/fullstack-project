import { HttpInterceptorFn } from '@angular/common/http';
import {AuthService} from './auth.service';
import {inject} from '@angular/core';
import {Router} from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService : AuthService = inject(AuthService);
  const router : Router = inject(Router);
  const token = localStorage.getItem('auth_token');

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
