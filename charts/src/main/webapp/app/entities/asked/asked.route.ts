import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Asked } from 'app/shared/model/asked.model';
import { AskedService } from './asked.service';
import { AskedComponent } from './asked.component';
import { AskedDetailComponent } from './asked-detail.component';
import { AskedUpdateComponent } from './asked-update.component';
import { AskedDeletePopupComponent } from './asked-delete-dialog.component';
import { IAsked } from 'app/shared/model/asked.model';

@Injectable({ providedIn: 'root' })
export class AskedResolve implements Resolve<IAsked> {
    constructor(private service: AskedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((asked: HttpResponse<Asked>) => asked.body));
        }
        return of(new Asked());
    }
}

export const askedRoute: Routes = [
    {
        path: 'asked',
        component: AskedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.asked.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asked/:id/view',
        component: AskedDetailComponent,
        resolve: {
            asked: AskedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.asked.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asked/new',
        component: AskedUpdateComponent,
        resolve: {
            asked: AskedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.asked.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asked/:id/edit',
        component: AskedUpdateComponent,
        resolve: {
            asked: AskedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.asked.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const askedPopupRoute: Routes = [
    {
        path: 'asked/:id/delete',
        component: AskedDeletePopupComponent,
        resolve: {
            asked: AskedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.asked.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
