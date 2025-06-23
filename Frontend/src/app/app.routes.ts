import { Routes } from '@angular/router';
import {authGuard} from './Auth/guards/auth.guard';
import {userProfileResolver} from './user/resolvers/user-profile.resolver';

export const routes: Routes = [
  {path : '', redirectTo: '/home', pathMatch: 'full'},
  {path : 'home', loadComponent: () => import('./homepage/homepage.component').then(m => m.HomepageComponent)},
  {path : 'register', loadComponent: () => import('./Auth/components/register/register.component').then(m => m.RegisterComponent)},
  {path : 'login', loadComponent: () => import('./Auth/components/login/login.component').then(m => m.LoginComponent)},
  {path : 'me', loadComponent: () => import('./user/components/me/me.component').then(m => m.MeComponent), canActivate: [authGuard]},
  {
    path : 'user/:username',
    loadComponent : () => import('./user/components/user-profile/user-profile.component').then(m => m.UserProfileComponent),
    resolve : { user: userProfileResolver }
  },
];
