import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, of} from 'rxjs';
import {User} from '../../user/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {
  private baseUrl = 'http://localhost:8080/graphql';


  constructor(
    private http: HttpClient
  ) {}

  getConversations() {
    return this.http.post<any>(
      this.baseUrl,
      {
        query: `
          query {
            userConversations{
              id
              participants {
                id
              }
              messages{
                id
                content
                sender{
                  id
                }
                createdAt
                updatedAt
              }
            }
          }
        `
      },
      { responseType: 'json' }
    ).pipe(
      map(response => response.data.userConversations),
      catchError(err => {
        console.error('Erreur API capturée dans le service', err);
        return of([] as User[]);
      })
    );
  }
}
