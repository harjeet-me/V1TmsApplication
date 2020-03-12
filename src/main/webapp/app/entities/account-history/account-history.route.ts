import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccountHistory, AccountHistory } from 'app/shared/model/account-history.model';
import { AccountHistoryService } from './account-history.service';
import { AccountHistoryComponent } from './account-history.component';
import { AccountHistoryDetailComponent } from './account-history-detail.component';
import { AccountHistoryUpdateComponent } from './account-history-update.component';

@Injectable({ providedIn: 'root' })
export class AccountHistoryResolve implements Resolve<IAccountHistory> {
  constructor(private service: AccountHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccountHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accountHistory: HttpResponse<AccountHistory>) => {
          if (accountHistory.body) {
            return of(accountHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AccountHistory());
  }
}

export const accountHistoryRoute: Routes = [
  {
    path: '',
    component: AccountHistoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accountHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountHistoryDetailComponent,
    resolve: {
      accountHistory: AccountHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accountHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountHistoryUpdateComponent,
    resolve: {
      accountHistory: AccountHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accountHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountHistoryUpdateComponent,
    resolve: {
      accountHistory: AccountHistoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.accountHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
