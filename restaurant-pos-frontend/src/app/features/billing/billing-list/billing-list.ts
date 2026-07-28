import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';

import { BillingService } from '../billing.service';
import { Bill } from '../billing.model';

@Component({
  selector: 'app-billing-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatTableModule,
    MatButtonModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  templateUrl: './billing-list.html',
  styleUrl: './billing-list.css'
})
export class BillingList implements OnInit {

  private billingService = inject(BillingService);

  bills: Bill[] = [];

  loading = true;

  error = '';

  displayedColumns = [
    'invoice',
    'table',
    'order',
    'total',
    'status',
    'action'
  ];

  ngOnInit(): void {
    this.loadBills();
  }

  loadBills(): void {

    this.loading = true;

    this.billingService.getBills().subscribe({

      next: (response) => {

        this.bills = response;
        this.loading = false;

      },

      error: () => {

        this.error = 'Unable to load bills.';
        this.loading = false;

      }

    });

  }

}