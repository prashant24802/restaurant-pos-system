import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

import { filter } from 'rxjs/operators';

import { Order } from '../../../models/order.model';
import { OrderService } from '../../../services/order.service';
import { AddItemDialogComponent } from '../../../dialogs/add-item-dialog/add-item-dialog.component';

@Component({
  selector: 'app-order-detail',
  standalone: false,
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent implements OnInit {

  order: Order | null = null;

  loading = false;

  displayedColumns: string[] = [
    'name',
    'quantity',
    'price',
    'subtotal',
    'actions'
  ];

  readonly statuses = [
    'PENDING',
    'PREPARING',
    'READY',
    'SERVED',
    'PAID',
    'CANCELLED'
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private orderService: OrderService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {

      const id = Number(params.get('id'));

      if (!id) {

        this.router.navigate(['/dashboard/orders']);

        return;

      }

      this.loadOrder(id);

    });

  }

  private loadOrder(id: number): void {

  this.loading = true;

  this.orderService.getOrder(id).subscribe({

    next: (order) => {

      console.log('Order received:', order);

      this.order = order;

      this.loading = false;

      console.log('Loading finished');

    },

    error: (err) => {

      console.error('Order Error:', err);

      this.loading = false;

    }

  });

  }

  addItem(): void {

    if (!this.order) {
      return;
    }

    const dialogRef = this.dialog.open(
      AddItemDialogComponent,
      {
        width: '500px',
        disableClose: true,
        data: {
          orderId: this.order.id
        }
      }
    );

    dialogRef.afterClosed()
      .pipe(
        filter(result => !!result)
      )
      .subscribe(() => {

        this.loadOrder(this.order!.id);

        this.snackBar.open(
          'Item added successfully.',
          'Close',
          {
            duration: 2000
          }
        );

      });

  }

  removeItem(itemId: number): void {

    if (!this.order) {
      return;
    }

    this.orderService.removeItem(
      this.order.id,
      itemId
    ).subscribe({

      next: order => {

        this.order = order;

        this.snackBar.open(
          'Item removed.',
          'Close',
          {
            duration: 2000
          }
        );

      },

      error: () => {

        this.snackBar.open(
          'Unable to remove item.',
          'Close',
          {
            duration: 3000
          }
        );

      }

    });

  }

  updateStatus(status: string): void {

    if (!this.order || status === this.order.status) {
      return;
    }

    this.orderService.updateStatus(
      this.order.id,
      status
    ).subscribe({

      next: order => {

        this.order = order;

        this.snackBar.open(
          'Order status updated.',
          'Close',
          {
            duration: 2000
          }
        );

      },

      error: () => {

        this.snackBar.open(
          'Unable to update status.',
          'Close',
          {
            duration: 3000
          }
        );

      }

    });

  }

  cancelOrder(): void {

    if (!this.order) {
      return;
    }

    this.orderService.cancelOrder(
      this.order.id
    ).subscribe({

      next: order => {

        this.order = order;

        this.snackBar.open(
          'Order cancelled.',
          'Close',
          {
            duration: 2000
          }
        );

      },

      error: () => {

        this.snackBar.open(
          'Unable to cancel order.',
          'Close',
          {
            duration: 3000
          }
        );

      }

    });

  }

  goBack(): void {

    this.router.navigate(['/dashboard/orders']);

  }

}