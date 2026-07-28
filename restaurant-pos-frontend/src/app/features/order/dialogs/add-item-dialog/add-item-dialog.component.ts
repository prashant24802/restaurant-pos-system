import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { finalize } from 'rxjs/operators';

import { Menu } from '../../../menu/services/menu';
import { MenuItem } from '../../../menu/models/menu-item';

import { OrderService } from '../../services/order.service';
import { Order } from '../../models/order.model';

@Component({
  selector: 'app-add-item-dialog',
  standalone: false,
  templateUrl: './add-item-dialog.component.html',
  styleUrls: ['./add-item-dialog.component.scss']
})
export class AddItemDialogComponent implements OnInit {

  form!: FormGroup;

  menuItems: MenuItem[] = [];

  loading = false;

  saving = false;

  constructor(
    private fb: FormBuilder,
    private menuService: Menu,
    private orderService: OrderService,
    private dialogRef: MatDialogRef<AddItemDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: { orderId: number }
  ) {}

  ngOnInit(): void {

    this.form = this.fb.group({
      menuItemId: [null, Validators.required],
      quantity: [
        1,
        [
          Validators.required,
          Validators.min(1)
        ]
      ]
    });

    this.loadMenuItems();
  }

  private loadMenuItems(): void {

    this.loading = true;

    this.menuService
      .getAvailable()
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe({
        next: (items: MenuItem[]) => {
          this.menuItems = items;
        },
        error: (err) => {
          console.error(err);
        }
      });

  }

  addItem(): void {

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving = true;

    this.orderService
      .addItem(
        this.data.orderId,
        this.form.value
      )
      .pipe(
        finalize(() => this.saving = false)
      )
      .subscribe({
        next: (order: Order) => {
          this.dialogRef.close(order);
        },
        error: (err) => {
          console.error(err);
        }
      });

  }

  close(): void {
    this.dialogRef.close();
  }

}