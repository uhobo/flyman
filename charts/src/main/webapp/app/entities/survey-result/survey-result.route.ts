import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SurveyResult } from 'app/shared/model/survey-result.model';
import { SurveyResultService } from './survey-result.service';
import { SurveyResultComponent } from './survey-result.component';
import { SurveyResultDetailComponent } from './survey-result-detail.component';
import { SurveyResultUpdateComponent } from './survey-result-update.component';
import { SurveyResultDeletePopupComponent } from './survey-result-delete-dialog.component';
import { ISurveyResult } from 'app/shared/model/survey-result.model';

@Injectable({ providedIn: 'root' })
export class SurveyResultResolve implements Resolve<ISurveyResult[]> {
    constructor(private service: SurveyResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const surveyId = route.params['surveyId'];
        const respondingId = route.params['respondingId'];
        if(surveyId && respondingId){
            return this.service.findBySurveyAndResponding(surveyId, respondingId ).pipe(map((surveyResults: HttpResponse<SurveyResult[]>) => surveyResults.body));
        }
        
        
        // const id = route.params['id'] ? route.params['id'] : null;
        // if (id) {
        //     return this.service.find(id).pipe(map((surveyResult: HttpResponse<SurveyResult[]>) => surveyResult.body));
        // }
        
        //let surveyResultArr = [];
        return null
    }
}

export const surveyResultRoute: Routes = [
    {
        path: 'survey-result',
        component: SurveyResultComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.surveyResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'survey-result/:id/view',
        component: SurveyResultDetailComponent,
        resolve: {
            surveyResult: SurveyResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.surveyResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'survey-result/new',
        component: SurveyResultUpdateComponent,
        resolve: {
            surveyResult: SurveyResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.surveyResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'survey-result/:id/edit',
        component: SurveyResultUpdateComponent,
        resolve: {
            surveyResult: SurveyResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.surveyResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'survey-result/:surveyId/:respondingId/view',
        component: SurveyResultDetailComponent,
        resolve: {
            surveyResults: SurveyResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.surveyResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const surveyResultPopupRoute: Routes = [
    {
        path: 'survey-result/:id/delete',
        component: SurveyResultDeletePopupComponent,
        resolve: {
            surveyResult: SurveyResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'chartsApp.surveyResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
