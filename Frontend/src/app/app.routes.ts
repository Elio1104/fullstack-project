import { Routes } from '@angular/router';

export const routes: Routes = [
  {path : '', redirectTo: '/home', pathMatch: 'full'},
  {path : 'home', loadComponent: () => import('./homepage/homepage.component').then(m => m.HomepageComponent)},
  {path : 'register', loadComponent: () => import('./register/register.component').then(m => m.RegisterComponent)},
  {path : 'login', loadComponent: () => import('./login/login.component').then(m => m.LoginComponent)},
];
