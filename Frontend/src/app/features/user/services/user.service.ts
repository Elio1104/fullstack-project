import { Injectable } from '@angular/core';
import {catchError, map, Observable, of} from 'rxjs';
import {User} from '../models/user.model';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/graphql';


  constructor(
    private http: HttpClient
  ) {}

  getUsers(): Observable<User[]> {
    return this.http.post<any>(
      this.baseUrl,
      {
        query: `
          query {
            users {
              id
              username
              email
              firstName
              lastName
              role
              friends {
                id
              }
            }
          }
        `
      },
      { responseType: 'json' }
    ).pipe(
      map(response => response.data.users),
      catchError(err => {
        console.error('Erreur API capturée dans le service', err);
        return of([] as User[]);
      })
    );
  }

  getUserByUsername(username: string): Observable<User | null> {
    return this.http.post<any>(
      this.baseUrl,
      {
        query: `
        query($username: String!) {
          userByUsername(username: $username) {
            id
            username
            email
            firstName
            lastName
            role
            friends {
              id
              username
              firstName
              lastName
            }
          }
        }
      `,
        variables: {
          username: username
        }
      },
      { responseType: 'json' }
    ).pipe(
      map(response => response.data?.userByUsername || null),
      catchError(err => {
        console.error('Error fetching user by username:', err);
        return of(null);
      })
    );
  }
}
