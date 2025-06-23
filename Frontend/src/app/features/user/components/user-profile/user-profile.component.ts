import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../models/user.model';
import {JsonPipe, NgIf} from '@angular/common';

@Component({
  selector: 'app-user-profile',
  imports: [
    NgIf,
    JsonPipe
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {
  $route= inject(ActivatedRoute);
  user: User | null = null;

  ngOnInit(): void {
    this.$route.data.subscribe(data => {
      this.user = data['user'];
    });
  }
}
