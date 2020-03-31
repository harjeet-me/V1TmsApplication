import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmail, Email } from 'app/shared/model/email.model';
import { EmailService } from './email.service';
import { EmailComponent } from './email.component';
import { EmailDetailComponent } from './email-detail.component';
import { EmailUpdateComponent } from './email-update.component';

@Injectable({ providedIn: 'root' })
export class EmailResolve implements Resolve<IEmail> {
  constructor(private service: EmailService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmail> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((email: HttpResponse<Email>) => {
          if (email.body) {
            return of(email.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Email());
  }
}

export const emailRoute: Routes = [
  {
    path: '',
    component: EmailComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tmsV1ApplicationApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmailDetailComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmailUpdateComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmailUpdateComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/sendEmail',
    component: EmailUpdateComponent,
    resolve: {
      email: EmailResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.email.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
