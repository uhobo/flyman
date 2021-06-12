import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from '../../shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from '../../app.constants';
import { createRequestOption } from '../../shared';
import { IFileData } from '../../shared/model/file-data.model';
import { ChartRequet, ChartResponse, ExportExcelFile } from '../../shared/model/chart-types.model';
import { TreeNode } from 'primeng/api';

type EntityResponseType = HttpResponse<IFileData>;
type EntityArrayResponseType = HttpResponse<IFileData[]>;
type EntityChartResponseType = HttpResponse<ChartResponse>;
type ExportResponseType = HttpResponse<ExportExcelFile>;
type TreeNodeResponseType = HttpResponse<TreeNode[]>;

@Injectable({ providedIn: 'root' })
export class FileDataService {
  public resourceUrl = SERVER_API_URL + 'api/file-data';

  constructor(protected http: HttpClient) {}

  create(fileData: IFileData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileData);
    return this.http
      .post<IFileData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  upload(file: any): Observable<EntityResponseType> {
    return this.http
    .post<any>(this.resourceUrl+ '/upload', file, { observe: 'response' })
    .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fileData: IFileData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileData);
    return this.http
      .put<IFileData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IFileData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFileData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChartData(chartReq: ChartRequet): Observable<EntityChartResponseType>{ 
    return this.http.post<ChartResponse>(`${this.resourceUrl}/chartData`, chartReq, { observe: 'response' })
    .pipe(map((res: EntityChartResponseType) => res));
  }
  
  
  
  createTreeMenu(id: String,subjectType: number, isSelectGroupCheckBox: boolean):Promise<HttpResponse<TreeNode[]>>{
    return this.http.get<TreeNode[]>(`${this.resourceUrl}/createTreeMenu/${id}/${subjectType}/${isSelectGroupCheckBox}`,  { observe: 'response' }).toPromise(); 
  }
  // export2ExcelFile(chartData: ChartRequet): Observable<ExportResponseType>{
  //   return this.http.post<ExportExcelFile>(`${this.resourceUrl}/chartData`, ChartRequet, { observe: 'response' })
  //   .pipe(map((res: ExportResponseType) => res));
  // }


  //, responseType: 'blob' as 'json' 
  export2ExcelFile(chartData: ChartRequet){
    return this.http.post(`${this.resourceUrl}/export2Excel`, chartData,{ responseType: "blob"}).map(res => {
     return {
        filename: "export-chart.xlsx",
        data: res
      };
    }).subscribe(res => {
      let url = window.URL.createObjectURL(res.data);
      let a = document.createElement('a');
      document.body.appendChild(a);
      a.setAttribute('style', 'display: none');
      a.href = url;
      a.download = res.filename;
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });

  //    //return this.http.post<Blob>(`${this.resourceUrl}/export2Excel`, chartData, { responseType: "blob"}).pipe(
  //     //  map((res :BlobResponseType) => res));
   }

  protected convertDateFromClient(fileData: IFileData): IFileData {
    const copy: IFileData = Object.assign({}, fileData, {
      timestamp: fileData.timestamp != null && fileData.timestamp.isValid() ? fileData.timestamp.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fileData: IFileData) => {
        fileData.timestamp = fileData.timestamp != null ? moment(fileData.timestamp) : null;
      });
    }
    return res;
  }
}
