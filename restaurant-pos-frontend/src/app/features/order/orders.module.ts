import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { OrdersRoutingModule } from './orders-routing.module';

import { OrderListComponent } from './pages/components/order-list/order-list.component';
import { OrderDetailComponent } from './pages/components/order-detail/order-detail.component';

import { CreateOrderDialogComponent } from './dialogs/create-order-dialog/create-order-dialog.component';
import { AddItemDialogComponent } from './dialogs/add-item-dialog/add-item-dialog.component';

import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { MatDialogModule } from '@angular/material/dialog';

import { MatCardModule } from '@angular/material/card';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

import { MatSnackBarModule } from '@angular/material/snack-bar';

import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatChipsModule } from '@angular/material/chips';
@NgModule({
  declarations: [
    OrderListComponent,
    OrderDetailComponent,
    CreateOrderDialogComponent,
    AddItemDialogComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,

    OrdersRoutingModule,

    MatTableModule,
    MatPaginatorModule,
    MatSortModule,

    MatButtonModule,
    MatIconModule,

    MatDialogModule,

    MatCardModule,

    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,

    MatSnackBarModule,

    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class OrdersModule {}