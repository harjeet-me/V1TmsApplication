import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransactionsRecord, TransactionsRecord } from 'app/shared/model/transactions-record.model';
import { TransactionsRecordService } from './transactions-record.service';
import { TransactionsRecordComponent } from './transactions-record.component';
import { TransactionsRecordDetailComponent } from './transactions-record-detail.component';
import { TransactionsRecordUpdateComponent } from './transactions-record-update.component';

@Injectable({ providedIn: 'root' })
export class TransactionsRecordResolve implements Resolve<ITransactionsRecord> {
  constructor(private service: TransactionsRecordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransactionsRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transactionsRecord: HttpResponse<TransactionsRecord>) => {
          if (transactionsRecord.body) {
            return of(transactionsRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransactionsRecord());
  }
}

export const transactionsRecordRoute: Routes = [
  {
    path: '',
    component: TransactionsRecordComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tmsV1ApplicationApp.transactionsRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionsRecordDetailComponent,
    resolve: {
      transactionsRecord: TransactionsRecordResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.transactionsRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionsRecordUpdateComponent,
    resolve: {
      transactionsRecord: TransactionsRecordResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.transactionsRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionsRecordUpdateComponent,
    resolve: {
      transactionsRecord: TransactionsRecordResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.transactionsRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
