import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICarrier, Carrier } from 'app/shared/model/carrier.model';
import { CarrierService } from './carrier.service';
import { CarrierComponent } from './carrier.component';
import { CarrierDetailComponent } from './carrier-detail.component';
import { CarrierUpdateComponent } from './carrier-update.component';

@Injectable({ providedIn: 'root' })
export class CarrierResolve implements Resolve<ICarrier> {
  constructor(private service: CarrierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarrier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((carrier: HttpResponse<Carrier>) => {
          if (carrier.body) {
            return of(carrier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Carrier());
  }
}

export const carrierRoute: Routes = [
  {
    path: '',
    component: CarrierComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.carrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CarrierDetailComponent,
    resolve: {
      carrier: CarrierResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.carrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CarrierUpdateComponent,
    resolve: {
      carrier: CarrierResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.carrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CarrierUpdateComponent,
    resolve: {
      carrier: CarrierResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.carrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
