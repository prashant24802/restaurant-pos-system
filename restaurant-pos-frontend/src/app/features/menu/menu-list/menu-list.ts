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
import {
  MatSortModule,
  Sort
} from '@angular/material/sort';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';

import {
  Subject,
  debounceTime,
  distinctUntilChanged
} from 'rxjs';

import { MenuFormDialog } from '../menu-form-dialog/menu-form-dialog';
import { ConfirmationDialog } from '../../../shared/confirmation-dialog/confirmation-dialog';

import { Menu } from '../services/menu';
import { CategoryService } from '../services/category';

import { MenuItem } from '../models/menu-item';
import { MenuQuery } from '../models/menu-query';
import { Category } from '../models/category';

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
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatSelectModule
  ],
  templateUrl: './menu-list.html',
  styleUrl: './menu-list.css'
})
export class MenuList implements OnInit {

  private menuService = inject(Menu);
  private categoryService = inject(CategoryService);
  private dialog = inject(MatDialog);
  private snackBar = inject(MatSnackBar);

  private searchSubject = new Subject<string>();

  menuItems: MenuItem[] = [];

  categories: Category[] = [];

  displayedColumns = [
    'id',
    'name',
    'category',
    'price',
    'actions'
  ];

  query: MenuQuery = {
    page: 0,
    size: 5,
    search: '',
    categoryId: null,
    available: null,
    sort: 'name',
    direction: 'asc'
  };

  totalElements = 0;

  loading = false;

  ngOnInit(): void {

    this.loadCategories();

    this.loadData();

    this.searchSubject
      .pipe(
        debounceTime(300),
        distinctUntilChanged()
      )
      .subscribe(() => {

        this.query.page = 0;

        this.loadData();

      });

  }

  loadCategories(): void {

    this.categoryService.getAll().subscribe({

      next: (categories) => {

        this.categories = categories;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  loadData(): void {

    this.loading = true;

    this.menuService.getPage(this.query).subscribe({

      next: (page) => {

        this.menuItems = page.content;

        this.totalElements = page.totalElements;

        this.loading = false;

      },

      error: (err) => {

        console.error(err);

        this.loading = false;

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

    onSearch(): void {

    this.searchSubject.next(this.query.search);

  }

  onCategoryChange(): void {

    this.query.page = 0;

    this.loadData();

  }

  onAvailabilityChange(): void {

    this.query.page = 0;

    this.loadData();

  }

  clearFilters(): void {

    this.query.search = '';

    this.query.categoryId = null;

    this.query.available = null;

    this.query.page = 0;

    this.loadData();

  }

  onPageChange(event: PageEvent): void {

    this.query.page = event.pageIndex;

    this.query.size = event.pageSize;

    this.loadData();

  }

  sortData(sort: Sort): void {

    if (!sort.active) {

      return;

    }

    this.query.sort = sort.active;

    this.query.direction =
      (sort.direction || 'asc') as 'asc' | 'desc';

    this.query.page = 0;

    this.loadData();

  }

  openAddDialog(): void {

    const dialogRef = this.dialog.open(MenuFormDialog, {

      width: '500px'

    });

    dialogRef.afterClosed().subscribe(result => {

      if (result) {

        this.loadData();

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

        this.loadData();

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

          this.loadData();

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