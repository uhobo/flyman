import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ChartsSharedModule } from 'app/shared';
import {
    AnswerScaleComponent,
    AnswerScaleDetailComponent,
    AnswerScaleUpdateComponent,
    AnswerScaleDeletePopupComponent,
    AnswerScaleDeleteDialogComponent,
    answerScaleRoute,
    answerScalePopupRoute
} from './';

const ENTITY_STATES = [...answerScaleRoute, ...answerScalePopupRoute];

@NgModule({
    imports: [ChartsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AnswerScaleComponent,
        AnswerScaleDetailComponent,
        AnswerScaleUpdateComponent,
        AnswerScaleDeleteDialogComponent,
        AnswerScaleDeletePopupComponent
    ],
    entryComponents: [AnswerScaleComponent, AnswerScaleUpdateComponent, AnswerScaleDeleteDialogComponent, AnswerScaleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsAnswerScaleModule {}
