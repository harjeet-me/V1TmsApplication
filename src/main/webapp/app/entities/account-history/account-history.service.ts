import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAccountHistory } from 'app/shared/model/account-history.model';

type EntityResponseType = HttpResponse<IAccountHistory>;
type EntityArrayResponseType = HttpResponse<IAccountHistory[]>;

@Injectable({ providedIn: 'root' })
export class AccountHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/account-histories';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/account-histories';

  constructor(protected http: HttpClient) {}

  create(accountHistory: IAccountHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountHistory);
    return this.http
      .post<IAccountHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountHistory: IAccountHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountHistory);
    return this.http
      .put<IAccountHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccountHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountHistory[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(accountHistory: IAccountHistory): IAccountHistory {
    const copy: IAccountHistory = Object.assign({}, accountHistory, {
      createdOn: accountHistory.createdOn && accountHistory.createdOn.isValid() ? accountHistory.createdOn.toJSON() : undefined,
      updatedOn: accountHistory.updatedOn && accountHistory.updatedOn.isValid() ? accountHistory.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accountHistory: IAccountHistory) => {
        accountHistory.createdOn = accountHistory.createdOn ? moment(accountHistory.createdOn) : undefined;
        accountHistory.updatedOn = accountHistory.updatedOn ? moment(accountHistory.updatedOn) : undefined;
      });
    }
    return res;
  }
}
