import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';

type EntityResponseType = HttpResponse<IInvoiceItem>;
type EntityArrayResponseType = HttpResponse<IInvoiceItem[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceItemService {
  public resourceUrl = SERVER_API_URL + 'api/invoice-items';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/invoice-items';

  constructor(protected http: HttpClient) {}

  create(invoiceItem: IInvoiceItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceItem);
    return this.http
      .post<IInvoiceItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoiceItem: IInvoiceItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceItem);
    return this.http
      .put<IInvoiceItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvoiceItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoiceItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoiceItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(invoiceItem: IInvoiceItem): IInvoiceItem {
    const copy: IInvoiceItem = Object.assign({}, invoiceItem, {
      createdOn: invoiceItem.createdOn && invoiceItem.createdOn.isValid() ? invoiceItem.createdOn.toJSON() : undefined,
      updatedOn: invoiceItem.updatedOn && invoiceItem.updatedOn.isValid() ? invoiceItem.updatedOn.toJSON() : undefined
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
      res.body.forEach((invoiceItem: IInvoiceItem) => {
        invoiceItem.createdOn = invoiceItem.createdOn ? moment(invoiceItem.createdOn) : undefined;
        invoiceItem.updatedOn = invoiceItem.updatedOn ? moment(invoiceItem.updatedOn) : undefined;
      });
    }
    return res;
  }
}
