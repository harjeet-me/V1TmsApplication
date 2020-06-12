import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ITransactionsRecord } from 'app/shared/model/transactions-record.model';

type EntityResponseType = HttpResponse<ITransactionsRecord>;
type EntityArrayResponseType = HttpResponse<ITransactionsRecord[]>;

@Injectable({ providedIn: 'root' })
export class TransactionsRecordService {
  public resourceUrl = SERVER_API_URL + 'api/transactions-records';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/transactions-records';

  constructor(protected http: HttpClient) {}

  create(transactionsRecord: ITransactionsRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionsRecord);
    return this.http
      .post<ITransactionsRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionsRecord: ITransactionsRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionsRecord);
    return this.http
      .put<ITransactionsRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionsRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionsRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionsRecord[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(transactionsRecord: ITransactionsRecord): ITransactionsRecord {
    const copy: ITransactionsRecord = Object.assign({}, transactionsRecord, {
      createdDate:
        transactionsRecord.createdDate && transactionsRecord.createdDate.isValid() ? transactionsRecord.createdDate.toJSON() : undefined,
      lastModifiedDate:
        transactionsRecord.lastModifiedDate && transactionsRecord.lastModifiedDate.isValid()
          ? transactionsRecord.lastModifiedDate.toJSON()
          : undefined
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
      res.body.forEach((transactionsRecord: ITransactionsRecord) => {
        transactionsRecord.createdDate = transactionsRecord.createdDate ? moment(transactionsRecord.createdDate) : undefined;
        transactionsRecord.lastModifiedDate = transactionsRecord.lastModifiedDate ? moment(transactionsRecord.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
