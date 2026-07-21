import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef
} from '@angular/material/dialog';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import {
  MatSnackBar,
  MatSnackBarModule
} from '@angular/material/snack-bar';

import {
  RestaurantTable,
  TableRequest
} from '../models/table';

import { TableService } from '../services/table';

@Component({
  selector: 'app-table-form-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule
  ],
  templateUrl: './table-form-dialog.html',
  styleUrl: './table-form-dialog.css'
})
export class TableFormDialog implements OnInit {

  private fb = inject(FormBuilder);

  private tableService = inject(TableService);

  private dialogRef =
    inject(MatDialogRef<TableFormDialog>);

  private snackBar = inject(MatSnackBar);

  private data = inject<RestaurantTable | null>(
    MAT_DIALOG_DATA,
    { optional: true }
  );

  isEdit = false;

  saving = false;

  form = this.fb.group({

    tableNumber: [
      null as number | null,
      [
        Validators.required,
        Validators.min(1)
      ]
    ],

    capacity: [
      null as number | null,
      [
        Validators.required,
        Validators.min(1)
      ]
    ]

  });

  ngOnInit(): void {

    if (this.data) {

      this.isEdit = true;

      this.form.patchValue({

        tableNumber: this.data.tableNumber,

        capacity: this.data.capacity

      });

    }

  }

  save(): void {

    if (this.form.invalid) {

      this.form.markAllAsTouched();

      return;

    }

    if (this.saving) {

      return;

    }

    const request: TableRequest = {

      tableNumber: Number(
        this.form.value.tableNumber
      ),

      capacity: Number(
        this.form.value.capacity
      )

    };

    this.saving = true;

    if (this.isEdit && this.data) {

      this.tableService
        .update(this.data.id, request)
        .subscribe({

          next: () => {

            this.saving = false;

            this.snackBar.open(
              'Table updated successfully',
              'Close',
              {
                duration: 3000,
                horizontalPosition: 'right',
                verticalPosition: 'top'
              }
            );

            this.dialogRef.close(true);

          },

          error: (err) => {

            console.error(err);

            this.saving = false;

            this.snackBar.open(
              'Failed to update table',
              'Close',
              {
                duration: 3000
              }
            );

          }

        });

      return;

    }

    this.tableService
      .create(request)
      .subscribe({

        next: () => {

          this.saving = false;

          this.snackBar.open(
            'Table created successfully',
            'Close',
            {
              duration: 3000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            }
          );

          this.dialogRef.close(true);

        },

        error: (err) => {

          console.error(err);

          this.saving = false;

          this.snackBar.open(
            'Failed to create table',
            'Close',
            {
              duration: 3000
            }
          );

        }

      });

  }

  cancel(): void {

    this.dialogRef.close(false);

  }

}