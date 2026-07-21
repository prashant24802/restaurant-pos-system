import { Routes } from '@angular/router';

import { Login } from './features/auth/login/login';

import { MainLayout } from './layout/main-layout/main-layout';

import { Dashboard } from './features/dashboard/dashboard/dashboard';

import { authGuard } from './core/guards/auth-guard';

import { MenuList } from './features/menu/menu-list/menu-list';

import { TableList } from './features/table/table-list/table-list';

export const routes: Routes = [

  {
    path: '',
    component: Login
  },

  {
    path: 'dashboard',

    component: MainLayout,

    canActivate: [authGuard],

    children: [

      {
        path: '',
        component: Dashboard
      },

      {
        path: 'menu',
        component: MenuList
      },

      {
        path: 'tables',
        component: TableList
      }

    ]

  }

];