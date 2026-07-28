import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import {
  MAT_DIALOG_DATA,
  MatDialogRef
} from '@angular/material/dialog';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { finalize } from 'rxjs/operators';
import { ChangeDetectorRef } from '@angular/core';

import { RestaurantTable } from '../../../table/models/table';
import { TableService } from '../../../table/services/table';

import { OrderService } from '../../services/order.service';

import { Order } from '../../models/order.model';

@Component({
  selector: 'app-create-order-dialog',
  standalone: false,
  templateUrl: './create-order-dialog.component.html',
  styleUrls: ['./create-order-dialog.component.scss']
})
export class CreateOrderDialogComponent
  implements OnInit {

  form!: FormGroup;

  tables: RestaurantTable[] = [];

  loading = false;

  creating = false;

  constructor(

    private fb: FormBuilder,

    private tableService: TableService,

    private orderService: OrderService,

    private dialogRef: MatDialogRef<CreateOrderDialogComponent>,

    private cdr: ChangeDetectorRef,

    @Inject(MAT_DIALOG_DATA)
    public data: any

  ) { }

  ngOnInit(): void {

    this.form = this.fb.group({

      tableId: [
        null,
        Validators.required
      ]

    });

    queueMicrotask(() => {
        this.loadTables();

    });

  }

  private loadTables(): void {

    this.loading = true;
    this.cdr.detectChanges();

    this.tableService

      .getAvailable()

      .pipe(

        finalize(() => {
            this.loading = false;
            this.cdr.detectChanges()
         })

      )

      .subscribe({

        next: (tables: RestaurantTable[]) => {

          this.tables = tables;

        },

        error: console.error

      });

  }

  create(): void {
    console.log('Create button clicked');
    console.log('Form Value:', this.form.value);
    
    if (this.form.invalid) {
      console.log('Form Invalid');
      this.form.markAllAsTouched();
      return;
   }
   
   console.log('Sending request...');
   this.creating = true;
   this.orderService
   .createOrder(this.form.value)
   .pipe(
      finalize(() => this.creating = false)
    )
    .subscribe({
      
      next: (order: Order) => {
        console.log('SUCCESS', order);
        this.dialogRef.close(order);

      },

      error: (err) => {
        
        console.error('ERROR', err);

      }

    });

  }

  close(): void {

    this.dialogRef.close();

  }

}