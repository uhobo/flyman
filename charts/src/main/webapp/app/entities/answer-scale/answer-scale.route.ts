import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AnswerScale } from 'app/shared/model/answer-scale.model';
import { AnswerScaleService } from './answer-scale.service';
import { AnswerScaleComponent } from './answer-scale.component';
import { AnswerScaleDetailComponent } from './answer-scale-detail.component';
import { AnswerScaleUpdateComponent } from './answer-scale-update.component';
import { AnswerScaleDeletePopupComponent } from './answer-scale-delete-dialog.component';
import { IAnswerScale } from 'app/shared/model/answer-scale.model';

@Injectable({ providedIn: 'root' })
export class AnswerScaleResolve implements Resolve<IAnswerScale> {
    constructor(private service: AnswerScaleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((answerScale: HttpResponse<AnswerScale>) => answerScale.body));
        }
        return of(new AnswerScale());
    }
}

export const answerScaleRoute: Routes = [
    {
        path: 'answer-scale',
        component: AnswerScaleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.answerScale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'answer-scale/:id/view',
        component: AnswerScaleDetailComponent,
        resolve: {
            answerScale: AnswerScaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.answerScale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'answer-scale/new',
        component: AnswerScaleUpdateComponent,
        resolve: {
            answerScale: AnswerScaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.answerScale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'answer-scale/:id/edit',
        component: AnswerScaleUpdateComponent,
        resolve: {
            answerScale: AnswerScaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.answerScale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const answerScalePopupRoute: Routes = [
    {
        path: 'answer-scale/:id/delete',
        component: AnswerScaleDeletePopupComponent,
        resolve: {
            answerScale: AnswerScaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.answerScale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
