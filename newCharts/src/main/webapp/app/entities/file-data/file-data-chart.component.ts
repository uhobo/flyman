import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { FileDataService } from './file-data.service';
import { IFileData } from '../../shared/model/file-data.model';
import { DataHeader} from '../../shared/model/data-header.model';
import { DataLine} from '../../shared/model/data-line.model';
import { ChartDisplay} from '../../shared/model/chart-display.model';
import { ChartRequet, ChartResponse, ExportExcelFile } from '../../shared/model/chart-types.model';
import {CheckboxModule} from 'primeng/checkbox';
import {SelectItem, TreeNode, Message} from 'primeng/api';
import { throwStatement, breakStatement } from '@babel/types';
import {Observable} from 'rxjs';
import { TreeNodeService } from 'app/primeng/data/tree/service/treenode.service';
import { DialogService } from 'primeng/api';
import { FileDataDynamicDialogComponent } from './file-data-dynamic-dialog.component';

enum SubjectType{
  SERIE =1,
  CATEGORY = 2,
  TOPIC =3
}

@Component({
  selector: 'jhi-file-data-detail',
  templateUrl: './file-data-chart.component.html',
  providers: [DialogService]
})
export class FileDataChartComponent implements OnInit {
  msgs: Message[] = [];
  fileData: IFileData;

  chartDisplay: ChartDisplay;
  categorylist: SelectItem[];

  chartTypelist: SelectItem[];
  selectedChartType: number;
  chartTypeHidden: boolean;

  seriesList:SelectItem[];
  topicList:SelectItem[];
 
  subjectlist:SelectItem[];
  selectedSubject: number;

  checkBoxListXHidden:boolean;
  checkBoxListYHidden:boolean;
 
  averageCheckBox:SelectItem;
  medianCheckBox:SelectItem;
  checkBoxstatisticsHidden:boolean;
  isSelectAverageCheckBox:number[];
  isSelectMedianCheckBox:number[];
  
  groupCheckBox:SelectItem;
  isSelectGroupCheckBox:number[];
  checkBoxGroupHidden:boolean;

  distributionCheckBox:SelectItem;
  isSelectDistribution:number[];
  

  chartReponse: ChartResponse;
  visibleSidebar1: boolean;
  blobData : Blob;
  chartRequet:ChartRequet;

  axisXTree: TreeNode[];
  selectedXnodes:TreeNode[];
  axisYTree: TreeNode[];
  selectedYnodes:TreeNode[];

  constructor(protected activatedRoute: ActivatedRoute, protected fileDataService: FileDataService, protected jhiAlertService: JhiAlertService, protected dialogService: DialogService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileData }) => {
      this.fileData = fileData;
      this.visibleSidebar1 = false; 
      this.chartDisplay = new ChartDisplay();
      this.averageCheckBox = {label: 'Average', value: 1};
      this.medianCheckBox ={label: 'Median', value: 1};
      this.isSelectAverageCheckBox = [];
      this.isSelectMedianCheckBox =[];
     
      this.groupCheckBox = {label: 'Group', value: 1};
      this.isSelectGroupCheckBox = [];
      this.checkBoxGroupHidden = true;

      this.distributionCheckBox ={label: 'Distribution', value: 1};
      this.isSelectDistribution = [];

      this.categorylist = [];
      this.fileData.headers.forEach(element => {
          if(element.show && this.fileData.topicColIndex !== element.index){
            this.categorylist.push({label: element.title, value: element.index});
          }
          
      });
      this.seriesList = [];
      this.fileData.seriesList.forEach(series => {
        this.seriesList.push({label: series.title, value: series.value})
        }  
      );

      this.topicList = [];
      this.fileData.dataLines.forEach( dataLine => {
        var labelStr:string = dataLine.data[this.fileData.topicColIndex];
        this.topicList.push({label: labelStr, value: dataLine.lineNum});
      });

      this.subjectlist = [];
      this.subjectlist.push({label:'Serie', value: SubjectType.SERIE});
      this.subjectlist.push({label:'Category', value: SubjectType.CATEGORY});
      this.subjectlist.push({label: this.fileData.headers[this.fileData.topicColIndex].title, value: SubjectType.TOPIC});
      this.selectedSubject =SubjectType.SERIE;
      this.chartTypelist = [];
      this.chartTypelist.push({label: 'Pie', value: 1});
      this.chartTypelist.push({label: 'Diagram', value: 2});
      this.selectedChartType = 2;
      this.chartTypeHidden = true;
      this.setTreeFields();
      
      
    });
  }

  setTreeFields(){
    this.selectedXnodes = [];
    this.selectedYnodes = [];
    this.axisXTree = [];
    this.axisYTree = [];
    this.chartTypeHidden = true;
    this.checkBoxstatisticsHidden = false;
    this.checkBoxListXHidden = false;
    this.checkBoxListYHidden = false;

    if(this.fileData.groupColIndex || this.fileData.groupColIndex === 0){
      this.checkBoxGroupHidden = false;
    }
    
    switch(this.selectedSubject){
      case SubjectType.SERIE:

          this.chartTypeHidden = false;
          this.checkBoxGroupHidden = true;
          if(this.selectedChartType === 1){
              this.checkBoxstatisticsHidden = true;
              this.checkBoxListXHidden = true;
          }
        this.fileDataService.createTreeMenu(this.fileData.id, SubjectType.SERIE, this.isSelectGroupCheckBox.length>0).then(
            (res: HttpResponse<TreeNode[]>) => {
              this.axisXTree  = res.body;
              this.fillSelectedNodes(this.axisXTree, this.selectedXnodes);
               this.fileDataService.createTreeMenu(this.fileData.id, SubjectType.CATEGORY, this.isSelectGroupCheckBox.length>0).then(
                (res: HttpResponse<TreeNode[]>) => {
                  this.axisYTree  = res.body;
                  this.fillSelectedNodes(this.axisYTree, this.selectedYnodes);
                  this.showChart();
                },
                (res: HttpErrorResponse) => this.onError(res.message)); 
             },
            (res: HttpErrorResponse) => this.onError(res.message));

            

      break;
      case SubjectType.CATEGORY:
          this.fileDataService.createTreeMenu(this.fileData.id, SubjectType.CATEGORY, this.isSelectGroupCheckBox.length>0).then(
            (res: HttpResponse<TreeNode[]>) => {
               this.axisXTree  = res.body;
               this.fillSelectedNodes(this.axisXTree, this.selectedXnodes);
                this.fileDataService.createTreeMenu(this.fileData.id, SubjectType.TOPIC, this.isSelectGroupCheckBox.length >0 ).then(
                  (res: HttpResponse<TreeNode[]>) => {
                      this.axisYTree  = res.body;
                      this.fillSelectedNodes(this.axisYTree, this.selectedYnodes);
                      this.showChart();
                    },
                  (res: HttpErrorResponse) => this.onError(res.message));
              },
            (res: HttpErrorResponse) => this.onError(res.message));

           
      break;   
      case SubjectType.TOPIC:
          this.fileDataService.createTreeMenu(this.fileData.id, SubjectType.TOPIC, this.isSelectGroupCheckBox.length>0).then(
            (res: HttpResponse<TreeNode[]>) => {
              this.axisXTree  = res.body;
              this.fillSelectedNodes(this.axisXTree, this.selectedXnodes);
               this.fileDataService.createTreeMenu(this.fileData.id, SubjectType.CATEGORY, this.isSelectGroupCheckBox.length>0).then(
                (res: HttpResponse<TreeNode[]>) => {
                  this.axisYTree  = res.body;
                  this.fillSelectedNodes(this.axisYTree, this.selectedYnodes);
                  this.showChart();
                },
                (res: HttpErrorResponse) => this.onError(res.message));
             },
            (res: HttpErrorResponse) => this.onError(res.message));

           
      break;     
    
    }
  }

  fillSelectedNodes(sourceTree:TreeNode[], destTree:TreeNode[]){
    sourceTree.forEach(elm => {
      destTree.push(elm);
      if(elm.children){
        this.fillSelectedNodes(elm.children, destTree);
      }
    });
  }

  
  previousState() {
    window.history.back();
  }

  showChart(){
      this.chartRequet = new ChartRequet();
      
      this.chartRequet.fileDataId = this.fileData.id;
      this.chartRequet.chartType = this.selectedChartType;
      this.chartRequet.selectXnodes = this.trimSelectedXnodes(this.selectedXnodes);
      this.chartRequet.selectYnodes = this.trimSelectedXnodes(this.selectedYnodes);
      this.chartRequet.selectedSubject = this.selectedSubject;
      this.chartRequet.includeAverage = this.isSelectAverageCheckBox.length > 0;
      this.chartRequet.includeMedian = this.isSelectMedianCheckBox.length > 0;
      this.chartRequet.chartByGroup = this.isSelectGroupCheckBox.length > 0;
      this.chartRequet.distrubution = this.isSelectDistribution.length > 0;
      this.fileDataService.getChartData(this.chartRequet).subscribe(
        (res: HttpResponse<ChartResponse>) => this.chartReponse = res.body,
        (res: HttpErrorResponse) => this.onError(res.message)); 
  }

  trimSelectedXnodes(treeNodes: TreeNode[]): TreeNode[]{
    let resultTreeNodes: TreeNode[] = [];
    let treeNode:TreeNode;
    treeNodes.forEach(x => {
        x.parent = null;
        resultTreeNodes.push(x);  
    }
    );

    return resultTreeNodes;
  }
  saveChart(){
    const ref = this.dialogService.open(FileDataDynamicDialogComponent, {
      header: 'Give a name',
      width: '70%'
    })
   
    ref.onClose.subscribe((chartName:string) => {
      if(chartName){
        console.log("chartName=" + chartName);
      }
    });
    //add chart name 
   // this.chartRequet

  }

  exportChart(){
    this.chartRequet.selectXnodes = this.trimSelectedXnodes(this.selectedXnodes);
    this.chartRequet.selectYnodes = this.trimSelectedXnodes(this.selectedYnodes);
    return this.fileDataService.export2ExcelFile(this.chartRequet);
    
    // .subscribe(
    //   (res: HttpResponse<ExportExcelFile>) => {

    //     var binaryData = [];
    //     binaryData.push(res.body.byteArr);
    //     let url = window.URL.createObjectURL(res.body.byteArr);
    //     let a = document.createElement('a');
    //     document.body.appendChild(a);
    //     a.setAttribute('style', 'display: none');
    //     a.href = url;
    //     a.download = res.body.fileName;
    //     a.click();
    //     window.URL.revokeObjectURL(url);
    //     a.remove();
    //   },
    //     (res: HttpErrorResponse) => this.onError(res.message)

    // );
    
  }

  


sidebarSave(){
  this.visibleSidebar1 = false;
  this.showChart();
}

  private isEmpty(input:string):boolean{
    return (!input || input.length === 0 || !input.trim());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
    //this.msgs.push({severity: 'error', summary: 'Error', detail: errorMessage});
   
  }


}


