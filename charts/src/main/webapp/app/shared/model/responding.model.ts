import { IAttribute } from './attribute.model';
import { ISurvey } from './survey.model';
import { ISurveyResult } from './survey-result.model';

export interface IResponding {
    id?: string;
    description?: string;
    attributes?: IAttribute[];
    surveys?: ISurvey[];
    //surveyResults? : ISurveyResult[];
}

export class Responding implements IResponding {
    constructor(public id?: string, public description?: string, public attributes?: IAttribute[], surveys?: ISurvey[]) {}
}
