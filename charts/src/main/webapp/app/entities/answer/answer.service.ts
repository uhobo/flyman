import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnswer } from 'app/shared/model/answer.model';

type EntityResponseType = HttpResponse<IAnswer>;
type EntityArrayResponseType = HttpResponse<IAnswer[]>;

@Injectable({ providedIn: 'root' })
export class AnswerService {
    public resourceUrl = SERVER_API_URL + 'api/answers';

    constructor(private http: HttpClient) {}

    create(answer: IAnswer): Observable<EntityResponseType> {
        return this.http.post<IAnswer>(this.resourceUrl, answer, { observe: 'response' });
    }

    update(answer: IAnswer): Observable<EntityResponseType> {
        return this.http.put<IAnswer>(this.resourceUrl, answer, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
