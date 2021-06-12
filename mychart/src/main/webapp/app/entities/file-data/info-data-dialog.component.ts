import { Component,OnInit } from '@angular/core';
import {DynamicDialogRef} from 'primeng/api';
import {DynamicDialogConfig} from 'primeng/api';
import { InfoData } from 'app/shared/model/info-data.model';



@Component({
    templateUrl: './info-data-dialog.component.html',
   
})
export class InfoDataDialogComponent implements OnInit{
     infoDataList:InfoData[];
    constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig) { }
    OnInit():void{
       this.infoDataList = this.config.data.list;
    }
    ngOnInit() :void{
      this.infoDataList = this.config.data.list;
    }
    closeMe():void{
        this.ref.close();
    }
     
}