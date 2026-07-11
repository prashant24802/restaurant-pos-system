import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import {
  MatSnackBar,
  MatSnackBarModule
} from '@angular/material/snack-bar';
import {
  MatPaginatorModule,
  PageEvent
} from '@angular/material/paginator';

import { MenuFormDialog } from '../menu-form-dialog/menu-form-dialog';
import { ConfirmationDialog } from '../../../shared/confirmation-dialog/confirmation-dialog';

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
    MatDialogModule,
    MatSnackBarModule,
    MatPaginatorModule
  ],
  templateUrl: './menu-list.html',
  styleUrl: './menu-list.css'
})
export class MenuList implements OnInit {

  private menuService = inject(Menu);

  private dialog = inject(MatDialog);

  private snackBar = inject(MatSnackBar);

  menuItems: MenuItem[] = [];

  displayedColumns = [
    'id',
    'name',
    'category',
    'price',
    'actions'
  ];

  search = '';

  pageIndex = 0;

  pageSize = 5;

  totalElements = 0;

  ngOnInit(): void {

    this.loadMenu();

  }

  loadMenu(): void {

    this.menuService.getPage({

      page: this.pageIndex,

      size: this.pageSize,

      search: '',

      sort: 'name',

      direction: 'asc'

    }).subscribe({

      next: (page) => {

        this.menuItems = page.content;

        this.totalElements = page.totalElements;

      },

      error: (err) => {

        console.error(err);

        this.snackBar.open(

          'Failed to load menu',

          'Close',

          {

            duration: 3000

          }

        );

      }

    });

  }

  onPageChange(event: PageEvent): void {

    this.pageIndex = event.pageIndex;

    this.pageSize = event.pageSize;

    this.loadMenu();

  }

  openAddDialog(): void {

    const dialogRef = this.dialog.open(MenuFormDialog, {

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

  deleteItem(item: MenuItem): void {

    const dialogRef = this.dialog.open(ConfirmationDialog, {

      width: '400px',

      data: {

        title: 'Delete Menu Item',

        message: `Are you sure you want to delete "${item.name}"?`

      }

    });

    dialogRef.afterClosed().subscribe(result => {

      if (!result) {

        return;

      }

      this.menuService.delete(item.id).subscribe({

        next: () => {

          this.snackBar.open(

            'Menu item deleted successfully',

            'Close',

            {

              duration: 3000

            }

          );

          this.loadMenu();

        },

        error: (err) => {

          console.error(err);

          this.snackBar.open(

            'Failed to delete menu item',

            'Close',

            {

              duration: 3000

            }

          );

        }

      });

    });

  }

}