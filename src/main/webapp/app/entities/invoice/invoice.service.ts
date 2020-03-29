import { IdEntity } from './../../shared/model/id-entity.model';
import { IIdEntity } from './../../shared/model/id-entity.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IInvoice } from 'app/shared/model/invoice.model';

type EntityResponseType = HttpResponse<IInvoice>;
type IdEntityResponseType = HttpResponse<IIdEntity>;

type EntityArrayResponseType = HttpResponse<IInvoice[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceService {
  public resourceUrl = SERVER_API_URL + 'api/invoices';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/invoices';

  constructor(protected http: HttpClient) {}

  create(invoice: IInvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoice);
    return this.http
      .post<IInvoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoice: IInvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoice);
    return this.http
      .put<IInvoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findMaxId(): Observable<EntityResponseType> {
    return this.http
      .get<IInvoice>(`${this.resourceUrl}/getEntityMaxId`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoice[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(invoice: IInvoice): IInvoice {
    const copy: IInvoice = Object.assign({}, invoice, {
      invoiceDate: invoice.invoiceDate && invoice.invoiceDate.isValid() ? invoice.invoiceDate.format(DATE_FORMAT) : undefined,
      invoicePaidDate:
        invoice.invoicePaidDate && invoice.invoicePaidDate.isValid() ? invoice.invoicePaidDate.format(DATE_FORMAT) : undefined,
      invoiceDueDate: invoice.invoiceDueDate && invoice.invoiceDueDate.isValid() ? invoice.invoiceDueDate.format(DATE_FORMAT) : undefined,
      createdOn: invoice.createdOn && invoice.createdOn.isValid() ? invoice.createdOn.toJSON() : undefined,
      updatedOn: invoice.updatedOn && invoice.updatedOn.isValid() ? invoice.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.invoiceDate = res.body.invoiceDate ? moment(res.body.invoiceDate) : undefined;
      res.body.invoicePaidDate = res.body.invoicePaidDate ? moment(res.body.invoicePaidDate) : undefined;
      res.body.invoiceDueDate = res.body.invoiceDueDate ? moment(res.body.invoiceDueDate) : undefined;
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invoice: IInvoice) => {
        invoice.invoiceDate = invoice.invoiceDate ? moment(invoice.invoiceDate) : undefined;
        invoice.invoicePaidDate = invoice.invoicePaidDate ? moment(invoice.invoicePaidDate) : undefined;
        invoice.invoiceDueDate = invoice.invoiceDueDate ? moment(invoice.invoiceDueDate) : undefined;
        invoice.createdOn = invoice.createdOn ? moment(invoice.createdOn) : undefined;
        invoice.updatedOn = invoice.updatedOn ? moment(invoice.updatedOn) : undefined;
      });
    }
    return res;
  }
}
