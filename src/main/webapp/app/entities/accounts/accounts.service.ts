import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAccounts } from 'app/shared/model/accounts.model';

type EntityResponseType = HttpResponse<IAccounts>;
type EntityArrayResponseType = HttpResponse<IAccounts[]>;

@Injectable({ providedIn: 'root' })
export class AccountsService {
  public resourceUrl = SERVER_API_URL + 'api/accounts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/accounts';

  constructor(protected http: HttpClient) {}

  create(accounts: IAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accounts);
    return this.http
      .post<IAccounts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accounts: IAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accounts);
    return this.http
      .put<IAccounts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccounts>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccounts[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccounts[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(accounts: IAccounts): IAccounts {
    const copy: IAccounts = Object.assign({}, accounts, {
      createdDate: accounts.createdDate && accounts.createdDate.isValid() ? accounts.createdDate.toJSON() : undefined,
      lastModifiedDate: accounts.lastModifiedDate && accounts.lastModifiedDate.isValid() ? accounts.lastModifiedDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accounts: IAccounts) => {
        accounts.createdDate = accounts.createdDate ? moment(accounts.createdDate) : undefined;
        accounts.lastModifiedDate = accounts.lastModifiedDate ? moment(accounts.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
