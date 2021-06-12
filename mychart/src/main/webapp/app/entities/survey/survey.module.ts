import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MychartSharedModule } from 'app/shared/shared.module';
import { SurveyComponent } from './survey.component';
import { SurveyDetailComponent } from './survey-detail.component';
import { SurveyUpdateComponent } from './survey-update.component';
import { SurveyDeleteDialogComponent } from './survey-delete-dialog.component';
import { surveyRoute } from './survey.route';

@NgModule({
  imports: [MychartSharedModule, RouterModule.forChild(surveyRoute)],
  declarations: [SurveyComponent, SurveyDetailComponent, SurveyUpdateComponent, SurveyDeleteDialogComponent],
  entryComponents: [SurveyDeleteDialogComponent]
})
export class MychartSurveyModule {}
