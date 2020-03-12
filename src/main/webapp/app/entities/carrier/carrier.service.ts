import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICarrier } from 'app/shared/model/carrier.model';

type EntityResponseType = HttpResponse<ICarrier>;
type EntityArrayResponseType = HttpResponse<ICarrier[]>;

@Injectable({ providedIn: 'root' })
export class CarrierService {
  public resourceUrl = SERVER_API_URL + 'api/carriers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/carriers';

  constructor(protected http: HttpClient) {}

  create(carrier: ICarrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carrier);
    return this.http
      .post<ICarrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(carrier: ICarrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(carrier);
    return this.http
      .put<ICarrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICarrier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarrier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICarrier[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(carrier: ICarrier): ICarrier {
    const copy: ICarrier = Object.assign({}, carrier, {
      customerSince: carrier.customerSince && carrier.customerSince.isValid() ? carrier.customerSince.format(DATE_FORMAT) : undefined,
      timeZone: carrier.timeZone && carrier.timeZone.isValid() ? carrier.timeZone.toJSON() : undefined,
      createdOn: carrier.createdOn && carrier.createdOn.isValid() ? carrier.createdOn.toJSON() : undefined,
      updatedOn: carrier.updatedOn && carrier.updatedOn.isValid() ? carrier.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.customerSince = res.body.customerSince ? moment(res.body.customerSince) : undefined;
      res.body.timeZone = res.body.timeZone ? moment(res.body.timeZone) : undefined;
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((carrier: ICarrier) => {
        carrier.customerSince = carrier.customerSince ? moment(carrier.customerSince) : undefined;
        carrier.timeZone = carrier.timeZone ? moment(carrier.timeZone) : undefined;
        carrier.createdOn = carrier.createdOn ? moment(carrier.createdOn) : undefined;
        carrier.updatedOn = carrier.updatedOn ? moment(carrier.updatedOn) : undefined;
      });
    }
    return res;
  }
}
