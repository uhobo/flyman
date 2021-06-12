import { Component } from '@angular/core';
import {InputTextModule} from 'primeng/inputtext'
import {DynamicDialogRef} from 'primeng/api';
import {DynamicDialogConfig} from 'primeng/api';


@Component({
    templateUrl: './file-data-dynamic-dialog.component.html',
   
})
export class FileDataDynamicDialogComponent {
    chartName:string;
    constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) {}

    giveName(){
        this.ref.close(this.chartName);
    }
    closeMe(){
        this.ref.close();
    }
}