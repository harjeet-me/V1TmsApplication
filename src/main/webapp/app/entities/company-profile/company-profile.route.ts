import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompanyProfile, CompanyProfile } from 'app/shared/model/company-profile.model';
import { CompanyProfileService } from './company-profile.service';
import { CompanyProfileComponent } from './company-profile.component';
import { CompanyProfileDetailComponent } from './company-profile-detail.component';
import { CompanyProfileUpdateComponent } from './company-profile-update.component';

@Injectable({ providedIn: 'root' })
export class CompanyProfileResolve implements Resolve<ICompanyProfile> {
  constructor(private service: CompanyProfileService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((companyProfile: HttpResponse<CompanyProfile>) => {
          if (companyProfile.body) {
            return of(companyProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompanyProfile());
  }
}

export const companyProfileRoute: Routes = [
  {
    path: '',
    component: CompanyProfileComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.companyProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompanyProfileDetailComponent,
    resolve: {
      companyProfile: CompanyProfileResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.companyProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompanyProfileUpdateComponent,
    resolve: {
      companyProfile: CompanyProfileResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.companyProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompanyProfileUpdateComponent,
    resolve: {
      companyProfile: CompanyProfileResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.companyProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
