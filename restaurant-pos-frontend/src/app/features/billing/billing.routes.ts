import { Routes } from '@angular/router';

import { BillingList } from './billing-list/billing-list';
import { BillingDetail } from './billing-detail/billing-detail';

export const BILLING_ROUTES: Routes = [

  {
    path: '',
    component: BillingList
  },

  {
    path: ':id',
    component: BillingDetail
  }

];