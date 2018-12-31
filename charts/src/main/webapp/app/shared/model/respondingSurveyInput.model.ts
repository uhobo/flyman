import { ISurvey } from './survey.model';
import { IResponding } from './responding.model';

export interface IRespondingSurveyInput{
    survey?: ISurvey;
    responding?: IResponding;
}

export class RespondingSurveyInput implements IRespondingSurveyInput{
    constructor(public survey?: ISurvey, public responding?: IResponding) {}   
}