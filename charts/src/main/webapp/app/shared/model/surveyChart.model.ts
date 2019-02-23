
export class ChartDataWrapper{
    chartType?: string;
    chartData?: SurveyChart;
    options?: ChartOptions;
}


export class SurveyChart{
    labels?:string[] = new Array<string>();
    datasets?: ChartDataSet[] = new Array<ChartDataSet>();

}

export class ChartDataSet{
    label?:string ='';
    backgroundColor?:string = '#42A5F5'; 
    borderColor?:string = '#1E88E5';
    data?:number[] = new Array<number>();
}


export class ChartOptions{
    title?:Title;
    legend?:Legend = new Legend();
}

export class Title{
    display?:boolean;
    text?: string;
    fontSize?: number;

}

export class Legend{
    position?: string;
}