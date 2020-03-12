import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDriver } from 'app/shared/model/driver.model';

type EntityResponseType = HttpResponse<IDriver>;
type EntityArrayResponseType = HttpResponse<IDriver[]>;

@Injectable({ providedIn: 'root' })
export class DriverService {
  public resourceUrl = SERVER_API_URL + 'api/drivers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/drivers';

  constructor(protected http: HttpClient) {}

  create(driver: IDriver): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(driver);
    return this.http
      .post<IDriver>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(driver: IDriver): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(driver);
    return this.http
      .put<IDriver>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDriver>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDriver[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDriver[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(driver: IDriver): IDriver {
    const copy: IDriver = Object.assign({}, driver, {
      dob: driver.dob && driver.dob.isValid() ? driver.dob.format(DATE_FORMAT) : undefined,
      companyJoinedOn: driver.companyJoinedOn && driver.companyJoinedOn.isValid() ? driver.companyJoinedOn.format(DATE_FORMAT) : undefined,
      companyLeftOn: driver.companyLeftOn && driver.companyLeftOn.isValid() ? driver.companyLeftOn.format(DATE_FORMAT) : undefined,
      createdOn: driver.createdOn && driver.createdOn.isValid() ? driver.createdOn.toJSON() : undefined,
      updatedOn: driver.updatedOn && driver.updatedOn.isValid() ? driver.updatedOn.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dob = res.body.dob ? moment(res.body.dob) : undefined;
      res.body.companyJoinedOn = res.body.companyJoinedOn ? moment(res.body.companyJoinedOn) : undefined;
      res.body.companyLeftOn = res.body.companyLeftOn ? moment(res.body.companyLeftOn) : undefined;
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
      res.body.updatedOn = res.body.updatedOn ? moment(res.body.updatedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((driver: IDriver) => {
        driver.dob = driver.dob ? moment(driver.dob) : undefined;
        driver.companyJoinedOn = driver.companyJoinedOn ? moment(driver.companyJoinedOn) : undefined;
        driver.companyLeftOn = driver.companyLeftOn ? moment(driver.companyLeftOn) : undefined;
        driver.createdOn = driver.createdOn ? moment(driver.createdOn) : undefined;
        driver.updatedOn = driver.updatedOn ? moment(driver.updatedOn) : undefined;
      });
    }
    return res;
  }
}
