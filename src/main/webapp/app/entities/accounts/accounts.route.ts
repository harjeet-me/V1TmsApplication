import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccounts, Accounts } from 'app/shared/model/accounts.model';
import { AccountsService } from './accounts.service';
import { AccountsComponent } from './accounts.component';
import { AccountsDetailComponent } from './accounts-detail.component';
import { AccountsUpdateComponent } from './accounts-update.component';

@Injectable({ providedIn: 'root' })
export class AccountsResolve implements Resolve<IAccounts> {
  constructor(private service: AccountsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccounts> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accounts: HttpResponse<Accounts>) => {
          if (accounts.body) {
            return of(accounts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accounts());
  }
}

export const accountsRoute: Routes = [
  {
    path: '',
    component: AccountsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountsDetailComponent,
    resolve: {
      accounts: AccountsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountsUpdateComponent,
    resolve: {
      accounts: AccountsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountsUpdateComponent,
    resolve: {
      accounts: AccountsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accounts.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
