import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISurveyResult } from 'app/shared/model/survey-result.model';
import { TreeNode } from 'primeng/components/common/treenode';
import { ChartDataWrapper } from 'app/shared/model/surveyChart.model';
import { ChartCriteria } from 'app/shared/model/chartcriteria.model';

type EntityResponseType = HttpResponse<ISurveyResult>;
type EntityArrayResponseType = HttpResponse<ISurveyResult[]>;

@Injectable({ providedIn: 'root' })
export class SurveyResultService {
    public resourceUrl = SERVER_API_URL + 'api/survey-results';

    constructor(private http: HttpClient) {}

    create(surveyResult: ISurveyResult): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(surveyResult);
        return this.http
            .post<ISurveyResult>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(surveyResult: ISurveyResult[]): Observable<EntityArrayResponseType> {
        const copy = this.convertDateArrayFromClient(surveyResult);
        return this.http
            .put<ISurveyResult[]>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    
    
    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<ISurveyResult>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }


    findByResponding(id?: String) : Observable<EntityArrayResponseType> {
        
        return this.http
            .get<ISurveyResult[]>(`${this.resourceUrl}/getSurveyResultByResponding/${id}`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

     findBySurveyAndResponding(surveyId:String, respondingId:String): Observable<EntityArrayResponseType> {
        return this.http
            .get<ISurveyResult[]>(`${this.resourceUrl}/findBySurveyAndResponding/${surveyId}/${respondingId}`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISurveyResult[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getAllSurveyData(): Promise<HttpResponse<TreeNode[]>>{
        return this.http.get<TreeNode[]>(`${this.resourceUrl}/getAllSurveyData`,  { observe: 'response' })
            .pipe().toPromise();
    }

    getChartData(chartCriteria?: ChartCriteria ): Promise<HttpResponse<ChartDataWrapper>>{
        return this.http.post<ChartDataWrapper>(`${this.resourceUrl}/getChartData`, chartCriteria, { observe: 'response' })
            .pipe().toPromise();
    }



    private convertDateArrayFromClient(surveyResult: ISurveyResult[]): ISurveyResult[] {
        let copy: ISurveyResult [] =[];
        surveyResult.forEach((surveyResult: ISurveyResult) => {
            copy.push(this.convertDateFromClient(surveyResult));    

        });  
        
       return copy;

        
    }


    private convertDateFromClient(surveyResult: ISurveyResult): ISurveyResult {
        const copy: ISurveyResult = Object.assign({}, surveyResult, {
            submitDate:
                surveyResult.submitDate != null && surveyResult.submitDate.isValid() ? surveyResult.submitDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.submitDate = res.body.submitDate != null ? moment(res.body.submitDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((surveyResult: ISurveyResult) => {
            surveyResult.submitDate = surveyResult.submitDate != null ? moment(surveyResult.submitDate) : null;
        });
        return res;
    }
}
