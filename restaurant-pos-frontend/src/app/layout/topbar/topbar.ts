import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-topbar',
  standalone: true,
  templateUrl: './topbar.html',
  styleUrl: './topbar.css'
})
export class Topbar {

  private router = inject(Router);

  logout() {

    localStorage.removeItem('token');

    this.router.navigate(['/']);

  }

}