import { Routes } from '@angular/router';
import { RatesListComponent } from './rates-list/rates-list.component';
import { RateDetailComponent } from './rate-detail/rate-detail.component';

export const routes: Routes = [
  {
    path: '',
    component: RatesListComponent,
    children: [
      {
        path: ':currency',
        component: RateDetailComponent,
      },
    ],
  },
];
