import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvoiceItem, InvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { InvoiceItemComponent } from './invoice-item.component';
import { InvoiceItemDetailComponent } from './invoice-item-detail.component';
import { InvoiceItemUpdateComponent } from './invoice-item-update.component';

@Injectable({ providedIn: 'root' })
export class InvoiceItemResolve implements Resolve<IInvoiceItem> {
  constructor(private service: InvoiceItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invoiceItem: HttpResponse<InvoiceItem>) => {
          if (invoiceItem.body) {
            return of(invoiceItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvoiceItem());
  }
}

export const invoiceItemRoute: Routes = [
  {
    path: '',
    component: InvoiceItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoiceItemDetailComponent,
    resolve: {
      invoiceItem: InvoiceItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoiceItemUpdateComponent,
    resolve: {
      invoiceItem: InvoiceItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoiceItemUpdateComponent,
    resolve: {
      invoiceItem: InvoiceItemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.invoiceItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
