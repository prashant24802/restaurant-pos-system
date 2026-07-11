import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MenuFormDialog } from '../menu-form-dialog/menu-form-dialog';


import { Menu } from '../services/menu';
import { MenuItem } from '../models/menu-item';

@Component({
  selector: 'app-menu-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatDialogModule
  ],
  templateUrl: './menu-list.html',
  styleUrl: './menu-list.css'
})
export class MenuList implements OnInit {

  private menuService = inject(Menu);
  private dialog = inject(MatDialog);

  menuItems: MenuItem[] = [];

  displayedColumns: string[] = [
    'id',
    'name',
    'category',
    'price',
    'actions'
  ];

  search = '';

  ngOnInit(): void {
    this.loadMenu();
  }

  loadMenu(): void {

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

  openAddDialog(): void {
    const dialogRef = this.dialog.open(MenuFormDialog,{
    width: '500px'
    });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.loadMenu();
    }

   });
  }

  editItem(item: MenuItem): void {
    const dialogRef = this.dialog.open(MenuFormDialog, {
      width: '500px',
      data: item

    });

     dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadMenu();

      }

    });

  }

}