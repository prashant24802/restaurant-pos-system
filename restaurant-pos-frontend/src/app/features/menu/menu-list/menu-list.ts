import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Menu } from '../services/menu';
import { MenuItem } from '../models/menu-item';

@Component({
  selector: 'app-menu-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu-list.html',
  styleUrl: './menu-list.css'
})
export class MenuList implements OnInit {

  private menuService = inject(Menu);

  menuItems: MenuItem[] = [];

  ngOnInit(): void {

    this.loadMenu();

  }

  loadMenu() {

    this.menuService.getAll().subscribe({

      next: (data) => {

        this.menuItems = data;

        console.log(data);

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

}