import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { BillingService } from '../billing.service';
import { Bill, PaymentMethod } from '../billing.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-billing-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatRadioModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    FormsModule
  ],
  templateUrl: './billing-detail.html',
  styleUrl: './billing-detail.css'
})
export class BillingDetail implements OnInit {

  private route = inject(ActivatedRoute);
  private billingService = inject(BillingService);
  private snackBar = inject(MatSnackBar);

  bill?: Bill;

  loading = true;

  error = '';

  paymentMethod: PaymentMethod = 'CASH';

  displayedColumns = [
    'item',
    'qty',
    'price',
    'total'
  ];

  ngOnInit(): void {
    this.loadBill();
  }

  loadBill(): void {

    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.billingService.getBill(id).subscribe({

      next: bill => {

        this.bill = bill;

        if (bill.paymentMethod) {
          this.paymentMethod = bill.paymentMethod;
        }

        this.loading = false;

      },

      error: () => {

        this.error = 'Unable to load bill.';
        this.loading = false;

      }

    });

  }

  markPaid(): void {

    if (!this.bill) return;

    this.billingService.makePayment(
      this.bill.id,
      this.paymentMethod
    ).subscribe({

      next: bill => {

        this.bill = bill;

        this.snackBar.open(
          'Payment successful',
          'Close',
          {
            duration: 3000
          }
        );

      }

    });

  }

}