import { ResolveFn } from '@angular/router';
import {User} from '../models/user.model';
import {Observable} from 'rxjs';
import {inject} from '@angular/core';
import {UserService} from '../services/user.service';

export const userProfileResolver: ResolveFn<User | null> = (route, state) : Observable<User | null> => {
  const $userService = inject(UserService);
  const username = route.paramMap.get('username');

  if (!username) {
    return new Observable<null>(subscriber => {
      subscriber.next(null);
      subscriber.complete();
    });
  }

  return $userService.getUserByUsername(username);
};
