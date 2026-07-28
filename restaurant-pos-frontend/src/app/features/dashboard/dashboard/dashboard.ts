import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardService } from '../../../core/services/dashboard.service';
import { Dashboard as DashboardSummary } from '../../../core/models/dashboard';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {

  private dashboardService = inject(DashboardService);

  summary?: DashboardSummary;

  loading = true;

  error = '';

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {

  console.log('Loading dashboard...');

  this.dashboardService.getSummary().subscribe({

    next: (response) => {
      console.log('SUCCESS', response);
      this.summary = response;
      this.loading = false;
    },

    error: (err) => {
      console.error('ERROR', err);
      this.error = 'Unable to load dashboard.';
      this.loading = false;
    },

    complete: () => {
      console.log('COMPLETE');
    }

  });

 }
}