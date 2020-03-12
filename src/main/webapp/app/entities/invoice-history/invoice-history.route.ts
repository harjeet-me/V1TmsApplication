import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvoiceHistory, InvoiceHistory } from 'app/shared/model/invoice-history.model';
import { InvoiceHistoryService } from './invoice-history.service';
import { InvoiceHistoryComponent } from './invoice-history.component';
import { InvoiceHistoryDetailComponent } from './invoice-history-detail.component';
import { InvoiceHistoryUpdateComponent } from './invoice-history-update.component';

@Injectable({ providedIn: 'root' })
export class InvoiceHistoryResolve implements Resolve<IInvoiceHistory> {
  constructor(private service: InvoiceHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invoiceHistory: HttpResponse<InvoiceHistory>) => {
          if (invoiceHistory.body) {
            return of(invoiceHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvoiceHistory());
  }
}

export const invoiceHistoryRoute: Routes = [
  {
    path: '',
    component: InvoiceHistoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoiceHistoryDetailComponent,
    resolve: {
      invoiceHistory: InvoiceHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoiceHistoryUpdateComponent,
    resolve: {
      invoiceHistory: InvoiceHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoiceHistoryUpdateComponent,
    resolve: {
      invoiceHistory: InvoiceHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
