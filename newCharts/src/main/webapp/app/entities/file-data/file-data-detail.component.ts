import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TableModule } from 'primeng/table';
import { IFileData } from '../../shared/model/file-data.model';
import { DataHeader} from '../../shared/model/data-header.model';
import { DataLine} from '../../shared/model/data-line.model';
import {CheckboxModule} from 'primeng/checkbox';
@Component({
  selector: 'jhi-file-data-detail',
  templateUrl: './file-data-detail.component.html',
  styles: [`
            .wrongValue:before{
              content: '\e90b';
            }`
          ]
})
export class FileDataDetailComponent implements OnInit {
  fileData: IFileData;
  headers: DataHeader[];
  dataLines: DataLine[];
  // cols: any[];
  
  
  
  //faTimes
  //faCheck
  //pi-check
  //pi-times

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileData }) => {
      this.fileData = fileData;
      //this.cols =  this.fileData.headers.filter;
      this.headers = this.fileData.headers.filter(header => header.show);
      this.dataLines =  Object.assign([], this.fileData.dataLines); ;
        for(let i = 0;i<this.fileData.headers.length;i++) { 
          if(!this.fileData.headers[i].show){
            this.dataLines.forEach(elm => elm.data.splice(i, 1)) ; 
          }
        }
 

      //  for(let i = 1;i<this.fileData.importData.data.length;i++) { 
      //   this.rows[i-1] = [];
      //    this.rows[i-1]= this.fileData.importData.data[i];
      // }
    });
  }
  getCellClass(data: any):string{
    
    if(data){
      return null;
    }
    return 'fa fa-times';
  }
  

  previousState() {
    window.history.back();
  }
}
