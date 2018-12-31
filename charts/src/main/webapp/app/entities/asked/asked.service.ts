import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAsked } from 'app/shared/model/asked.model';

type EntityResponseType = HttpResponse<IAsked>;
type EntityArrayResponseType = HttpResponse<IAsked[]>;

@Injectable({ providedIn: 'root' })
export class AskedService {
    public resourceUrl = SERVER_API_URL + 'api/askeds';

    constructor(private http: HttpClient) {}

    create(asked: IAsked): Observable<EntityResponseType> {
        return this.http.post<IAsked>(this.resourceUrl, asked, { observe: 'response' });
    }

    update(asked: IAsked): Observable<EntityResponseType> {
        return this.http.put<IAsked>(this.resourceUrl, asked, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IAsked>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAsked[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
