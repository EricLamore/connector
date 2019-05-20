import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRelaunchEmail } from 'app/shared/model/relaunch-email.model';

type EntityResponseType = HttpResponse<IRelaunchEmail>;
type EntityArrayResponseType = HttpResponse<IRelaunchEmail[]>;

@Injectable({ providedIn: 'root' })
export class RelaunchEmailService {
  public resourceUrl = SERVER_API_URL + 'api/relaunch-emails';

  constructor(protected http: HttpClient) {}

  create(relaunchEmail: IRelaunchEmail): Observable<EntityResponseType> {
    return this.http.post<IRelaunchEmail>(this.resourceUrl, relaunchEmail, { observe: 'response' });
  }

  update(relaunchEmail: IRelaunchEmail): Observable<EntityResponseType> {
    return this.http.put<IRelaunchEmail>(this.resourceUrl, relaunchEmail, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IRelaunchEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelaunchEmail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
