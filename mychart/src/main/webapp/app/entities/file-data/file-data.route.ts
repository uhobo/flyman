import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
//import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FileData } from '../../shared/model/file-data.model';
import { FileDataService } from './file-data.service';
import { FileDataComponent } from './file-data.component';
import { FileDataDetailComponent } from './file-data-detail.component';
import { FileDataUpdateComponent } from './file-data-update.component';
import { FileDataChartComponent } from './file-data-chart.component';
import { FileDataDeletePopupComponent } from './file-data-delete-dialog.component';
import { IFileData } from '../../shared/model/file-data.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

@Injectable({ providedIn: 'root' })
export class FileDataResolve implements Resolve<IFileData> {
  constructor(private service: FileDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFileData> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FileData>) => response.ok),
        map((fileData: HttpResponse<FileData>) => fileData.body)
      );
    }
    return of(new FileData());
  }
}

export const fileDataRoute: Routes = [
  {
    path: '',
    component: FileDataComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'newChartsApp.fileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/chart',
    component: FileDataChartComponent,
    resolve: {
      fileData: FileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newChartsApp.fileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FileDataDetailComponent,
    resolve: {
      fileData: FileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newChartsApp.fileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FileDataUpdateComponent,
    resolve: {
      fileData: FileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newChartsApp.fileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FileDataUpdateComponent,
    resolve: {
      fileData: FileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newChartsApp.fileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fileDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FileDataDeletePopupComponent,
    resolve: {
      fileData: FileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newChartsApp.fileData.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
