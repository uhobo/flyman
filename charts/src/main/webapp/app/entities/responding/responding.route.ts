import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Responding } from 'app/shared/model/responding.model';
import { RespondingService } from './responding.service';
import { RespondingComponent } from './responding.component';
import { RespondingDetailComponent } from './responding-detail.component';
import { RespondingUpdateComponent } from './responding-update.component';
import { RespondingDeletePopupComponent } from './responding-delete-dialog.component';


import { IResponding } from 'app/shared/model/responding.model';

@Injectable({ providedIn: 'root' })
export class RespondingResolve implements Resolve<IResponding> {
    constructor(private service: RespondingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((responding: HttpResponse<Responding>) => responding.body));
        }
        return of(new Responding());
    }
}

export const respondingRoute: Routes = [
    {
        path: 'responding',
        component: RespondingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.responding.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'responding/:id/view',
        component: RespondingDetailComponent,
        resolve: {
            responding: RespondingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.responding.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'responding/new',
        component: RespondingUpdateComponent,
        resolve: {
            responding: RespondingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.responding.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'responding/:id/edit',
        component: RespondingUpdateComponent,
        resolve: {
            responding: RespondingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.responding.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const respondingPopupRoute: Routes = [
    {
        path: 'responding/:id/delete',
        component: RespondingDeletePopupComponent,
        resolve: {
            responding: RespondingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.responding.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
