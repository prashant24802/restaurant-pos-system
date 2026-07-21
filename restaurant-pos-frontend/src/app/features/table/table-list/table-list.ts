import {
  Component,
  OnDestroy,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

import {
  MatDialog,
  MatDialogModule
} from '@angular/material/dialog';

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

import {
  MatSelectModule
} from '@angular/material/select';

import {
  MatProgressSpinnerModule
} from '@angular/material/progress-spinner';

import {
  Subject,
  debounceTime,
  distinctUntilChanged,
  takeUntil
} from 'rxjs';

import {
  RestaurantTable,
  TableStatus
} from '../models/table';

import {
  TableQuery
} from '../models/table-query';

import {
  TableService
} from '../services/table';

import {
  TableFormDialog
} from '../table-form-dialog/table-form-dialog';

import {
  ConfirmationDialog
} from '../../../shared/confirmation-dialog/confirmation-dialog';

@Component({
  selector: 'app-table-list',

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
    MatSelectModule,

    MatProgressSpinnerModule
  ],

  templateUrl: './table-list.html',

  styleUrl: './table-list.css'
})
export class TableList
  implements OnInit, OnDestroy {

  private tableService =
    inject(TableService);

  private dialog =
    inject(MatDialog);

  private snackBar =
    inject(MatSnackBar);

  private searchSubject =
    new Subject<string>();

  private destroySubject =
    new Subject<void>();

  tables: RestaurantTable[] = [];

  displayedColumns: string[] = [
    'id',
    'tableNumber',
    'capacity',
    'status',
    'actions'
  ];

  statuses: {
    value: TableStatus;
    label: string;
  }[] = [

    {
      value: 'AVAILABLE',
      label: 'Available'
    },

    {
      value: 'OCCUPIED',
      label: 'Occupied'
    },

    {
      value: 'RESERVED',
      label: 'Reserved'
    },

    {
      value: 'OUT_OF_SERVICE',
      label: 'Out of Service'
    }

  ];

  query: TableQuery = {

    page: 0,

    size: 5,

    search: '',

    status: null,

    sort: 'tableNumber',

    direction: 'asc'

  };

  totalElements = 0;

  loading = false;

  ngOnInit(): void {

    this.setupSearch();

    this.loadData();

  }

  ngOnDestroy(): void {

    this.destroySubject.next();

    this.destroySubject.complete();

  }

  private setupSearch(): void {

    this.searchSubject
      .pipe(

        debounceTime(300),

        distinctUntilChanged(),

        takeUntil(
          this.destroySubject
        )

      )
      .subscribe(() => {

        this.query.page = 0;

        this.loadData();

      });

  }

  loadData(): void {

    this.loading = true;

    this.tableService
      .getPage(this.query)
      .subscribe({

        next: (page) => {

          this.tables =
            page.content;

          this.totalElements =
            page.totalElements;

          this.loading = false;

        },

        error: (err) => {

          console.error(err);

          this.loading = false;

          this.tables = [];

          this.totalElements = 0;

          this.snackBar.open(

            'Failed to load tables',

            'Close',

            {
              duration: 3000
            }

          );

        }

      });

  }

  onSearch(): void {

    this.searchSubject.next(
      this.query.search
    );

  }

  onStatusChange(): void {

    this.query.page = 0;

    this.loadData();

  }

  clearFilters(): void {

    this.query.search = '';

    this.query.status = null;

    this.query.page = 0;

    this.loadData();

  }

  onPageChange(
    event: PageEvent
  ): void {

    this.query.page =
      event.pageIndex;

    this.query.size =
      event.pageSize;

    this.loadData();

  }

  sortData(
    sort: Sort
  ): void {

    if (!sort.active) {

      return;

    }

    this.query.sort =
      sort.active;

    this.query.direction = (sort.direction || 'asc') as 'asc' | 'desc';

    this.query.page = 0;

    this.loadData();

  }

  openAddDialog(): void {

    const dialogRef =
      this.dialog.open(
        TableFormDialog,
        {
          width: '500px'
        }
      );

    dialogRef
      .afterClosed()
      .subscribe(result => {

        if (result) {

          this.loadData();

        }

      });

  }

  editTable(
    table: RestaurantTable
  ): void {

    const dialogRef =
      this.dialog.open(
        TableFormDialog,
        {

          width: '500px',

          data: table

        }
      );

    dialogRef
      .afterClosed()
      .subscribe(result => {

        if (result) {

          this.loadData();

        }

      });

  }

  deleteTable(
    table: RestaurantTable
  ): void {

    const dialogRef =
      this.dialog.open(
        ConfirmationDialog,
        {

          width: '400px',

          data: {

            title:
              'Delete Table',

            message:
              `Are you sure you want to delete Table ${table.tableNumber}?`

          }

        }
      );

    dialogRef
      .afterClosed()
      .subscribe(result => {

        if (!result) {

          return;

        }

        this.tableService
          .delete(table.id)
          .subscribe({

            next: () => {

              this.snackBar.open(

                'Table deleted successfully',

                'Close',

                {
                  duration: 3000,

                  horizontalPosition:
                    'right',

                  verticalPosition:
                    'top'
                }

              );

              /*
               * If we deleted the only
               * record on a page other
               * than page zero, move
               * back one page.
               */
              if (
                this.tables.length === 1 &&
                this.query.page > 0
              ) {

                this.query.page--;

              }

              this.loadData();

            },

            error: (err) => {

              console.error(err);

              this.snackBar.open(

                'Failed to delete table',

                'Close',

                {
                  duration: 3000
                }

              );

            }

          });

      });

  }

  getStatusLabel(
    status: TableStatus
  ): string {

    switch (status) {

      case 'AVAILABLE':

        return 'Available';

      case 'OCCUPIED':

        return 'Occupied';

      case 'RESERVED':

        return 'Reserved';

      case 'OUT_OF_SERVICE':

        return 'Out of Service';

      default:

        return status;

    }

  }

}