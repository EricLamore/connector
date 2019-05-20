import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISMS } from 'app/shared/model/sms.model';

type EntityResponseType = HttpResponse<ISMS>;
type EntityArrayResponseType = HttpResponse<ISMS[]>;

@Injectable({ providedIn: 'root' })
export class SMSService {
  public resourceUrl = SERVER_API_URL + 'api/sms';

  constructor(protected http: HttpClient) {}

  create(sMS: ISMS): Observable<EntityResponseType> {
    return this.http.post<ISMS>(this.resourceUrl, sMS, { observe: 'response' });
  }

  update(sMS: ISMS): Observable<EntityResponseType> {
    return this.http.put<ISMS>(this.resourceUrl, sMS, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISMS>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISMS[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
