export interface IChartCriteria {
    chartType?: String;
	surveyId?: String;
	respondingId?: String;
	//relevant only if charType is percent
	questionId?: number;
   
}

export class ChartCriteria implements  IChartCriteria {
    constructor(public chartType?: String, public surveyId?: String,  public respondingId?: String, public questionId?: number ) {}
}
