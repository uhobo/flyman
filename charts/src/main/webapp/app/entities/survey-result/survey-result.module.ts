import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ChartModule, TreeModule, DropdownModule } from 'primeng/primeng';
import { ChartsSharedModule } from 'app/shared';
import {
    SurveyResultComponent,
    SurveyResultDetailComponent,
    SurveyResultUpdateComponent,
    SurveyResultDeletePopupComponent,
    SurveyResultDeleteDialogComponent,
    surveyResultRoute,
    surveyResultPopupRoute
} from './';
import { ChartSurveyDialogComponent } from './survey-chart-dialog.component';

const ENTITY_STATES = [...surveyResultRoute, ...surveyResultPopupRoute];

@NgModule({
    imports: [ChartsSharedModule,DropdownModule, ChartModule,TreeModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SurveyResultComponent,
        SurveyResultDetailComponent,
        SurveyResultUpdateComponent,
        SurveyResultDeleteDialogComponent,
        SurveyResultDeletePopupComponent,
        ChartSurveyDialogComponent
    ],
    entryComponents: [
        SurveyResultComponent,
        SurveyResultUpdateComponent,
        SurveyResultDeleteDialogComponent,
        SurveyResultDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsSurveyResultModule {}
