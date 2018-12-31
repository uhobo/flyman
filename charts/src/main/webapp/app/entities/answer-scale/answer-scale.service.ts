import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnswerScale } from 'app/shared/model/answer-scale.model';

type EntityResponseType = HttpResponse<IAnswerScale>;
type EntityArrayResponseType = HttpResponse<IAnswerScale[]>;

@Injectable({ providedIn: 'root' })
export class AnswerScaleService {
    public resourceUrl = SERVER_API_URL + 'api/answer-scales';

    constructor(private http: HttpClient) {}

    create(answerScale: IAnswerScale): Observable<EntityResponseType> {
        return this.http.post<IAnswerScale>(this.resourceUrl, answerScale, { observe: 'response' });
    }

    update(answerScale: IAnswerScale): Observable<EntityResponseType> {
        return this.http.put<IAnswerScale>(this.resourceUrl, answerScale, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IAnswerScale>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnswerScale[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
