
import { Injectable } from '@angular/core';
import { ISurvey } from 'app/shared/model/survey.model';
@Injectable()
export class SurveyDataService {
        survey: ISurvey;
        setData(data) {
                this.survey = data;
        }
        getData() {
                return this.survey;
        }
}
