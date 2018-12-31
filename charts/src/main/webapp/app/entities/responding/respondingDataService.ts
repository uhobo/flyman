
import { Injectable } from '@angular/core';
import { ISurvey } from 'app/shared/model/survey.model';
import {IResponding} from 'app/shared/model/responding.model';
@Injectable()
export class RespondingDataService {
        responding: IResponding;
        surveys : ISurvey[];

        setResponding(responding) {
                this.responding = responding;
        }
        getResponding() {
                return this.responding;
        }

        setSurveys(surveys){
            this.surveys = surveys;
        }

        getSurveys(){
            return this.surveys;
        }
}
