import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Attribute } from 'app/shared/model/attribute.model';
import { AttributeService } from './attribute.service';
import { AttributeComponent } from './attribute.component';
import { AttributeDetailComponent } from './attribute-detail.component';
import { AttributeUpdateComponent } from './attribute-update.component';
import { AttributeDeletePopupComponent } from './attribute-delete-dialog.component';
import { IAttribute } from 'app/shared/model/attribute.model';

@Injectable({ providedIn: 'root' })
export class AttributeResolve implements Resolve<IAttribute> {
    constructor(private service: AttributeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((attribute: HttpResponse<Attribute>) => attribute.body));
        }
        return of(new Attribute());
    }
}

export const attributeRoute: Routes = [
    {
        path: 'attribute',
        component: AttributeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.attribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'attribute/:id/view',
        component: AttributeDetailComponent,
        resolve: {
            attribute: AttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.attribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'attribute/new',
        component: AttributeUpdateComponent,
        resolve: {
            attribute: AttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.attribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'attribute/:id/edit',
        component: AttributeUpdateComponent,
        resolve: {
            attribute: AttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.attribute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const attributePopupRoute: Routes = [
    {
        path: 'attribute/:id/delete',
        component: AttributeDeletePopupComponent,
        resolve: {
            attribute: AttributeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.attribute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
