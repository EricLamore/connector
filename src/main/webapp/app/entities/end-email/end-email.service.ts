import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEndEmail } from 'app/shared/model/end-email.model';

type EntityResponseType = HttpResponse<IEndEmail>;
type EntityArrayResponseType = HttpResponse<IEndEmail[]>;

@Injectable({ providedIn: 'root' })
export class EndEmailService {
  public resourceUrl = SERVER_API_URL + 'api/end-emails';

  constructor(protected http: HttpClient) {}

  create(endEmail: IEndEmail): Observable<EntityResponseType> {
    return this.http.post<IEndEmail>(this.resourceUrl, endEmail, { observe: 'response' });
  }

  update(endEmail: IEndEmail): Observable<EntityResponseType> {
    return this.http.put<IEndEmail>(this.resourceUrl, endEmail, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IEndEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEndEmail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
