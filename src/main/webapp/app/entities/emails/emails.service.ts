import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmails } from 'app/shared/model/emails.model';

type EntityResponseType = HttpResponse<IEmails>;
type EntityArrayResponseType = HttpResponse<IEmails[]>;

@Injectable({ providedIn: 'root' })
export class EmailsService {
  public resourceUrl = SERVER_API_URL + 'api/emails';

  constructor(protected http: HttpClient) {}

  create(emails: IEmails): Observable<EntityResponseType> {
    return this.http.post<IEmails>(this.resourceUrl, emails, { observe: 'response' });
  }

  update(emails: IEmails): Observable<EntityResponseType> {
    return this.http.put<IEmails>(this.resourceUrl, emails, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEmails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
