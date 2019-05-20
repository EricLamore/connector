import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStartEmail } from 'app/shared/model/start-email.model';

type EntityResponseType = HttpResponse<IStartEmail>;
type EntityArrayResponseType = HttpResponse<IStartEmail[]>;

@Injectable({ providedIn: 'root' })
export class StartEmailService {
  public resourceUrl = SERVER_API_URL + 'api/start-emails';

  constructor(protected http: HttpClient) {}

  create(startEmail: IStartEmail): Observable<EntityResponseType> {
    return this.http.post<IStartEmail>(this.resourceUrl, startEmail, { observe: 'response' });
  }

  update(startEmail: IStartEmail): Observable<EntityResponseType> {
    return this.http.put<IStartEmail>(this.resourceUrl, startEmail, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IStartEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStartEmail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
