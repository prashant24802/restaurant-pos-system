import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { OrderListComponent } from './pages/components/order-list/order-list.component';
import { OrderDetailComponent } from './pages/components/order-detail/order-detail.component';

const routes: Routes = [

  {
    path: '',
    component: OrderListComponent
  },

  {
    path: ':id',
    component: OrderDetailComponent
  }

];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class OrdersRoutingModule {}