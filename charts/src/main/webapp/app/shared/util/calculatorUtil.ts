import { ISurveyResult } from '../model/survey-result.model';

export class CalculatorUtil{
    

    public static calcAverageQuestion(surveyResultArr: ISurveyResult[], questionId: number) : number{
        
        let sum: number = 0;
        if(surveyResultArr === null || surveyResultArr.length === 0){
            return 0;
        }

        for(let surveyResult of surveyResultArr){
            sum += surveyResult.askedResult.answers[questionId] === null ? 0 :  surveyResult.askedResult.answers[questionId];
        }
        return sum/surveyResultArr.length;
    }



    
    

}