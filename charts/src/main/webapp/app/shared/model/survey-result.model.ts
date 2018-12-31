import { Moment } from 'moment';
import { ISurvey } from './survey.model';
import { IAsked } from './asked.model';
import { IResponding } from './responding.model';
import { IAnswer } from './answer.model';
import { IAskedResult } from './asked-result.model';

export interface ISurveyResult {
    id?: string;
    submitDate?: Moment;
    survey?: ISurvey;
    responding?: IResponding;
    askedResult?: IAskedResult;
    getResultsMap(): Map<Number,Number>;
}

export class SurveyResult implements ISurveyResult {
    constructor(
        public id?: string,
        public submitDate?: Moment,
        public survey?: ISurvey,
        public responding?: IResponding,
        public askedResult?: IAskedResult
    ) {}

    getResultsMap() : Map<Number,Number>{
        let resultMap = new Map<Number,Number>();
        for (let question of this.survey.questions){
            resultMap.set(question.questionId, this.askedResult.answers[question.questionId]);
        }
        return resultMap;
                
    }   



}
