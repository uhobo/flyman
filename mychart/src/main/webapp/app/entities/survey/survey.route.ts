import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISurvey, Survey } from 'app/shared/model/survey.model';
import { SurveyService } from './survey.service';
import { SurveyComponent } from './survey.component';
import { SurveyDetailComponent } from './survey-detail.component';
import { SurveyUpdateComponent } from './survey-update.component';

@Injectable({ providedIn: 'root' })
export class SurveyResolve implements Resolve<ISurvey> {
  constructor(private service: SurveyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISurvey> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((survey: HttpResponse<Survey>) => {
          if (survey.body) {
            return of(survey.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Survey());
  }
}

export const surveyRoute: Routes = [
  {
    path: '',
    component: SurveyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mychartApp.survey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SurveyDetailComponent,
    resolve: {
      survey: SurveyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mychartApp.survey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SurveyUpdateComponent,
    resolve: {
      survey: SurveyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mychartApp.survey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SurveyUpdateComponent,
    resolve: {
      survey: SurveyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'mychartApp.survey.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
