import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICustomer } from 'app/shared/model/customer.model';

type EntityResponseType = HttpResponse<ICustomer>;
type EntityArrayResponseType = HttpResponse<ICustomer[]>;

@Injectable({ providedIn: 'root' })
export class CustomerService {
  public resourceUrl = SERVER_API_URL + 'api/customers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/customers';

  constructor(protected http: HttpClient) {}

  create(customer: ICustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .post<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customer: ICustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .put<ICustomer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(customer: ICustomer): ICustomer {
    const copy: ICustomer = Object.assign({}, customer, {
      preferredContactTime:
        customer.preferredContactTime && customer.preferredContactTime.isValid() ? customer.preferredContactTime.toJSON() : undefined,
      customerSince: customer.customerSince && customer.customerSince.isValid() ? customer.customerSince.format(DATE_FORMAT) : undefined,
      timeZone: customer.timeZone && customer.timeZone.isValid() ? customer.timeZone.toJSON() : undefined,
      createdDate: customer.createdDate && customer.createdDate.isValid() ? customer.createdDate.toJSON() : undefined,
      lastModifiedDate: customer.lastModifiedDate && customer.lastModifiedDate.isValid() ? customer.lastModifiedDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.preferredContactTime = res.body.preferredContactTime ? moment(res.body.preferredContactTime) : undefined;
      res.body.customerSince = res.body.customerSince ? moment(res.body.customerSince) : undefined;
      res.body.timeZone = res.body.timeZone ? moment(res.body.timeZone) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customer: ICustomer) => {
        customer.preferredContactTime = customer.preferredContactTime ? moment(customer.preferredContactTime) : undefined;
        customer.customerSince = customer.customerSince ? moment(customer.customerSince) : undefined;
        customer.timeZone = customer.timeZone ? moment(customer.timeZone) : undefined;
        customer.createdDate = customer.createdDate ? moment(customer.createdDate) : undefined;
        customer.lastModifiedDate = customer.lastModifiedDate ? moment(customer.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
