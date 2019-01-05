import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage, LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { ChartsSharedModule } from 'app/shared';
import { ChartsCoreModule } from 'app/core';
import { ChartsAppRoutingModule } from './app-routing.module';
import { ChartsHomeModule } from './home/home.module';
import { ChartsAccountModule } from './account/account.module';
import { ChartsEntityModule } from './entities/entity.module';
import { SurveyDataService } from 'app/shared/util/SurveyDataService';
import { ModelDataService } from 'app/shared/util/modelDataService';
import { RespondingAddSurveyDialogComponent } from 'app/entities/responding/responding-add-survey-dialog.component';
import {AttributeAddDialogComponent} from 'app/entities/attribute/attribute-add-dialog.component';
import * as moment from 'moment';
import { ChartsDashboardModule } from './dashboard/dashboard.module';
import { ChartsprimengModule } from './primeng/primeng.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import { ChartSurveyDialogComponent } from './entities/survey-result/survey-chart-dialog.component';

@NgModule({
    imports: [
        BrowserModule,
        ChartsAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        ChartsSharedModule,
        ChartsCoreModule,
        ChartsHomeModule,
        ChartsAccountModule,
        ChartsDashboardModule,
        ChartsprimengModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        ChartsEntityModule,
        ReactiveFormsModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent, RespondingAddSurveyDialogComponent, 
                AttributeAddDialogComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
        , SurveyDataService
        , FormBuilder
        , ModelDataService
    ],
    bootstrap: [JhiMainComponent],
    entryComponents: [RespondingAddSurveyDialogComponent, AttributeAddDialogComponent,ChartSurveyDialogComponent]
})
export class ChartsAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
