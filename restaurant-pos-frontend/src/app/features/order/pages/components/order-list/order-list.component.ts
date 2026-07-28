import {
  ChangeDetectorRef,
  Component,
  OnInit,
  ViewChild
} from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';

import {
  Subject,
  debounceTime,
  distinctUntilChanged
} from 'rxjs';

import { Order } from '../../../models/order.model';
import { OrderService } from '../../../services/order.service';
import { CreateOrderDialogComponent } from '../../../dialogs/create-order-dialog/create-order-dialog.component';

@Component({
  selector: 'app-order-list',
  standalone: false,
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {

  displayedColumns: string[] = [
    'id',
    'table',
    'status',
    'subtotal',
    'tax',
    'total',
    'createdAt',
    'actions'
  ];

  orders: Order[] = [];

  totalElements = 0;

  pageIndex = 0;

  pageSize = 10;

  loading = false;

  search = '';

  status = '';

  private readonly searchSubject = new Subject<string>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  constructor(
    private orderService: OrderService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.searchSubject
      .pipe(
        debounceTime(400),
        distinctUntilChanged()
      )
      .subscribe(value => {

        this.search = value;
        this.pageIndex = 0;

        this.loadOrders();

      });

    this.loadOrders();

  }

  loadOrders(): void {

    this.loading = true;
    this.cdr.detectChanges();

    this.orderService
      .getOrders(
        this.pageIndex,
        this.pageSize,
        this.search,
        this.status
      )
      .subscribe({

        next: page => {

          this.orders = page.content;
          this.totalElements = page.totalElements;

          this.loading = false;
          this.cdr.detectChanges();

        },

        error: () => {

          this.loading = false;
          this.cdr.detectChanges();

          this.snackBar.open(
            'Failed to load orders',
            'Close',
            {
              duration: 3000
            }
          );

        }

      });

  }

  refresh(): void {

    this.loadOrders();

  }

  onSearch(value: string): void {

    this.searchSubject.next(value);

  }

  onStatusChange(status: string): void {

    this.status = status;
    this.pageIndex = 0;

    this.loadOrders();

  }

  onPageChange(event: PageEvent): void {

    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    this.loadOrders();

  }

  viewOrder(order: Order): void {

    this.router.navigate(
      [order.id],
      {
        relativeTo: this.route
      }
    );

  }

  createOrder(): void {

    const dialogRef = this.dialog.open(
      CreateOrderDialogComponent,
      {
        width: '450px',
        disableClose: true
      }
    );

    dialogRef.afterClosed().subscribe(result => {

      if (!result) {
        return;
      }

      this.snackBar.open(
        'Order created successfully',
        'Close',
        {
          duration: 3000
        }
      );

      this.loadOrders();

    });

  }

}