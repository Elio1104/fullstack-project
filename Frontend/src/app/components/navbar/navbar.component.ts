import {Component, inject, OnInit} from '@angular/core';
import {AuthStore} from '../../features/auth/stores/auth.store';
import {Router, RouterLink} from '@angular/router';
import {UserService} from '../../features/user/services/user.service';
import {User} from '../../features/user/models/user.model';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  $auth : AuthStore = inject(AuthStore);
  $router : Router = inject(Router);
  $userService = inject(UserService);

  users: User[] = [];
  searchTerm : string  = '';
  filteredUsers : User[] = [];
  showResults : boolean = false;

  ngOnInit(): void {
    this.$userService.getUsers().subscribe(users => {
      this.users = users;
      this.filteredUsers = users;
    });
  }

  logout (): void {
    this.$auth.clearToken();
    this.$router.navigate(['/login']);
  }

  filterUsers(): void {
    const term = this.searchTerm.trim().toLowerCase();

    if (term === '') {
      this.filteredUsers = [];
      this.showResults = false;
      return;
    }

    this.filteredUsers = this.users.filter(user =>
      user.username.toLowerCase().includes(term) ||
      user.firstName.toLowerCase().includes(term) ||
      user.lastName.toLowerCase().includes(term)
    );

    this.showResults = true;
  }

  hideResults(): void {
    setTimeout(() => {
      this.showResults = false;
    }, 200);
  }

}
