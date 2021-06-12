import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IFileData, FileData } from '../../shared/model/file-data.model';
import { FileDataService } from './file-data.service';
import { IDataHeader, DataHeader } from '../../shared/model/data-header.model';
import { SeriesItem } from '../../shared/model/series-item.model';
import {RadioButtonModule} from 'primeng/radiobutton';
import {CheckboxModule} from 'primeng/checkbox';
import { threadId } from 'worker_threads';
@Component({
  selector: 'jhi-file-data-update',
  templateUrl: './file-data-update.component.html'
})
export class FileDataUpdateComponent implements OnInit {
  isSaving: boolean;
  timestampDp: any;
  cols: any[];
  data: DataHeader[];
  selectedTopicRow : number;
  selectedGroupRow : number;
  selectedShowRows: number[];
  unSelectedShowRows : boolean[];
  seriesList: SeriesItem[];

  origFileData: IFileData;
  editForm = this.fb.group({
    id: [],
    fileName: [null, [Validators.required]],
    title: [null, [Validators.required]],
    timestamp: [null, [Validators.required]]
  });

  constructor(protected fileDataService: FileDataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() :void{
    this.isSaving = false;
    this.cols = [
      { field: 'title', header: 'Title' },
      { field: 'show', header: 'Show' },
      { field: 'topic', header: 'Topic' },
      { field: 'group', header: 'Group' }
    ];

    this.activatedRoute.data.subscribe(({ fileData }) => {
      this.origFileData = fileData;
      this.seriesList = fileData.seriesList;
      this.data = fileData.headers;
      this.selectedShowRows = [];
      this.unSelectedShowRows =[];
      this.selectedTopicRow = fileData.topicColIndex;
      this.selectedGroupRow = fileData.groupColIndex;

      for (let entry of fileData.headers) {
        if(entry.show){
          this.selectedShowRows.push(entry.index);
          this.unSelectedShowRows[entry.index] = false;
        } else{
          this.unSelectedShowRows[entry.index] = true;
        }
      }
      this.updateForm(fileData);
    });
  }

  updateForm(fileData: IFileData):void {
    this.editForm.patchValue({
      id: fileData.id,
      fileName: fileData.fileName,
      title: fileData.title,
      timestamp: fileData.timestamp
    });
  }

  previousState():void {
    window.history.back();
  }

  save():void {
    this.isSaving = true;
    const fileData = this.createFromForm();
    if (fileData.id !== undefined) {
      this.subscribeToSaveResponse(this.fileDataService.update(fileData));
    } else {
      this.subscribeToSaveResponse(this.fileDataService.create(fileData));
    }
  }

  private createFromForm(): IFileData {
    return {
      ...new FileData(),
      id: this.editForm.get(['id']).value,
      fileName: this.editForm.get(['fileName']).value,
      title: this.editForm.get(['title']).value,
      timestamp: this.editForm.get(['timestamp']).value,
      headers: this.updateHeader(),
      topicColIndex: this.selectedTopicRow,
      groupColIndex: this.selectedGroupRow,
      seriesList: this.seriesList,
      dataLines: this.origFileData.dataLines
    };
  }

  protected updateHeader() : DataHeader[]{
    for (let entry of this.data) {
      entry.show = false;
    }
    for(let selectedInx of this.selectedShowRows){
      this.data[selectedInx].show = true;
    }
    return this.data;

  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileData>>) :void{
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess():void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError():void {
    this.isSaving = false;
  }

  onChageShow(index: number):void{
    this.unSelectedShowRows[index] = !this.unSelectedShowRows[index];
    if(this.selectedTopicRow === index){
      this.selectedTopicRow  = -1;
      this.selectedGroupRow = -1;
    }
  }
 

 
}
