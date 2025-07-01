import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {NgClass, NgIf} from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {LoginForm} from '../../models/login.form';
import {AuthStore} from '../../stores/auth.store';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    NgClass,
    NgIf,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm!: FormGroup;
  submitted = false;
  showPassword = false;
  errorMessage = '';
  loading = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private authStore: AuthStore
  ) {}

  ngOnInit(): void {
    if (this.authStore.isAuthenticated()) {
      this.router.navigate(['/home']);
    }

    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  get f() { return this.loginForm.controls; }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(): void {
    this.submitted = true;
    this.errorMessage = '';

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    const loginForm : LoginForm = this.loginForm.value;

    this.authService.login(loginForm).subscribe({
      next: (response) => {
        console.log('Connexion réussie:', response);
        this.loading = false;

        this.router.navigate(['/me']);
      },
      error: (error) => {
        console.error('Erreur de connexion:', error);
        this.errorMessage = 'Échec de la connexion. Veuillez vérifier vos identifiants.';
        this.loading = false;
      }
    });
  }
}
