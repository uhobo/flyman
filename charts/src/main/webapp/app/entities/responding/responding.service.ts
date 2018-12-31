import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResponding, Responding } from 'app/shared/model/responding.model';
import { ISurvey } from 'app/shared/model/survey.model';
import { RespondingSurveyInput } from 'app/shared/model/respondingSurveyInput.model';
import { ISurveyResult } from 'app/shared/model/survey-result.model';

type EntityResponseType = HttpResponse<IResponding>;
type EntityArrayResponseType = HttpResponse<IResponding[]>;

@Injectable({ providedIn: 'root' })
export class RespondingService {
    public resourceUrl = SERVER_API_URL + 'api/respondings';

    constructor(private http: HttpClient) {}

    create(responding: IResponding): Observable<EntityResponseType> {
        return this.http.post<IResponding>(this.resourceUrl, responding, { observe: 'response' });
    }

    update(responding: IResponding): Observable<EntityResponseType> {
        return this.http.put<IResponding>(this.resourceUrl, responding, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IResponding>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResponding[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    
    publish(repondingSurveyInput: RespondingSurveyInput): Observable<HttpResponse<ISurveyResult[]>>{
        return this.http.post<ISurveyResult[]>("api/survey-results/publish", repondingSurveyInput, { observe: 'response' });
    }
    
}
