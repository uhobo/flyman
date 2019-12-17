import { ChartDataset, PieDataset, DiagramDataset} from './chart-datasets.model';
import { TreeNode } from 'primeng/api';
  
export interface ChartData {
    labels?: string[];
    datasets?: ChartDataset[];
}

export class DiagramChart implements ChartData {
    constructor(public labels?: string[], public datasets?: DiagramDataset[]) {}
 }

 export class PieChart implements ChartData {
    constructor(public labels?: string[], public datasets?: PieDataset[]) {}
 }

 export class ChartRequet{
    constructor(public fileDataId?: string, public chartType?: number, public selectedSubject?:number, public includeAverage?:boolean,  public includeMedian?:boolean, public selectXnodes?: TreeNode[],  public selectYnodes?: TreeNode[]) {}
 }

 export class ChartResponse{
    constructor(public chartType?: number, public chartTypeStr?: string, public data?: ChartData, public options?: any) {}
 }

 export class ExportExcelFile{
    constructor(public fileName?: string, public byteArr?: Blob){}
 }