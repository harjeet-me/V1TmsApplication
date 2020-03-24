import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IInvoiceReport } from 'app/shared/model/invoice-report.model';

type EntityResponseType = HttpResponse<IInvoiceReport>;
type EntityArrayResponseType = HttpResponse<IInvoiceReport[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceReportService {
  public resourceUrl = SERVER_API_URL + 'api/invoice-reports';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/invoice-reports';

  constructor(protected http: HttpClient) {}

  create(invoiceReport: IInvoiceReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceReport);
    return this.http
      .post<IInvoiceReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoiceReport: IInvoiceReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceReport);
    return this.http
      .put<IInvoiceReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvoiceReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoiceReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoiceReport[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(invoiceReport: IInvoiceReport): IInvoiceReport {
    const copy: IInvoiceReport = Object.assign({}, invoiceReport, {
      fromDate: invoiceReport.fromDate && invoiceReport.fromDate.isValid() ? invoiceReport.fromDate.format(DATE_FORMAT) : undefined,
      toDate: invoiceReport.toDate && invoiceReport.toDate.isValid() ? invoiceReport.toDate.format(DATE_FORMAT) : undefined,
      createdOn: invoiceReport.createdOn && invoiceReport.createdOn.isValid() ? invoiceReport.createdOn.toJSON() : undefined,
      updatedOn: invoiceReport.updatedOn && invoiceReport.updatedOn.isValid() ? invoiceReport.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.toDate = res.body.toDate ? moment(res.body.toDate) : undefined;
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invoiceReport: IInvoiceReport) => {
        invoiceReport.fromDate = invoiceReport.fromDate ? moment(invoiceReport.fromDate) : undefined;
        invoiceReport.toDate = invoiceReport.toDate ? moment(invoiceReport.toDate) : undefined;
        invoiceReport.createdOn = invoiceReport.createdOn ? moment(invoiceReport.createdOn) : undefined;
        invoiceReport.updatedOn = invoiceReport.updatedOn ? moment(invoiceReport.updatedOn) : undefined;
      });
    }
    return res;
  }
}
