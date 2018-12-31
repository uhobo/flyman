import { IAsked } from './asked.model';
import { NUMBER_TYPE } from '@angular/compiler/src/output/output_ast';


export interface IAskedResult{
    asked?: IAsked;
    answers?: { [name: number]: number };
    getAverage(): any;
}

export class AskedResult implements IAskedResult{
    constructor(asked?: IAsked, answers?: Map<number,number>){ }

    getAverage(){
        
    }   
}