import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClerk } from 'app/shared/model/clerk.model';

type EntityResponseType = HttpResponse<IClerk>;
type EntityArrayResponseType = HttpResponse<IClerk[]>;

@Injectable({ providedIn: 'root' })
export class ClerkService {
  public resourceUrl = SERVER_API_URL + 'api/clerks';

  constructor(protected http: HttpClient) {}

  create(clerk: IClerk): Observable<EntityResponseType> {
    return this.http.post<IClerk>(this.resourceUrl, clerk, { observe: 'response' });
  }

  update(clerk: IClerk): Observable<EntityResponseType> {
    return this.http.put<IClerk>(this.resourceUrl, clerk, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IClerk>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClerk[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
