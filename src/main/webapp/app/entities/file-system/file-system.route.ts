import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFileSystem, FileSystem } from 'app/shared/model/file-system.model';
import { FileSystemService } from './file-system.service';
import { FileSystemComponent } from './file-system.component';
import { FileSystemDetailComponent } from './file-system-detail.component';
import { FileSystemUpdateComponent } from './file-system-update.component';

@Injectable({ providedIn: 'root' })
export class FileSystemResolve implements Resolve<IFileSystem> {
  constructor(private service: FileSystemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFileSystem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fileSystem: HttpResponse<FileSystem>) => {
          if (fileSystem.body) {
            return of(fileSystem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FileSystem());
  }
}

export const fileSystemRoute: Routes = [
  {
    path: '',
    component: FileSystemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tmsV1ApplicationApp.fileSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FileSystemDetailComponent,
    resolve: {
      fileSystem: FileSystemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.fileSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FileSystemUpdateComponent,
    resolve: {
      fileSystem: FileSystemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.fileSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FileSystemUpdateComponent,
    resolve: {
      fileSystem: FileSystemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tmsV1ApplicationApp.fileSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
