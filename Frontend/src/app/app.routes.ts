import { Routes } from '@angular/router';
import {authGuard} from './features/auth/guards/auth.guard';
import {userProfileResolver} from './features/user/resolvers/user-profile.resolver';

export const routes: Routes = [
  {path : '', redirectTo: '/home', pathMatch: 'full'},
  {path : 'home', loadComponent: () => import('./pages/homepage/homepage.component').then(m => m.HomepageComponent)},
  {path : 'register', loadComponent: () => import('./features/auth/components/register/register.component').then(m => m.RegisterComponent)},
  {path : 'login', loadComponent: () => import('./features/auth/components/login/login.component').then(m => m.LoginComponent)},
  {path : 'me', loadComponent: () => import('./features/user/components/me/me.component').then(m => m.MeComponent), canActivate: [authGuard]},
  {
    path : 'user/:username',
    loadComponent : () => import('./features/user/components/user-profile/user-profile.component').then(m => m.UserProfileComponent),
    resolve : { user: userProfileResolver }
  },
];
