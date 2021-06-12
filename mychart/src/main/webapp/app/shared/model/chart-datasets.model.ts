export interface ChartDataset{
    label?: string;
    data?: number[];
}

export class PieDataset implements ChartDataset {
    constructor(public label?: string, public data?: number[], public backgroundColor?: string[], public hoverBackgroundColor?: string[]) {}
 }

export class DiagramDataset implements ChartDataset  {
    constructor(public label?: string, public data?: number[], public backgroundColor?: string, public borderColor?: string) {}
 }

  


