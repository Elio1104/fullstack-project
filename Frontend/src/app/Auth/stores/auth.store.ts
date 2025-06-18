import { Injectable, computed, signal } from '@angular/core';


export interface AuthState {
  token : string | null;
}

@Injectable({
  providedIn: 'root'
})
export class AuthStore {
  private _authState = signal<AuthState>({
    token: localStorage.getItem('auth_token')
  });

  readonly token = computed(() => this._authState().token);
  readonly isAuthenticated = computed(() => !!this._authState().token);

  constructor() {
    this.initialize();
  }

  private initialize() {
    const token = localStorage.getItem('auth_token');
    if (token) {
      this._authState.update(state => ({ ...state, token }));
    }
  }

  setToken(token: string) {
    localStorage.setItem('auth_token', token);
    this._authState.update(state => ({ ...state, token }));
  }

  clearToken() {
    localStorage.removeItem('auth_token');
    this._authState.update(state => ({ ...state, token: null }));
  }
}
