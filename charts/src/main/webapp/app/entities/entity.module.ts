import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ChartsAnswerScaleModule } from './answer-scale/answer-scale.module';
import { ChartsQuestionModule } from './question/question.module';
import { ChartsAttributeModule } from './attribute/attribute.module';
import { ChartsAnswerModule } from './answer/answer.module';
import { ChartsSurveyModule } from './survey/survey.module';
import { ChartsSurveyResultModule } from './survey-result/survey-result.module';
import { ChartsRespondingModule } from './responding/responding.module';
import { ChartsAskedModule } from './asked/asked.module';
import { ChartsPersonModule } from './person/person.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ChartsAnswerScaleModule,
        ChartsQuestionModule,
        ChartsAttributeModule,
        ChartsAnswerModule,
        ChartsSurveyModule,
        ChartsSurveyResultModule,
        ChartsRespondingModule,
        ChartsAskedModule,
        ChartsPersonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ChartsEntityModule {}
